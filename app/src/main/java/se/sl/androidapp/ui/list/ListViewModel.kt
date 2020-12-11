package se.sl.androidapp.ui.list

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import se.sl.androidapp.databinding.SingleLiveData
import se.sl.androidapp.model.ChuckJoke
import se.sl.androidapp.ui.ChuckRepo

class ListViewModel(app: Application) : AndroidViewModel(app) {

    sealed class Command {
        data class NavigateToDetails(val joke: ChuckJoke) : Command()
    }

    private val _commands = SingleLiveData<Command>()
    val commands: LiveData<Command> = _commands

    private val repo = ChuckRepo(app)

    private val _list = MutableLiveData<List<ChuckWrapper>>(emptyList())
    val list: MutableLiveData<List<ChuckWrapper>> = _list

    init {
        repo.listJokes()
            .asFlow()
            .onEach {
                list.postValue(
                    it.map { joke ->
                        ChuckWrapper(joke) { this.navigateToDetail(joke) }
                    }
                )
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun navigateToDetail(joke: ChuckJoke) {
        _commands.postValue(Command.NavigateToDetails(joke))
    }

    data class ChuckWrapper(val joke: ChuckJoke, val clickListener: (() -> Unit))
}