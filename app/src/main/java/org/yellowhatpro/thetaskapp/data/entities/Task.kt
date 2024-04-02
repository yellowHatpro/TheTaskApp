package org.yellowhatpro.thetaskapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String = "",
    var dueDate: LocalDateTime = LocalDateTime.now(),
    var isCompleted: Boolean = false,
    var description: String = ""
    ) {
    val createdDateFormatted: String
    get() = dueDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
}