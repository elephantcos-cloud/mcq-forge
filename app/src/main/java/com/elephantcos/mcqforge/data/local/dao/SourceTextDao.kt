package com.elephantcos.mcqforge.data.local.dao
import androidx.room.*
import com.elephantcos.mcqforge.data.local.entity.SourceTextEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface SourceTextDao {
    @Query("SELECT * FROM source_texts ORDER BY createdAt DESC") fun all(): Flow<List<SourceTextEntity>>
    @Insert suspend fun insert(s: SourceTextEntity): Long
    @Delete suspend fun delete(s: SourceTextEntity)
}
