package com.dev.careby.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.careby.data.DateD
import java.time.LocalDate

@Entity
data class DateEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    var localDate: String
) {
    fun toDto() = DateD(id,localDate)
    companion object {
        fun fromDto(dto: DateD) =
            DateEntity(dto.id, dto.localDate)

    }
}