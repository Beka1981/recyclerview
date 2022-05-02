package ge.gogichaishvili.lesson4

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import ge.gogichaishvili.lesson4.adapters.ToDoAdapter
import ge.gogichaishvili.lesson4.data.database.ToDoDataBase
import ge.gogichaishvili.lesson4.data.entities.ToDoModel
import ge.gogichaishvili.lesson4.databinding.ActivityMainBinding
import ge.gogichaishvili.lesson4.extensions.hideKeyboard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var toDoAdapter: ToDoAdapter
    private lateinit var database: ToDoDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvToDo.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        toDoAdapter = ToDoAdapter()
        binding.rvToDo.adapter = toDoAdapter.apply {
            setOnItemCLickListener { item: ToDoModel ->
                if (!item.isCompleted) {
                    item.isCompleted = true
                    updateAndUpdateRv(item)
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.changed),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    deleteAndUpdateRv(item)
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.deleted),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }

        database = ToDoDataBase.getDatabase(applicationContext)
        drawToDoItems()

        binding.btnAddToList.setOnClickListener {

            if (binding.etToDo.text.toString().trim().isEmpty()) {
                binding.etToDo.error = getString(R.string.field_required)
                binding.etToDo.setBackgroundResource(R.drawable.edittext_bg_red)
            } else {
                insertAndUpdateRv(ToDoModel(0, binding.etToDo.text.toString().trim()))
                binding.etToDo.text?.clear()
                hideKeyboard()
                Toast.makeText(this, getString(R.string.added), Toast.LENGTH_SHORT).show()
            }

        }

        binding.etToDo.doOnTextChanged { _, _, _, _ ->
            binding.etToDo.error = null
            binding.etToDo.setBackgroundResource(R.drawable.edittext_bg)
        }

        binding.btnDeleteAll.setOnClickListener {
            deleteAllAndUpdateRv()
        }

    }

    private fun insertAndUpdateRv(toDoModel: ToDoModel) {
        CoroutineScope(IO).launch {
            try {
                database.toDoDao().insert(toDoModel)
                withContext(Main) {
                    drawToDoItems()
                }
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    private fun deleteAndUpdateRv(toDoModel: ToDoModel) {
        CoroutineScope(IO).launch {
            try {
                database.toDoDao().delete(toDoModel)
                withContext(Main) {
                    drawToDoItems()
                }
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    private fun updateAndUpdateRv(toDoModel: ToDoModel) {
        CoroutineScope(IO).launch {
            try {
                database.toDoDao().update(toDoModel)
                withContext(Main) {
                    drawToDoItems()
                }
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    private fun deleteAllAndUpdateRv() {
        CoroutineScope(IO).launch {
            try {
                database.toDoDao().deleteAll()
                withContext(Main) {
                    drawToDoItems()
                }
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    private fun drawToDoItems() {
        CoroutineScope(IO).launch {
            try {
                val allToDoItems = database.toDoDao().getAll()
                withContext(Main) {
                    toDoAdapter.updateAll(allToDoItems)
                }
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

}