package com.dev.careby.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dev.careby.data.DateD
import com.dev.careby.entitys.DateEntity
import com.dev.careby.entitys.WaterScreenEntity
import java.time.LocalDate
import java.util.Date

@Dao
interface DateDao {

    @Query("SELECT * FROM DateEntity WHERE id = :id")
    fun getDate(id: Long): DateD

    @Insert
    fun insert(data: DateEntity)

    @Query("UPDATE DateEntity SET localDate = :localDate WHERE id = :id")
    fun updateDate(id: Long, localDate: String)


    fun saveDate(data: DateEntity) =
        insert(data)





}