package com.dev.careby.repositories

import com.dev.careby.dao.AlarmDao
import com.dev.careby.data.AlarmData
import com.dev.careby.entitys.AlarmEntity
import com.dev.careby.entitys.DateEntity

class AlarmRepoImpl (
    private val dao: AlarmDao
        ) : AlarmRepo{

    override fun getData(id: Long): AlarmData = dao.getData(id)

    override fun saveTime(data: AlarmData) {
        dao.saveTime(AlarmEntity.fromDto(data))
    }

    override fun updateTime(id: Long, time: String) {
        dao.updateTime(id,time)
    }
        }