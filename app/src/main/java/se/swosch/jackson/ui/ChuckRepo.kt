package se.swosch.jackson.ui

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import se.swosch.jackson.db.ChuckDatabase
import se.swosch.jackson.http.ChuckClient
import se.swosch.jackson.http.models.ChuckResponse

class ChuckRepo(private val context: Context) {

    @ExperimentalCoroutinesApi
    fun getRandomJoke(): Flow<ChuckJoke> = flow {
        emit(ChuckClient.instance.random().asChuckJoke())
    }.flowOn(Dispatchers.IO)

    fun saveJoke(joke: ChuckJoke) =
        ChuckDatabase.getInstance(context).chuckJokeDao().save(joke.asDatabaseEntity())

    fun listJokes(): LiveData<List<ChuckJoke>> =
        ChuckDatabase.getInstance(context).chuckJokeDao().list().map {
            it.map { joke ->
                joke.asDomainModel()
            }
        }

    fun getRating(jokeId: String?): LiveData<Rating> =
        ChuckDatabase.getInstance(context).ratingDao().getForJoke(jokeId)
            .map {
                if (it == null) {
                    Rating()
                } else {
                    it.asDomainModel()
                }
            }

    fun updateRating(ratingId: Int?, jokeId: String, stars: Int) {
        val rating =
            if (ratingId == null) {
                se.swosch.jackson.db.entities.Rating(jokeId = jokeId, stars = stars)
            } else {
                se.swosch.jackson.db.entities.Rating(ratingId, jokeId, stars)
            }
        ChuckDatabase.getInstance(context).ratingDao()
            .save(rating)
    }
}


@Parcelize
data class ChuckJoke(val id: String, val joke: String, val imgUrl: String) : Parcelable

private fun se.swosch.jackson.db.entities.ChuckJoke.asDomainModel() =
    ChuckJoke(this.id, this.joke, this.imgUrl)

fun ChuckJoke.asDatabaseEntity(): se.swosch.jackson.db.entities.ChuckJoke =
    se.swosch.jackson.db.entities.ChuckJoke(this.id, this.joke, this.imgUrl)

data class Rating(val id: Int = 0, val jokeId: String = "", val stars: Int = 0)

private fun se.swosch.jackson.db.entities.Rating.asDomainModel() =
    Rating(this.id, this.jokeId, this.stars)

private fun Rating.asDatabaseEntity() =
    se.swosch.jackson.db.entities.Rating(this.id, this.jokeId, this.stars)

fun ChuckResponse.asChuckJoke(): ChuckJoke = ChuckJoke(this.id, this.value, this.iconUrl)