package ge.gogichaishvili.lesson4.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ge.gogichaishvili.lesson4.R
import ge.gogichaishvili.lesson4.data.ToDoModel
import ge.gogichaishvili.lesson4.databinding.ListItemBinding

class ToDoAdapter(private val toDoList: MutableList<ToDoModel>) :
    RecyclerView.Adapter<ToDoViewHolder>() {

    private lateinit var itemClickListener: (ToDoModel, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding)
    }

    fun setOnItemCLickListener(clickListener: (ToDoModel, Int) -> Unit) {
        itemClickListener = clickListener
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val toDo = toDoList[position]

        holder.binding.tvToDo.text = toDo.title
        if (toDo.isCompleted) {
            holder.binding.vCircle.setBackgroundResource(R.drawable.circle_bg)
        } else {
            holder.binding.vCircle.setBackgroundResource(R.drawable.circle_bg_red)
        }

        holder.binding.vCard.setOnClickListener {
            itemClickListener.invoke(toDo, holder.adapterPosition)
        }

    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    fun addNewToDoItem(toDoModel: ToDoModel) {
        toDoList.add(0, toDoModel)
        notifyItemInserted(0)
    }

    fun removeToDoItem(position: Int) {
        toDoList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun changedToDoItem(position: Int) {
        notifyItemChanged(position)
    }
}

class ToDoViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {}


