package com.github.pokemon

import android.app.Application
import androidx.room.Room
import com.hi.dhl.pokemon.data.local.AppDataBase


class App : Application() {
    companion object {
        lateinit var db: AppDataBase
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, AppDataBase::class.java, "dhl.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}