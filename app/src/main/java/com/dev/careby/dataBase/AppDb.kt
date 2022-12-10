package com.dev.careby.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dev.careby.dao.DateDao
import com.dev.careby.dao.WaterScreenDao
import com.dev.careby.data.WaterScreenData
import com.dev.careby.entitys.AlarmEntity
import com.dev.careby.entitys.DateEntity
import com.dev.careby.entitys.WaterScreenEntity


@Database(entities = [WaterScreenEntity::class, DateEntity::class, AlarmEntity::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun WaterDao(): WaterScreenDao
    abstract fun DateDao(): DateDao

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDb::class.java, "app.db")
                .allowMainThreadQueries()
                .build()
    }
}

