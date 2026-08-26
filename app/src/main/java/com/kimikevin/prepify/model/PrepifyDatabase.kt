package com.kimikevin.prepify.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kimikevin.prepify.model.dao.StudyDao
import com.kimikevin.prepify.model.data.FlashCard
import com.kimikevin.prepify.model.data.Progress
import com.kimikevin.prepify.model.data.Topic
import kotlin.concurrent.Volatile

@Database(entities = [Topic::class, FlashCard::class, Progress::class], version = 1, exportSchema = false)
abstract class PrepifyDatabase : RoomDatabase() {

    abstract fun studyDao(): StudyDao

    companion object {
        @Volatile
        private var INSTANCE: PrepifyDatabase? = null

        fun getDatabase(context: Context): PrepifyDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context = context,
                        klass = PrepifyDatabase::class.java,
                        name = "prepify_database"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}