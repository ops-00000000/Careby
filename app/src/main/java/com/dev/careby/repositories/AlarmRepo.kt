package com.dev.careby.repositories

import com.dev.careby.data.AlarmData
import com.dev.careby.data.DateD


interface AlarmRepo {
    fun updateTime(id: Long, time: String)
    fun getData(id:Long): AlarmData
    fun saveTime(data: AlarmData)
}