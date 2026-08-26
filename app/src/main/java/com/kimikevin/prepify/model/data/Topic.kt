package com.kimikevin.prepify.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topic")
data class Topic(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val isPremium: Boolean
)
