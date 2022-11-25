package com.dev.careby.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dev.careby.data.WaterScreenData
import com.dev.careby.entitys.WaterScreenEntity


@Dao
interface WaterScreenDao {

    @Query("SELECT * FROM WaterScreenEntity WHERE id = :id")
    fun getData(id: Long): WaterScreenData

    @Insert
    fun insert(data: WaterScreenEntity)

    @Query("UPDATE WaterScreenEntity SET curIndicate = :curIndicate WHERE id = :id")
    fun updateCurIndicate(id: Long, curIndicate: Int)

    @Query("UPDATE WaterScreenEntity SET maxIndicate = :maxIndicate WHERE id = :id")
    fun updateMaxIndicate(id:Long , maxIndicate: Int)

    fun save(data: WaterScreenEntity) =
        insert(data)
}