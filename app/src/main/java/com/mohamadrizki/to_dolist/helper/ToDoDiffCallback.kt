package com.mohamadrizki.to_dolist.helper

import androidx.recyclerview.widget.DiffUtil
import com.mohamadrizki.to_dolist.database.ToDo

class ToDoDiffCallback(private val oldToDoList: List<ToDo>, private val newToDoList: List<ToDo>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldToDoList.size
    override fun getNewListSize(): Int = newToDoList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldToDoList[oldItemPosition].id == newToDoList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldToDo = oldToDoList[oldItemPosition]
        val newToDo = newToDoList[newItemPosition]
        return oldToDo.toDo == newToDo.toDo
    }
}