package com.example.yportfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.yportfolio.data.NoteDatabase
import com.example.yportfolio.data.NoteRepository
import com.example.yportfolio.ui.screens.HomeScreen
import com.example.yportfolio.ui.screens.NoteDetailScreen
import com.example.yportfolio.ui.screens.SettingsScreen
import com.example.yportfolio.ui.theme.YportfolioTheme
import com.example.yportfolio.viewmodel.NoteViewModel
import com.example.yportfolio.viewmodel.ThemeMode
import com.example.yportfolio.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

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
                val viewModel: NoteViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            @Suppress("UNCHECKED_CAST")
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

    NavHost(
        navController = navController, 
        startDestination = "home",
        enterTransition = { 
            fadeIn(animationSpec = tween(250)) + slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start, 
                animationSpec = tween(250)
            ) 
        },
        exitTransition = { 
            fadeOut(animationSpec = tween(250)) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start, 
                animationSpec = tween(250)
            ) 
        },
        popEnterTransition = { 
            fadeIn(animationSpec = tween(250)) + slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End, 
                animationSpec = tween(250)
            ) 
        },
        popExitTransition = { 
            fadeOut(animationSpec = tween(250)) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End, 
                animationSpec = tween(250)
            ) 
        }
    ) {
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
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: -1

            NoteDetailScreen(
                noteId = if (noteId == -1) null else noteId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
