package com.elephantcos.mcqforge.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elephantcos.mcqforge.ui.components.McqCard
import com.elephantcos.mcqforge.ui.navigation.Routes
import com.elephantcos.mcqforge.viewmodel.HomeViewModel
@Composable fun HomeScreen(nav: NavController, vm: HomeViewModel = viewModel()) {
    // Flow<List<...>> এ initial = emptyList() দিতে হয়, StateFlow হলে লাগত না
    val mcqs by vm.mcqs.collectAsState(initial = emptyList())
    Scaffold(
        topBar = { TopAppBar(title = { Text("MCQ Forge") }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer), actions = { IconButton(onClick = { nav.navigate(Routes.Help) }) { Icon(Icons.Filled.Info, "Help") } }) },
        floatingActionButton = { ExtendedFloatingActionButton(onClick = { nav.navigate(Routes.Generate) }, icon = { Icon(Icons.Filled.Add, null) }, text = { Text("নতুন MCQ") }) }
    ) { pad ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(pad).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                    Column(Modifier.padding(16.dp)) {
                        Text("মোট প্রশ্ন: ${mcqs.size}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { nav.navigate(Routes.Practice) }) { Icon(Icons.Filled.PlayArrow, null); Spacer(Modifier.width(8.dp)); Text("প্র্যাকটিস শুরু") }
                    }
                }
            }
            items(mcqs.take(10)) { McqCard(it, onDelete = { vm.delete(it.id) }) }
        }
    }
}
