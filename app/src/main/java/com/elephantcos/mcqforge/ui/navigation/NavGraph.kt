package com.elephantcos.mcqforge.ui.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elephantcos.mcqforge.ui.screens.*
object Routes { const val Home = "home"; const val Generate = "generate"; const val Practice = "practice"; const val Help = "help" }
@Composable fun NavGraph() {
    val nav = rememberNavController()
    NavHost(nav, Routes.Home) {
        composable(Routes.Home) { HomeScreen(nav) }
        composable(Routes.Generate) { GenerateScreen(nav) }
        composable(Routes.Practice) { PracticeScreen(nav) }
        composable(Routes.Help) { HelpScreen(nav) }
    }
}
