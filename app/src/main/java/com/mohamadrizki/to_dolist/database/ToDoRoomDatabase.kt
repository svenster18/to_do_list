package com.mohamadrizki.to_dolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDo::class], version = 1)
abstract class ToDoRoomDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
    companion object {
        @Volatile
        private var INSTANCE: ToDoRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): ToDoRoomDatabase {
            if (INSTANCE == null) {
                synchronized(ToDoRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ToDoRoomDatabase::class.java, "to_do_database")
                        .build()
                }
            }
            return INSTANCE as ToDoRoomDatabase
        }
    }
}