package se.swosch.jackson.ui.detail

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import se.swosch.jackson.ui.ChuckJoke
import se.swosch.jackson.ui.ChuckRepo
import se.swosch.jackson.ui.Rating

class DetailViewModel(app: Application) : AndroidViewModel(app) {

    private val _chuckJoke = MutableLiveData<ChuckJoke>()
    val chuckJoke: LiveData<ChuckJoke> = _chuckJoke

    var rating: LiveData<Rating> = MutableLiveData()
    private val newRating = MutableLiveData<Int>()

    private val repo = ChuckRepo(app.applicationContext)

    init {
        newRating.asFlow()
            .onEach {
                repo.updateRating(this.rating.value?.id, chuckJoke.value!!.id, it)
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun setJoke(joke: ChuckJoke) {
        _chuckJoke.postValue(joke)
        rating = repo.getRating(joke.id)
    }

    fun updateRating(rating: Int) {
        newRating.postValue(rating)
    }
}