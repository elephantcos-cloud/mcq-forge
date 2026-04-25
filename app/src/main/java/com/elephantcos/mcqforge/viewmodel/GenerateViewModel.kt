package com.elephantcos.mcqforge.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.elephantcos.mcqforge.McqForgeApp
import com.elephantcos.mcqforge.data.local.entity.McqQuestionEntity
import com.elephantcos.mcqforge.nlp.NlpBridge
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
data class GenState(val title: String = "", val text: String = "", val loading: Boolean = false, val result: List<McqItem> = emptyList())
data class McqItem(val question: String, val options: List<String>, val correct: Int)
class GenerateViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = (app as McqForgeApp).repository
    private val _s = MutableStateFlow(GenState()); val state: StateFlow<GenState> = _s
    fun setTitle(t: String) { _s.value = _s.value.copy(title = t) }
    fun setText(t: String) { _s.value = _s.value.copy(text = t) }
    fun generate() = viewModelScope.launch {
        _s.value = _s.value.copy(loading = true, result = emptyList())
        val raw = NlpBridge.generateMcq(_s.value.text)
        val list = listOf(McqItem(raw.question, raw.options.toList(), raw.correct))
        val entities = list.map { McqQuestionEntity(sourceTextId = 0, questionText = it.question, optionA = it.options.getOrElse(0){""}, optionB = it.options.getOrElse(1){""}, optionC = it.options.getOrElse(2){""}, optionD = it.options.getOrElse(3){""}, correctOptionIndex = it.correct, explanation = "") }
        repo.addMcqs(entities)
        _s.value = _s.value.copy(loading = false, result = list)
    }
}
