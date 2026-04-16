package com.worktrack.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.worktrack.ui.screens.ActivityScreen
import com.worktrack.ui.screens.AddEntryScreen
import com.worktrack.ui.screens.EditEntryScreen
import com.worktrack.ui.screens.EntryListScreen
import com.worktrack.ui.screens.EntryOptionsScreen
import com.worktrack.ui.screens.HistoryScreen
import com.worktrack.ui.screens.HomeScreen
import com.worktrack.ui.screens.JobListScreen
import com.worktrack.ui.screens.TimerScreen
import com.worktrack.ui.screens.WelcomeScreen

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Home : Screen("home")
    object AddEntry : Screen("add_entry/{jobId}"){
        fun createRoute(jobId: Long) = "add_entry/$jobId"
    }
    object Activity : Screen("activity")
    object History : Screen("history")
    object JobList : Screen("job_list/{companyId}") {
        fun createRoute(companyId: Long) = "job_list/$companyId"
    }
    object EntryOptions : Screen("entry_options/{jobId}") {
        fun createRoute(jobId: Long) = "entry_options/$jobId"
    }
    object EntryList : Screen("entry_list/{jobId}") {
        fun createRoute(jobId: Long) = "entry_list/$jobId"
    }
    object EditEntry : Screen("edit_entry/{entryId}")

}

    @Composable
    fun AppNavGraph() {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Screen.Welcome.route
        ) {

            composable(Screen.Welcome.route) {
                WelcomeScreen(
                    onStartClick = {
                        navController.navigate(Screen.Home.route)
                    }
                )
            }

            composable(Screen.Home.route) {
                HomeScreen(
                    onAddClick = {
                        navController.navigate(Screen.Activity.route)
                    },
                    onCompanyClick = {
                            companyId -> navController.navigate(Screen.JobList.createRoute(companyId))
                    }
                )
            }

            composable(
                route =Screen.AddEntry.route,
                arguments = listOf(navArgument("jobId") { type = NavType.LongType })
            ) {
                backStackEntry ->

                val jobId = backStackEntry.arguments?.getLong("jobId") ?: 0L

                AddEntryScreen(
                    jobId = jobId,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Screen.Activity.route) {
                ActivityScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Screen.History.route) {
                HistoryScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.JobList.route,
                arguments = listOf(navArgument("companyId") { type = NavType.LongType })
            ) { backStackEntry ->

                val companyId = backStackEntry.arguments?.getLong("companyId") ?: 0L

                JobListScreen(
                    companyId = companyId,
                    onBack = { navController.popBackStack() },
                    onJobClick = { jobId ->
                        navController.navigate(Screen.EntryList.createRoute(jobId))
                    },
                    onAddEntryClick = { jobId ->
                        navController.navigate(Screen.EntryOptions.createRoute(jobId))
                    }
                )
            }

            composable(
                route = Screen.EntryOptions.route,
                arguments = listOf(navArgument("jobId") { type = NavType.LongType })
            ) { backStackEntry ->

                val jobId = backStackEntry.arguments?.getLong("jobId") ?: 0L

                EntryOptionsScreen(
                    jobId = jobId,
                    onBack = { navController.popBackStack() },
                    onManualClick = {
                        navController.navigate(Screen.AddEntry.createRoute(jobId))
                    },
                    onTimerClick = {
                        navController.navigate("timer/$jobId")
                    }
                )
            }

            composable(
                route = "timer/{jobId}",
                arguments = listOf(navArgument("jobId") { type = NavType.LongType })
            ) { backStackEntry ->

                val jobId = backStackEntry.arguments?.getLong("jobId") ?: 0L

                TimerScreen(
                    jobId = jobId,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.EntryList.route,
                arguments = listOf(navArgument("jobId") { type = NavType.LongType })
            ) { backStackEntry ->

                val jobId = backStackEntry.arguments?.getLong("jobId") ?: 0L

                EntryListScreen(
                    jobId = jobId,
                    onBack = { navController.popBackStack()},
                    onEditClick = { entryId ->
                        navController.navigate("edit_entry/$entryId")
                    }
                )
            }

            composable(
                route = Screen.EditEntry.route,
                arguments = listOf(navArgument("entryId") { type = NavType.LongType })
            ) { backStackEntry ->

                val entryId = backStackEntry.arguments?.getLong("entryId") ?: 0L

                EditEntryScreen(
                    entryId = entryId,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
