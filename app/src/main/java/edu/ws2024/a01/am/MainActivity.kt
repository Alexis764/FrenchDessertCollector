package edu.ws2024.a01.am

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import edu.ws2024.a01.am.navigation.MyNavigation
import edu.ws2024.a01.am.ui.theme.ModuleAProjectTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ModuleAProjectTheme {
                Scaffold { innerPadding ->
                    MyNavigation(Modifier.padding(innerPadding))
                }
            }
        }
    }
}


