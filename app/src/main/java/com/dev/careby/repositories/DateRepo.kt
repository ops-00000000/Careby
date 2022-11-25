package com.dev.careby.repositories

import com.dev.careby.data.DateD
import com.dev.careby.data.WaterScreenData
import java.time.LocalDate
import java.util.Date

interface DateRepo {
    fun updateDate(id: Long, localDate: String)
    fun getDate(id:Long): DateD
    fun saveDate(date: DateD)
}