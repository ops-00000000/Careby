package com.dev.careby.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.careby.data.WaterScreenData
import com.dev.careby.screens.WaterScreen

@Entity
data class WaterScreenEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    var curIndicate: Int,
    var maxIndicate: Int
) {
    fun toDto() = WaterScreenData(id,curIndicate,maxIndicate)
    companion object {
        fun fromDto(dto: WaterScreenData) =
            WaterScreenEntity(dto.id, dto.curIndicate, dto.maxIndicate)

    }
}