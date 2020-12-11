package se.sl.androidapp.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "joke")
data class ChuckJoke(
    @PrimaryKey val id: String,
    val joke: String,
    val imgUrl: String
)