package com.mohamadrizki.to_dolist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mohamadrizki.to_dolist.database.ToDo
import com.mohamadrizki.to_dolist.repository.ToDoRepository

class MainViewModel(application: Application) : ViewModel() {
    private val mToDoRepository: ToDoRepository = ToDoRepository(application)
    
    fun getAllToDos(): LiveData<List<ToDo>> = mToDoRepository.getAllToDos()
    
    fun insert(toDo: ToDo) {
        mToDoRepository.insert(toDo)
    }
    fun update(toDo: ToDo) {
        mToDoRepository.update(toDo)
    }
    fun delete(toDo: ToDo) {
        mToDoRepository.delete(toDo)
    }
}