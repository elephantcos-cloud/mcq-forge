package com.elephantcos.mcqforge.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elephantcos.mcqforge.viewmodel.PracticeViewModel
@Composable fun PracticeScreen(nav: NavController, vm: PracticeViewModel = viewModel()) {
    val s by vm.state.collectAsState()
    if (s.current == null) {
        Column(Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text("প্রশ্ন সংখ্যা", fontSize = 20.sp)
            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf(5,10,20).forEach { n -> FilterChip(n==s.count, { vm.setCount(n) }, label = { Text("$n") }) }
            }
            Spacer(Modifier.height(24.dp))
            Button({ vm.start() }, Modifier.fillMaxWidth()) { Text("শুরু করুন") }
        }
    } else {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            LinearProgressIndicator(progress = s.progress, Modifier.fillMaxWidth().height(8.dp))
            Spacer(Modifier.height(16.dp))
            Text("প্রশ্ন ${s.idx+1}/${s.count}")
            Spacer(Modifier.height(12.dp))
            val q = s.current!!
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Column(Modifier.padding(20.dp)) {
                    Text(q.question, fontSize = 18.sp)
                    Spacer(Modifier.height(16.dp))
                    listOf(q.optA, q.optB, q.optC, q.optD).forEachIndexed { i, opt ->
                        OutlinedCard(onClick = { vm.select(i) }, Modifier.fillMaxWidth().padding(vertical = 4.dp)) { Text("${'A'+i}) $opt", Modifier.padding(12.dp)) }
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            Button({ vm.next() }, Modifier.fillMaxWidth(), enabled = s.sel != -1) { Text(if(s.idx+1 >= s.count) "শেষ করুন" else "পরবর্তী") }
        }
    }
}
