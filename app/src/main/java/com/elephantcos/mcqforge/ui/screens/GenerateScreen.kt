package com.elephantcos.mcqforge.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elephantcos.mcqforge.viewmodel.GenerateViewModel
@Composable fun GenerateScreen(nav: NavController, vm: GenerateViewModel = viewModel()) {
    val s by vm.state.collectAsState()
    Scaffold(
        topBar = { TopAppBar(title = { Text("MCQ তৈরি") }, navigationIcon = { IconButton(onClick = { nav.popBackStack() }) { Icon(Icons.Filled.ArrowBack, null) } }) }
    ) { pad ->
        Column(Modifier.fillMaxSize().padding(pad).padding(16.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedTextField(s.title, { vm.setTitle(it) }, label = { Text("শিরোনাম") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(s.text, { vm.setText(it) }, label = { Text("লেখা পেস্ট করুন") }, modifier = Modifier.fillMaxWidth().heightIn(min = 200.dp), maxLines = 15)
            Button(onClick = { vm.generate() }, Modifier.fillMaxWidth(), enabled = s.text.split("\\s+".toRegex()).size >= 10 && !s.loading) {
                if (s.loading) { CircularProgressIndicator(Modifier.size(24.dp), strokeWidth = 2.dp); Spacer(Modifier.width(12.dp)); Text("তৈরি হচ্ছে...") }
                // AutoAwesome → material-icons-extended এ পাওয়া যায়, এখন dependency আছে
                else { Icon(Icons.Filled.AutoAwesome, null); Spacer(Modifier.width(8.dp)); Text("MCQ তৈরি করুন") }
            }
            if (s.result.isNotEmpty()) { Text("তৈরি হয়েছে: ${s.result.size} টি প্রশ্ন", style = MaterialTheme.typography.titleMedium) }
            s.result.forEach { mcq ->
                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                    Column(Modifier.padding(16.dp)) {
                        Text(mcq.question, style = MaterialTheme.typography.bodyLarge)
                        Spacer(Modifier.height(8.dp))
                        mcq.options.forEachIndexed { i, opt -> Text("${'A'+i}) $opt ${if(i==mcq.correct)"✓" else ""}", color = if(i==mcq.correct) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface) }
                    }
                }
            }
        }
    }
}
