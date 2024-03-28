package org.yellowhatpro.thetaskapp.data.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SongDao {
    @Query("SELECT * from SONG")
    fun getAllSongs()
}