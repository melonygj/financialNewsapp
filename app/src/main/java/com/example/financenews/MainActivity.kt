package com.example.financenews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.financenews.ui.screens.NewsListScreen
import com.example.financenews.ui.theme.FinanceNewsTheme
import com.example.financenews.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {
    
    private val viewModel: NewsViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinanceNewsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NewsListScreen(viewModel = viewModel)
                }
            }
        }
    }
}
