package org.yellowhatpro.thetaskapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val dueDate: LocalDateTime = LocalDateTime.now(),
    val isCompleted: Boolean = false,
    val description: String = ""
    ) {
    val createdDateFormatted: String
    get() = dueDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
}