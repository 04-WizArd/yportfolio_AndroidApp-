package com.example.yportfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yportfolio.ui.screens.HomeScreen
import com.example.yportfolio.ui.screens.NoteDetailScreen
import com.example.yportfolio.ui.theme.YportfolioTheme
import com.example.yportfolio.viewmodel.NoteViewModel
import com.example.yportfolio.data.NoteDatabase
import com.example.yportfolio.data.NoteRepository
import androidx.navigation.NavType
import androidx.navigation.navArgument

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.yportfolio.ui.screens.SettingsScreen
import com.example.yportfolio.viewmodel.ThemeMode
import com.example.yportfolio.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // Initialisation de Room
        val database = NoteDatabase.getDatabase(this)
        val repository = NoteRepository(database.noteDao())

        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val themeMode by themeViewModel.themeMode.collectAsState()
            
            val darkTheme = when (themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            YportfolioTheme(darkTheme = darkTheme) {
                // On passe le repository au ViewModel
                val viewModel: NoteViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return NoteViewModel(repository) as T
                        }
                    }
                )
                NoteApp(viewModel, themeViewModel)
            }
        }
    }
}

@Composable
fun NoteApp(viewModel: NoteViewModel, themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onNoteClick = { id -> navController.navigate("detail/$id") },
                onAddNoteClick = { navController.navigate("detail/-1") },
                onSettingsClick = { navController.navigate("settings") }
            )
        }
        composable("settings") {
            SettingsScreen(
                viewModel = themeViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "detail/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            // On récupère l'ID en tant qu'Int
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: -1

            NoteDetailScreen(
                // Si l'ID est -1, on passe null (nouvelle note)
                noteId = if (noteId == -1) null else noteId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}