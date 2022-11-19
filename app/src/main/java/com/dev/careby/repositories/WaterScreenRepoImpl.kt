package com.dev.careby.repositories

import androidx.lifecycle.LiveData
import com.dev.careby.dao.WaterScreenDao
import com.dev.careby.entitys.WaterScreenEntity
import androidx.lifecycle.Transformations
import com.dev.careby.data.WaterScreenData


class WaterScreenRepoImpl(
    private val dao: WaterScreenDao,
) : WaterScreenRepo {

    override fun getData(id: Long): WaterScreenData = dao.getData(id)


    override fun updateCurIndicate(id:Long , curIndicate: Int) {
        dao.updateCurIndicate(id, curIndicate)

        }


    override fun updateMaxIndicate(id:Long ,maxIndicate: Int) {
        dao.updateMaxIndicate(id, maxIndicate)
    }

    override fun save(data: WaterScreenData) {
        dao.save(WaterScreenEntity.fromDto(data))
    }
}