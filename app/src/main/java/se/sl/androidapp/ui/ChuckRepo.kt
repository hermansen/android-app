package se.sl.androidapp.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import se.sl.androidapp.db.ChuckDatabase
import se.sl.androidapp.http.ChuckClient
import se.sl.androidapp.model.*

import timber.log.Timber

class ChuckRepo(private val context: Context) {

    @ExperimentalCoroutinesApi
    fun getRandomJoke(): Flow<ChuckJoke> = flow {
        val client = ChuckClient.instance
        val joke = client.random()
        val chuckJoke = joke.asChuckJoke()
        emit(chuckJoke)
    }

    suspend fun randomJoke(): ChuckJoke {
        return ChuckClient.instance.random().asChuckJoke()
    }

    fun saveJoke(joke: ChuckJoke) =
        ChuckDatabase.getInstance(context).chuckJokeDao().save(joke.asJokeDatabaseEntity())

    fun listJokes(): LiveData<List<ChuckJoke>> =
        ChuckDatabase.getInstance(context).chuckJokeDao().list().map {
            it.map { joke: se.sl.androidapp.db.entities.ChuckJoke ->
                joke.asJokeDomainModel()
            }
        }

    fun getRating(jokeId: String?): LiveData<Rating> {
        Timber.i("DEBUG SESH: getting rating")
        return if (jokeId == null) {
            Timber.i("DEBUG SESH: returning rating")
            MutableLiveData(Rating())
        } else {
            ChuckDatabase.getInstance(context).ratingDao().getForJoke(jokeId).map {
                it.asRatingDomainModel()
            }
        }
    }

    fun updateRating(ratingId: Int?, jokeId: String, stars: Int) {
        val rating =
            if (ratingId == null) {
                se.sl.androidapp.db.entities.Rating(jokeId = jokeId, stars = stars)
            } else {
                se.sl.androidapp.db.entities.Rating(ratingId, jokeId, stars)
            }
        ChuckDatabase.getInstance(context).ratingDao()
            .save(rating)
    }
}
