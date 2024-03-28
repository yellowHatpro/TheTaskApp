package org.yellowhatpro.thetaskapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song")
data class Song(
    @PrimaryKey
    val id: String = "",
    val song: String = "",
    val genre: String = ""
)
