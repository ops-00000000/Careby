package com.dev.careby.entitys


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.careby.data.AlarmData

@Entity
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    var time: String
) {
    fun toDto() = AlarmEntity(id,time)
    companion object {
        fun fromDto(dto: AlarmData) =
            AlarmEntity(dto.id, dto.time)

    }
}