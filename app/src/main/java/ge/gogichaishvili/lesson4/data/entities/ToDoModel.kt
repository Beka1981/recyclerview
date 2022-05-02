package ge.gogichaishvili.lesson4.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "to_do_list")
data class ToDoModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    var isCompleted: Boolean = false
)
