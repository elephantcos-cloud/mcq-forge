package com.elephantcos.mcqforge.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.elephantcos.mcqforge.McqForgeApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
data class PracState(val count: Int = 10, val started: Boolean = false, val idx: Int = 0, val sel: Int = -1, val current: McqView? = null, val progress: Float = 0f)
data class McqView(val question: String, val optA: String, val optB: String, val optC: String, val optD: String, val correctIdx: Int)
class PracticeViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = (app as McqForgeApp).repository
    private val _s = MutableStateFlow(PracState()); val state: StateFlow<PracState> = _s
    fun setCount(n: Int) { _s.value = _s.value.copy(count = n) }
    fun start() = viewModelScope.launch {
        val list = repo.randomMcqs(_s.value.count).first()
        if (list.isNotEmpty()) {
            val q = list[0]; _s.value = _s.value.copy(started = true, idx = 0, current = McqView(q.questionText, q.optionA, q.optionB, q.optionC, q.optionD, q.correctOptionIndex))
        }
    }
    fun select(opt: Int) { _s.value = _s.value.copy(sel = opt) }
    fun next() { _s.value = _s.value.copy(idx = _s.value.idx + 1, sel = -1, progress = (_s.value.idx + 1f) / _s.value.count) }
}
