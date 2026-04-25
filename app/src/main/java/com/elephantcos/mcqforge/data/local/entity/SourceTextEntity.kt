package com.elephantcos.mcqforge.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "source_texts")
data class SourceTextEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String, val content: String, val wordCount: Int,
    val createdAt: Long = System.currentTimeMillis()
)
