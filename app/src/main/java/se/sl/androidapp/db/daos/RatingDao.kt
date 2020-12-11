package se.sl.androidapp.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import se.sl.androidapp.db.entities.Rating

@Dao
interface RatingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(rating: Rating)

    @Query("SELECT * FROM rating WHERE id = :id")
    fun load(id: String): LiveData<Rating>

    @Query("SELECT * FROM rating")
    fun list(): LiveData<List<Rating>>

    @Query("SELECT * FROM rating WHERE jokeId = :jokeId")
    fun getForJoke(jokeId: String?): LiveData<Rating>
}