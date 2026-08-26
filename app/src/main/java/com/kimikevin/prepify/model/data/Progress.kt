package com.kimikevin.prepify.model.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "progress",
    foreignKeys = [
        ForeignKey(
            entity = FlashCard::class,
            parentColumns = ["id"],
            childColumns = ["cardId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("cardId", unique = true)]
)
data class Progress(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cardId: Int,
    val nextReviewDate: Long,
    val masteryLevel: Int = 0
)
