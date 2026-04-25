package com.elephantcos.mcqforge.data.repository
import com.elephantcos.mcqforge.data.local.AppDatabase
import com.elephantcos.mcqforge.data.local.entity.McqQuestionEntity
import com.elephantcos.mcqforge.data.local.entity.SourceTextEntity
import kotlinx.coroutines.flow.Flow
class McqRepository(private val db: AppDatabase) {
    suspend fun addSource(title: String, content: String) = db.sourceDao().insert(SourceTextEntity(title = title, content = content, wordCount = content.split("\\s+".toRegex()).size))
    fun sources(): Flow<List<SourceTextEntity>> = db.sourceDao().all()
    suspend fun addMcq(q: McqQuestionEntity) = db.mcqDao().insert(q)
    suspend fun addMcqs(qs: List<McqQuestionEntity>) = db.mcqDao().insertAll(qs)
    fun mcqs(): Flow<List<McqQuestionEntity>> = db.mcqDao().all()
    fun randomMcqs(n: Int) = db.mcqDao().random(n)
    suspend fun deleteAll() = db.mcqDao().deleteAll()
}
