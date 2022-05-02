package ge.gogichaishvili.lesson4.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ge.gogichaishvili.lesson4.data.dao.ToDoDao
import ge.gogichaishvili.lesson4.data.entities.ToDoModel

const val DATABASE_VERSION = 1

@Database(entities = [ToDoModel::class], version = DATABASE_VERSION, exportSchema = false)
abstract class ToDoDataBase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

    companion object {
        @Volatile
        private var INSTANCE: ToDoDataBase? = null
        fun getDatabase(context: Context): ToDoDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val newInstance =
                    Room.databaseBuilder(context, ToDoDataBase::class.java, "todo_database")
                        //.allowMainThreadQueries()
                        .build()
                INSTANCE = newInstance
                return newInstance
            }
        }
    }
}