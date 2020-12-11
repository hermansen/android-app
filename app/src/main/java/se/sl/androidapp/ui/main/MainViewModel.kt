package se.sl.androidapp.ui.main

import android.app.Application
import androidx.lifecycle.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import se.sl.androidapp.databinding.SingleLiveData
import se.sl.androidapp.model.ChuckJoke
import se.sl.androidapp.model.Rating
import se.sl.androidapp.ui.ChuckRepo
import timber.log.Timber

class MainViewModel(app: Application) : AndroidViewModel(app),
    SwipeRefreshLayout.OnRefreshListener {

    sealed class Command {
        object NavigateToListCommand : Command()
    }

    private val _commands = SingleLiveData<Command>()
    val commands: LiveData<Command> = _commands

    sealed class UIState {
        object Loading : UIState()
        object Done : UIState()
        object Refreshing : UIState()
    }

    private val _uiState = SingleLiveData<UIState>()
    val uiState: LiveData<UIState> = _uiState

    val progressBarVisible: LiveData<Boolean> = _uiState.map {
        when (it) {
            UIState.Loading -> true
            else -> false
        }
    }

    private val repo = ChuckRepo(getApplication())

    private val refreshClicked = MutableLiveData<Unit>()
    private val refreshFlow: Flow<Unit> = refreshClicked.asFlow()

    private val _chuckJoke = MutableLiveData<ChuckJoke>()
    val chuckJoke: LiveData<ChuckJoke> = _chuckJoke

    private val _rating = MutableLiveData<Rating>()
    val rating: LiveData<Rating> = _rating
    private val newRating = MutableLiveData<Int>()

    init {
        // load first joke directly
        flow<ChuckJoke> {
            _uiState.postValue(UIState.Loading)
            refreshJoke()
        }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)

        // whenever refresh is clicked
        refreshFlow
            .onEach {
                refreshJoke()
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)

        newRating.asFlow()
            .onEach {
                repo.updateRating(this.rating.value?.id, chuckJoke.value!!.id, it)
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun toListView() {
        _commands.postValue(Command.NavigateToListCommand)
    }

    override fun onRefresh() {
        _uiState.postValue(UIState.Refreshing)
        refresh()
    }

    fun updateRating(rating: Int) {
        newRating.postValue(rating)
    }

    private fun refresh() {
        refreshClicked.postValue(Unit)
    }

    private suspend fun refreshJoke() {
        Timber.i("DEBUG SESH: refreshing joke")
        jokeFlow().collect {
            _rating.postValue(Rating())
            _chuckJoke.postValue(it)
            saveJoke(it)
            _uiState.postValue(UIState.Done)
        }
    }

    private fun jokeFlow() = repo.getRandomJoke()

    private fun saveJoke(joke: ChuckJoke) {
        repo.saveJoke(joke)
    }
}