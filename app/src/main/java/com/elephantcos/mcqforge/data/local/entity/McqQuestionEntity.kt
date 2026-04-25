package com.elephantcos.mcqforge.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "mcq_questions")
data class McqQuestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sourceTextId: Long, val questionText: String,
    val optionA: String, val optionB: String, val optionC: String, val optionD: String,
    val correctOptionIndex: Int, val explanation: String,
    val createdAt: Long = System.currentTimeMillis()
)
