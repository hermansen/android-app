package se.swosch.jackson.ui.main

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import se.swosch.jackson.databinding.SingleLiveData
import se.swosch.jackson.ui.ChuckJoke
import se.swosch.jackson.ui.ChuckRepo

class MainViewModel(app: Application) : AndroidViewModel(app) {

    sealed class Command {
        object NavigateToListCommand : Command()
    }

    private val _commands = SingleLiveData<Command>()
    val commands: LiveData<Command> = _commands

    private val repo = ChuckRepo(getApplication())

    private val refreshClicked = MutableLiveData<Unit>()
    private val refreshFlow: Flow<Unit> = refreshClicked.asFlow()

    private val _chuckJoke = MutableLiveData<ChuckJoke>()
    val chuckJoke: LiveData<ChuckJoke> = _chuckJoke

    init {
        // load first joke directly
        flow<ChuckJoke> {
            refreshJoke()
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)

        // whenever refresh is clicked
        refreshFlow
            .onEach {
                refreshJoke()
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun refresh() {
        refreshClicked.postValue(Unit)
    }

    fun toListView(view: View) {
        _commands.postValue(Command.NavigateToListCommand)
    }

    private suspend fun refreshJoke() {
        jokeFlow().collect {
            saveJoke(it)
            _chuckJoke.postValue(it)
        }
    }

    private fun jokeFlow() = repo.getRandomJoke()

    private fun saveJoke(joke: ChuckJoke) {
        repo.saveJoke(joke)
    }
}