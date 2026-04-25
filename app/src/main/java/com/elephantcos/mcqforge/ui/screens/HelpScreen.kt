package com.elephantcos.mcqforge.ui.screens
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
@Composable fun HelpScreen(nav: NavController) {
    Scaffold(topBar = { TopAppBar(title = { Text("সাহায্য") }, navigationIcon = { IconButton(onClick = { nav.popBackStack() }) { Icon(Icons.Filled.ArrowBack, null) } }) }) { pad ->
        AndroidView(factory = { ctx -> WebView(ctx).apply { webViewClient = WebViewClient(); loadUrl("file:///android_asset/help.html") } }, modifier = Modifier.fillMaxSize())
    }
}
