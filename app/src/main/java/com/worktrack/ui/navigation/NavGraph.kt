@file:OptIn(ExperimentalMaterial3Api::class)

package com.worktrack.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

sealed class Screen(val route: String) {
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

    object Timer : Screen("timer/{jobId}") {
        fun createRoute(jobId: Long) = "timer/$jobId"
    }

}

    @Composable
    fun AppNavGraph() {
        val navController = rememberNavController()

        val currentBackStackEntry = navController.currentBackStackEntryAsState()
        val mainColor = Color(0xFF3A5A98)

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(getTitle(currentBackStackEntry.value?.destination?.route))
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = mainColor,
                        titleContentColor = Color.White)
                )
            },
            containerColor = mainColor.copy(alpha = 0.25f)
        ) { paddingValues ->

            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(
                        onAddClick = {
                            navController.navigate(Screen.Activity.route)
                        },
                        onCompanyClick = { companyId ->
                            navController.navigate(Screen.JobList.createRoute(companyId))
                        },
                        onHistoryClick = {
                            navController.navigate(Screen.History.route)
                        }
                    )
                }

                composable(
                    route = Screen.AddEntry.route,
                    arguments = listOf(navArgument("jobId") { type = NavType.LongType })
                ) { backStackEntry ->

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
                        onBack = { navController.popBackStack() },
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

                composable(
                    route = Screen.Timer.route,
                    arguments = listOf(navArgument("jobId") { type = NavType.LongType })
                ) { backStackEntry ->

                    val jobId = backStackEntry.arguments?.getLong("jobId") ?: 0L

                    TimerScreen(
                        jobId = jobId,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }


fun getTitle(route: String?): String {
    return when {
        route?.startsWith(Screen.Home.route) == true -> "FIRMY"
        route?.startsWith(Screen.JobList.route) == true -> "ZLECENIA"
        route?.startsWith(Screen.EntryList.route) == true -> "GODZINY"
        route?.startsWith(Screen.History.route) == true -> "HISTORIA"
        route?.startsWith(Screen.Activity.route) == true -> "NOWA FIRMA"
        route?.startsWith(Screen.EntryOptions.route) == true -> "DODAJ GODZINY"
        route?.startsWith(Screen.AddEntry.route) == true -> "DODAJ GODZINY"
        route?.startsWith(Screen.EditEntry.route) == true -> "EDYTUJ WPIS"
        route?.startsWith(Screen.Timer.route) == true -> "TIMER"
        else -> "     "
    }
}
