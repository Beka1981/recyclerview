package ge.gogichaishvili.lesson4

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ge.gogichaishvili.lesson4.adapters.ToDoAdapter
import ge.gogichaishvili.lesson4.data.ToDoModel
import ge.gogichaishvili.lesson4.databinding.ActivityMainBinding
import ge.gogichaishvili.lesson4.extensions.hideKeyboard

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var toDoList = mutableListOf<ToDoModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.rvToDo.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = ToDoAdapter(toDoList)
        binding.rvToDo.adapter = adapter.apply {
            setOnItemCLickListener { item: ToDoModel, i: Int ->
                if (!item.isCompleted) {
                    item.isCompleted = true
                    changedToDoItem(i)
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.changed),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    removeToDoItem(i)
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.deleted),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }


        binding.btnAddToList.setOnClickListener {

            if (binding.etToDo.text.toString().trim().isEmpty()) {
                binding.etToDo.error = getString(R.string.field_required)
                binding.etToDo.setBackgroundResource(R.drawable.edittext_bg_red)
            } else {
                adapter.addNewToDoItem(ToDoModel(binding.etToDo.text.toString().trim()))
                binding.etToDo.text?.clear()
                hideKeyboard()
                Toast.makeText(this, getString(R.string.added), Toast.LENGTH_SHORT).show()
            }

        }


        binding.etToDo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
                // empty
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.etToDo.error = null
                binding.etToDo.setBackgroundResource(R.drawable.edittext_bg)
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

    }


}