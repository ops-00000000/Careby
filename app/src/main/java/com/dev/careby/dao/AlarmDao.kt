package com.dev.careby.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dev.careby.data.AlarmData
import com.dev.careby.entitys.AlarmEntity

@Dao
interface AlarmDao {

    @Query("SELECT * FROM AlarmEntity WHERE id = :id")
    fun getData(id: Long):AlarmData

    @Insert
    fun insert(data:AlarmEntity)

    @Query("UPDATE AlarmEntity SET time = :time , data = :data WHERE id = :id")
    fun updateTime(id: Long, time: String, data: String)


    fun saveTime(data: AlarmEntity) =
        insert(data)
}