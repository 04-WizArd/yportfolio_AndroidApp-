package com.example.yportfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yportfolio.ui.screens.HomeScreen
import com.example.yportfolio.ui.screens.NoteDetailScreen
import com.example.yportfolio.ui.theme.YportfolioTheme
import com.example.yportfolio.viewmodel.NoteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YportfolioTheme {
                NoteApp()
            }
        }
    }
}

@Composable
fun NoteApp() {
    val navController = rememberNavController()
    val viewModel: NoteViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onNoteClick = { id -> navController.navigate("detail/$id") },
                onAddNoteClick = { navController.navigate("detail/new") }
            )
        }
        composable("detail/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")
            NoteDetailScreen(
                noteId = if (noteId == "new") null else noteId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}