package com.kimikevin.prepify.model.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "flashcard",
    foreignKeys = [
        ForeignKey(
            entity = Topic::class,
            parentColumns = ["id"],
            childColumns = ["topicId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("topicId")]
)
data class FlashCard(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val topicId: Int,
    val question: String,
    val answer: String
)
