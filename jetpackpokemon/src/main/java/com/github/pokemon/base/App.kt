package com.github.pokemon.base

import android.app.Application
import androidx.room.Room
import com.github.pokemon.local.AppDataBase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application() {
    companion object {
        lateinit var db: AppDataBase
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, AppDataBase::class.java, "jet_pack.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}