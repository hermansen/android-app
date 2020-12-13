package se.sl.androidapp.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import se.sl.androidapp.databinding.SingleLiveData
import se.sl.androidapp.model.ChuckJoke
import se.sl.androidapp.ui.ChuckRepo

class MainViewModel(app: Application) : AndroidViewModel(app) {

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

    private val _chuckJoke = MutableLiveData<ChuckJoke>()
    val chuckJoke: LiveData<ChuckJoke> = _chuckJoke

    fun toListView() {
        _commands.postValue(Command.NavigateToListCommand)
    }
}