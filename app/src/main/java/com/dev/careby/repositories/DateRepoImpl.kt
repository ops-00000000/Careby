package com.dev.careby.repositories

import com.dev.careby.dao.DateDao
import com.dev.careby.dao.WaterScreenDao
import com.dev.careby.data.DateD
import com.dev.careby.data.WaterScreenData
import com.dev.careby.entitys.DateEntity
import com.dev.careby.entitys.WaterScreenEntity
import java.time.LocalDate
import java.util.*

class DateRepoImpl(
    private val dao: DateDao,
) : DateRepo {

    override fun getDate(id: Long): DateD = dao.getDate(id)
    override fun updateDate(id: Long, localDate: String) {
        dao.updateDate(id, localDate)
    }

    override fun saveDate(date: DateD) {
        dao.saveDate(DateEntity.fromDto(date))
    }

}