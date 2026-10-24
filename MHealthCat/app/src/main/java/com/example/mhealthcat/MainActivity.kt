package com.example.mhealthcat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.mhealthcat.data.messages.CatMessageScheduler
import com.example.mhealthcat.data.repositories.AuthRepository
import com.example.mhealthcat.ui.theme.MHealthCatTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MHealthCatTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Screen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        lifecycleScope.launch {
            AuthRepository.currentUser.collect { user ->
                if (user != null) CatMessageScheduler.schedule(this@MainActivity)
                else CatMessageScheduler.cancel(this@MainActivity)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MHealthCatTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Screen()
        }
    }
}

