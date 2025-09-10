package com.mohamadrizki.to_dolist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(toDo: ToDo)
    @Update
    fun update(toDo: ToDo)
    @Delete
    fun delete(toDo: ToDo)
    @Query("SELECT * from todo ORDER BY id ASC")
    fun getAllToDos(): LiveData<List<ToDo>>
}