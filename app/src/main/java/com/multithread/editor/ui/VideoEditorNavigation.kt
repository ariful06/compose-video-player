package com.multithread.editor.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.multithread.editor.common.domain.exception.DomainException
import com.multithread.editor.domain.entities.Video
import com.multithread.editor.ui.home.HomeContract
import com.multithread.editor.ui.home.HomeScreen
import com.multithread.editor.ui.player.VideoPlayerScreen
import com.multithread.editor.utils.Constants.ARG_VIDEO
import com.multithread.editor.utils.JsonConverter
import kotlinx.coroutines.CoroutineScope
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

private const val ROOT = "ROOT"

internal sealed class EditorRoute(val route: String) {
    object Root : EditorRoute("$ROOT/")
    object Home : EditorRoute("$ROOT/home")
    object ToPlayer : EditorRoute(
        "$ROOT/player?" +
                "$ARG_VIDEO={$ARG_VIDEO}"
    ) {
        fun createRoute(
            video: String,
        ): String {
            return "$ROOT/player?" +
                    "$ARG_VIDEO=$video"
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun VideoEditorNavigation(
    activity: Activity,
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    onError: (DomainException) -> Unit,
) {

    NavHost(
        navController = navController,
        startDestination = EditorRoute.Root.route,
        builder = {
            editorNavGraph(
                activity = activity,
                navController = navController,
                coroutineScope = coroutineScope,
                onError = onError
            )
        }
    )
}


fun NavGraphBuilder.editorNavGraph(
    activity: Activity,
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    onError: (DomainException) -> Unit,
) {
    navigation(route = EditorRoute.Root.route, startDestination = EditorRoute.Home.route) {

        composable(
            route = EditorRoute.Home.route,
        ) {
            BackHandler {
                activity.finish()
            }
            HomeScreen(
                onError = {

                },
                onNavigationRequested = { navigationEffect ->
                    when (navigationEffect) {
                        is HomeContract.Effect.Navigation.ToVideoPlayer -> {
                            navController.navigate(
                                EditorRoute.ToPlayer.createRoute(
                                    video = URLEncoder.encode(
                                        JsonConverter.toJson(
                                            navigationEffect.video,
                                            Video::class.java
                                        ),
                                        StandardCharsets.UTF_8.name()
                                    ),
                                )
                            )
                        }
                        is HomeContract.Effect.GenericError -> {}
                        HomeContract.Effect.Navigation.NavigateUp -> {
                            activity.finish()
                        }
                    }
                }
            )
        }

        composable(
            route = EditorRoute.ToPlayer.route,
            arguments = listOf(
                navArgument(ARG_VIDEO) { type = NavType.StringType }
            )
        ) {
            VideoPlayerScreen(onError = {}) { navigationEffect ->
                navController.navigateUp()
            }
        }
    }
}