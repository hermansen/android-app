package se.sl.androidapp.model

data class Rating(val id: Int = 0, val jokeId: String = "", val stars: Int = 0)

fun se.sl.androidapp.db.entities.Rating.asRatingDomainModel(): Rating {
    return if (this == null) {
        Rating()
    } else {
        Rating(this.id, this.jokeId, this.stars)
    }
}