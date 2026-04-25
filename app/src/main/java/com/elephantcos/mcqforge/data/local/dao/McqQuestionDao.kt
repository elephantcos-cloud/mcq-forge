package com.elephantcos.mcqforge.data.local.dao
import androidx.room.*
import com.elephantcos.mcqforge.data.local.entity.McqQuestionEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface McqQuestionDao {
    @Query("SELECT * FROM mcq_questions ORDER BY createdAt DESC") fun all(): Flow<List<McqQuestionEntity>>
    @Insert suspend fun insert(q: McqQuestionEntity): Long
    @Insert suspend fun insertAll(qs: List<McqQuestionEntity>)
    @Delete suspend fun delete(q: McqQuestionEntity)
    @Query("DELETE FROM mcq_questions") suspend fun deleteAll()
    @Query("SELECT * FROM mcq_questions ORDER BY RANDOM() LIMIT :n") fun random(n: Int): Flow<List<McqQuestionEntity>>
}
