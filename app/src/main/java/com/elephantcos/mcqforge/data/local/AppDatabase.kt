package com.elephantcos.mcqforge.data.local
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elephantcos.mcqforge.data.local.dao.McqQuestionDao
import com.elephantcos.mcqforge.data.local.dao.SourceTextDao
import com.elephantcos.mcqforge.data.local.entity.McqQuestionEntity
import com.elephantcos.mcqforge.data.local.entity.SourceTextEntity
@Database(entities = [McqQuestionEntity::class, SourceTextEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mcqDao(): McqQuestionDao
    abstract fun sourceDao(): SourceTextDao
    companion object {
        @Volatile private var i: AppDatabase? = null
        fun get(ctx: Context) = i ?: synchronized(this) {
            i ?: Room.databaseBuilder(ctx.applicationContext, AppDatabase::class.java, "mcq_forge_db").build().also { i = it }
        }
    }
}
