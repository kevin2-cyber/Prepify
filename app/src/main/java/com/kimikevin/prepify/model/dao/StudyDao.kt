package com.kimikevin.prepify.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kimikevin.prepify.model.data.FlashCard
import com.kimikevin.prepify.model.data.FlashCardReview
import com.kimikevin.prepify.model.data.Progress
import com.kimikevin.prepify.model.data.Topic
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyDao {

    @Query("SELECT * FROM topic")
    fun getAllTopics(): Flow<List<Topic>>

    @Query("""
        SELECT * FROM flashcard 
        LEFT JOIN progress ON flashcard.id = progress.cardId 
        WHERE flashcard.topicId = :topicId 
        AND (progress.nextReviewDate IS NULL OR progress.nextReviewDate <= :currentTimestamp)
        ORDER BY progress.nextReviewDate ASC
    """)
    suspend fun getCardsDueForReview(topicId: Int, currentTimestamp: Long): List<FlashCardReview>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProgress(progress: Progress)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopic(topic: Topic): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashCards(flashCards: List<FlashCard>)
}