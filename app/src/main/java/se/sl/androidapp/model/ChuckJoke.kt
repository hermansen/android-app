package se.sl.androidapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import se.sl.androidapp.http.models.ChuckResponse

@Parcelize
data class ChuckJoke(val id: String, val joke: String, val imgUrl: String) : Parcelable

fun se.sl.androidapp.db.entities.ChuckJoke.asJokeDomainModel(): ChuckJoke =
    ChuckJoke(this.id, this.joke, this.imgUrl)

fun ChuckJoke.asJokeDatabaseEntity(): se.sl.androidapp.db.entities.ChuckJoke =
    se.sl.androidapp.db.entities.ChuckJoke(this.id, this.joke, this.imgUrl)

fun ChuckResponse.asChuckJoke(): ChuckJoke = ChuckJoke(this.id, this.value, this.iconUrl)