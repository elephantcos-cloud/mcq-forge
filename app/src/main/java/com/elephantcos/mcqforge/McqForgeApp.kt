package com.elephantcos.mcqforge
import android.app.Application
import com.elephantcos.mcqforge.data.local.AppDatabase
import com.elephantcos.mcqforge.data.repository.McqRepository
class McqForgeApp : Application() {
    val db by lazy { AppDatabase.get(this) }
    val repository by lazy { McqRepository(db) }
}
