package com.mohamadrizki.to_dolist.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.mohamadrizki.to_dolist.database.ToDo
import com.mohamadrizki.to_dolist.database.ToDoDao
import com.mohamadrizki.to_dolist.database.ToDoRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ToDoRepository(application: Application) {
    private val mToDosDao: ToDoDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    
    init {
        val db = ToDoRoomDatabase.getDatabase(application)
        mToDosDao = db.toDoDao()
    }
    
    fun getAllToDos(): LiveData<List<ToDo>> = mToDosDao.getAllToDos()
    
    fun insert(toDo: ToDo) {
        executorService.execute { mToDosDao.insert(toDo) }
    }
    fun delete(toDo: ToDo) {
        executorService.execute { mToDosDao.delete(toDo) }
    }
    
    fun update(toDo: ToDo) {
        executorService.execute { mToDosDao.update(toDo) }
    }
}