package com.kimikevin.prepify.model.data

import androidx.room.Embedded

data class FlashCardReview(
    @Embedded val flashcard: FlashCard,
    @Embedded val progress: Progress?
)
