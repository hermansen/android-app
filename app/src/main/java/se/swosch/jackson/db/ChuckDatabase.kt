package se.swosch.jackson.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import se.swosch.jackson.db.daos.ChuckJokeDao
import se.swosch.jackson.db.daos.RatingDao
import se.swosch.jackson.db.entities.ChuckJoke
import se.swosch.jackson.db.entities.Rating

@Database(entities = [ChuckJoke::class, Rating::class], version = 1)
abstract class ChuckDatabase : RoomDatabase() {

    abstract fun chuckJokeDao(): ChuckJokeDao

    abstract fun ratingDao(): RatingDao

    companion object {
        private var instance: ChuckDatabase? = null

        fun getInstance(context: Context) =
            Room.databaseBuilder(context, ChuckDatabase::class.java, "ChuckDatabase.db")
                .build()
                .also {
                    instance = it
                }

    }
}