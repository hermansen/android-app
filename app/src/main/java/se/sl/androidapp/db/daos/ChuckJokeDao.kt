package se.sl.androidapp.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import se.sl.androidapp.db.entities.ChuckJoke

@Dao
interface ChuckJokeDao {
    @Insert(onConflict = REPLACE)
    fun save(joke: ChuckJoke)

    @Query("SELECT * FROM joke WHERE id = :id")
    fun load(id: String): LiveData<ChuckJoke>

    @Query("SELECT * FROM joke")
    fun list(): LiveData<List<ChuckJoke>>
}