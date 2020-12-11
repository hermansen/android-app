package se.sl.androidapp.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "rating", indices = [Index(value = ["jokeId"], unique = true)])
data class Rating(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ForeignKey(
        entity = ChuckJoke::class,
        parentColumns = ["id"],
        childColumns = ["jokeId"],
        onDelete = ForeignKey.CASCADE)
    val jokeId: String,
    val stars: Int
)