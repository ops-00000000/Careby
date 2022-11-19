package com.dev.careby.repositories

import androidx.lifecycle.LiveData
import com.dev.careby.data.WaterScreenData
import com.dev.careby.entitys.WaterScreenEntity

interface WaterScreenRepo {
    fun updateCurIndicate(id: Long, curIndicate: Int)
    fun updateMaxIndicate(id: Long, maxIndicate: Int)
    fun getData(id:Long): WaterScreenData
    fun save(data: WaterScreenData)
}