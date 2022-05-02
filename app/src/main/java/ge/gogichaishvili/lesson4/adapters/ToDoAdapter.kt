package ge.gogichaishvili.lesson4.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ge.gogichaishvili.lesson4.R
import ge.gogichaishvili.lesson4.data.entities.ToDoModel
import ge.gogichaishvili.lesson4.databinding.ListItemBinding

class ToDoAdapter() : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    private val toDoList = mutableListOf<ToDoModel>()
    private lateinit var itemClickListener: (ToDoModel) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bindData(toDoList[position])
    }

    fun setOnItemCLickListener(clickListener: (ToDoModel) -> Unit) {
        itemClickListener = clickListener
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    fun updateAll(list: List<ToDoModel>) {
        toDoList.clear()
        toDoList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ToDoViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(toDoModel: ToDoModel) {

            binding.tvToDo.text = toDoModel.title
            if (toDoModel.isCompleted) {
                binding.vCircle.setBackgroundResource(R.drawable.circle_bg)
            } else {
                binding.vCircle.setBackgroundResource(R.drawable.circle_bg_red)
            }

            binding.vCard.setOnClickListener {
                itemClickListener.invoke(toDoModel)
            }
        }
    }
}


