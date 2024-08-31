package edu.ws2024.a01.am.feature.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.ws2024.a01.am.R
import edu.ws2024.a01.am.navigation.Routes
import edu.ws2024.a01.am.ui.components.MyImage
import edu.ws2024.a01.am.ui.theme.BlackAlpha2

@Composable
fun GameScreen(
    username: String,
    navController: NavHostController = rememberNavController(),
    gameViewModel: GameViewModel = hiltViewModel()
) {
    val startNextScreen by gameViewModel.startNextScreen.observeAsState(false)
    if (startNextScreen) {
        LaunchedEffect(key1 = Unit) {
            gameViewModel.navigateToGameOverScreen(navController, username)
        }
    }

    val playerScore by gameViewModel.playerScore.observeAsState(0)
    val playerHealth by gameViewModel.playerHealth.observeAsState(3)
    val time by gameViewModel.time.observeAsState("00:30")

    val isPauseScreenVisible by gameViewModel.isPauseScreenVisible.observeAsState(false)
    val isRestartDialogVisible by gameViewModel.isRestartDialogVisible.observeAsState(false)
    val isQuitDialogVisible by gameViewModel.isQuitDialogVisible.observeAsState(false)

    Box(modifier = Modifier.fillMaxSize()) {
        GameBackground()

        Column(Modifier.fillMaxSize()) {
            GameTopBar(playerScore, playerHealth, time) { gameViewModel.showPauseScreen() }

            UserGameInteractionsArea(gameViewModel, Modifier.weight(1f))

            GameBottomBar()
        }
    }

    BackHandler { gameViewModel.showPauseScreen() }

    if (isRestartDialogVisible) {
        RestartDialog(
            onNoButtonClick = { gameViewModel.hideRestartDialog() },
            onYesButtonClick = {
                gameViewModel.hideRestartDialog()
                navController.navigate(Routes.GameScreen.createRoute(username)) {
                    popUpTo(Routes.GameScreen.route) { inclusive = true }
                }
            }
        )

    } else if (isQuitDialogVisible) {
        QuitDialog(
            onNoButtonClick = { gameViewModel.hideQuitDialog() },
            onYesButtonClick = {
                gameViewModel.hideQuitDialog()
                navController.navigateUp()
            }
        )

    } else if (isPauseScreenVisible) {
        PauseScreen(
            onContinueButtonClick = { gameViewModel.hidePauseScreen() },
            onRestartButtonClick = { gameViewModel.showRestartDialog() },
            onQuitButtonClick = { gameViewModel.showQuitDialog() }
        )
    }
}


@Composable
fun QuitDialog(onNoButtonClick: () -> Unit, onYesButtonClick: () -> Unit) {
    Dialog(onDismissRequest = { }) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        ) {
            Text(
                text = "Whether to abandon the current progress and quit the game",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(
                    onClick = { onNoButtonClick() },
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "No")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = dropUnlessResumed { onYesButtonClick() },
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Yes")
                }
            }
        }
    }
}


@Composable
fun RestartDialog(onNoButtonClick: () -> Unit, onYesButtonClick: () -> Unit) {
    Dialog(onDismissRequest = { }) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        ) {
            Text(
                text = "Whether to abandon the current progress and restart the game",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(
                    onClick = { onNoButtonClick() },
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "No")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = dropUnlessResumed { onYesButtonClick() },
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Yes")
                }
            }
        }
    }
}


@Composable
fun PauseScreen(
    onContinueButtonClick: () -> Unit,
    onRestartButtonClick: () -> Unit,
    onQuitButtonClick: () -> Unit
) {
    Dialog(onDismissRequest = { }) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Game pause",
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(20.dp))

            MyImage(
                image = R.drawable.play,
                modifier = Modifier
                    .size(50.dp)
                    .clickable { onContinueButtonClick() },
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(20.dp))

            MyImage(
                image = R.drawable.restart,
                modifier = Modifier
                    .size(50.dp)
                    .clickable { onRestartButtonClick() },
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(20.dp))

            MyImage(
                image = R.drawable.out,
                modifier = Modifier
                    .size(50.dp)
                    .clickable { onQuitButtonClick() },
                contentScale = ContentScale.FillBounds
            )
        }
    }
}


@Composable
fun GameBottomBar() {
    Column {
        MyImage(
            image = R.drawable.bottom_bg,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )
        MyImage(
            image = R.drawable.bottom_bg,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun UserGameInteractionsArea(gameViewModel: GameViewModel, modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    LaunchedEffect(key1 = Unit) { gameViewModel.initItems(configuration.screenWidthDp.toFloat()) }
    var boxHeight by rememberSaveable { mutableFloatStateOf(configuration.screenHeightDp.toFloat()) }

    val playerPositionX by gameViewModel.playerPositionX.observeAsState(0f)
    val itemList = gameViewModel.itemList

    Box(
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val newPositionX = playerPositionX + dragAmount.x / 2
                    val maxWidthSize = getMaxSizeFloat(size.width.toDp()) - 140
                    gameViewModel.movePlayer(newPositionX.coerceIn(0f, maxWidthSize))
                }
            }
            .onGloballyPositioned { layoutCoordinates ->
                val boxSizePx = layoutCoordinates.size
                val boxSizeDp = with(density) {
                    DpSize(width = boxSizePx.width.toDp(), height = boxSizePx.height.toDp())
                }
                boxHeight = getMaxSizeFloat(boxSizeDp.height)
            }
    ) {
        itemList.forEach { gameItem ->
            if (gameItem.positionY >= (boxHeight - 40)) {
                gameViewModel.checkItemFall(gameItem)

            } else if (gameItem.positionY >= (boxHeight - 160)) {
                if (gameItem.positionX >= (playerPositionX - 20) && (gameItem.positionX <= (playerPositionX + 90))) {
                    gameViewModel.checkItemCollected(gameItem)
                }
            }

            MyImage(
                image = gameItem.image,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(y = gameItem.positionY.dp, x = gameItem.positionX.dp)
                    .size(40.dp),
                contentScale = ContentScale.FillBounds
            )
        }

        MyImage(
            image = R.drawable.player,
            modifier = Modifier
                .size(140.dp)
                .align(Alignment.BottomStart)
                .offset(x = playerPositionX.dp)
        )
    }
}

fun getMaxSizeFloat(sizeDp: Dp): Float {
    return sizeDp.toString().replace(".dp", "").toFloat()
}


@Composable
fun GameTopBar(playerScore: Int, playerHealth: Int, time: String, onPauseButtonClick: () -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        MyImage(
            image = R.drawable.bottom_bg,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScoreView(playerScore)
            Spacer(modifier = Modifier.weight(1f))
            MyImage(image = R.drawable.stop, modifier = Modifier
                .size(50.dp)
                .clickable { onPauseButtonClick() }
            )
        }

        HealthView(playerHealth)

        Text(
            text = "Time : $time",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun HealthView(playerHealth: Int) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 16.dp)) {
        MyImage(
            image = R.drawable.title_bg,
            modifier = Modifier
                .width(120.dp)
                .height(50.dp),
            contentScale = ContentScale.FillBounds
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            MyImage(
                image = R.drawable.life,
                modifier = Modifier.size(30.dp),
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "x$playerHealth",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun ScoreView(playerScore: Int) {
    Box(contentAlignment = Alignment.Center) {
        MyImage(
            image = R.drawable.title_bg,
            modifier = Modifier
                .width(140.dp)
                .height(50.dp),
            contentScale = ContentScale.FillBounds
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            MyImage(
                image = R.drawable.board,
                modifier = Modifier.size(30.dp),
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = playerScore.toString(),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}


@Composable
fun GameBackground() {
    MyImage(
        image = R.drawable.bg,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackAlpha2)
    )
}


@Preview(showSystemUi = true)
@Composable
private fun Prev() {
    GameScreen(username = "Alexis")
}