package ge.gogichaishvili.lesson4.data.dao

import androidx.room.*
import ge.gogichaishvili.lesson4.data.entities.ToDoModel

@Dao
interface ToDoDao {

    @Query("SELECT * FROM to_do_list ORDER BY id DESC")
    fun getAll(): List<ToDoModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(toDoModel: ToDoModel)

    @Query("DELETE FROM to_do_list")
    fun deleteAll()

    @Delete
    fun delete(toDoModel: ToDoModel)

    @Update
    fun update(toDoModel: ToDoModel)
}