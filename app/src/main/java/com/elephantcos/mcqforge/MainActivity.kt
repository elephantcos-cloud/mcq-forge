package com.elephantcos.mcqforge
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.elephantcos.mcqforge.ui.navigation.NavGraph
import com.elephantcos.mcqforge.ui.theme.McqForgeTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { McqForgeTheme { NavGraph() } }
    }
}
