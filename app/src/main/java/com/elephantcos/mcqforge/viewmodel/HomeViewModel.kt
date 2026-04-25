package com.elephantcos.mcqforge.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.elephantcos.mcqforge.McqForgeApp
import kotlinx.coroutines.launch
class HomeViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = (app as McqForgeApp).repository
    val mcqs = repo.mcqs()
    fun delete(id: Long) = viewModelScope.launch { repo.deleteAll() }
}
