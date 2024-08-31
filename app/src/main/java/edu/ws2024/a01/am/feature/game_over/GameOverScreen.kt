package edu.ws2024.a01.am.feature.game_over

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.ws2024.a01.am.R
import edu.ws2024.a01.am.navigation.Routes
import edu.ws2024.a01.am.ui.components.MyImage

@SuppressLint("DefaultLocale")
@Composable
fun GameOverScreen(
    baguettePoints: Int,
    macaronPoints: Int,
    puffPoints: Int,
    fihshbonePoints: Int,
    bonePoints: Int,
    secondsTime: Int,
    totalScore: Int,
    username: String,
    navController: NavHostController = rememberNavController(),
    gameOverViewModel: GameOverViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        gameOverViewModel.insertRanking(username, secondsTime, totalScore)
    }

    val minutes = secondsTime / 60
    val secondsRemaining = secondsTime % 60

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyImage(
            image = R.drawable.bottom_bg,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        GameOverTitle()

        ItemDetailView(R.drawable.baguette_box, true, (baguettePoints / 10), baguettePoints)
        ItemDetailView(R.drawable.macaron_box, true, (macaronPoints / 20), macaronPoints)
        ItemDetailView(R.drawable.puff_box, true, (puffPoints / 30), puffPoints)
        ItemDetailView(R.drawable.fishbone_box, false, (fihshbonePoints / 20), fihshbonePoints)
        ItemDetailView(R.drawable.bone_box, false, (bonePoints / 10), bonePoints)

        ItemTime("TIME", String.format("%02d:%02d", minutes, secondsRemaining))
        ItemTime("SCORE", totalScore.toString())

        Button(
            onClick = dropUnlessResumed {
                navController.navigate(Routes.RankingScreen.route) {
                    popUpTo(Routes.GameOverScreen.route) { inclusive = true }
                }
            },
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 24.dp)
                .align(Alignment.Start)
        ) {
            Text(text = "Next", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Icon(imageVector = Icons.AutoMirrored.Filled.NavigateNext, contentDescription = null)
        }

        Spacer(modifier = Modifier.weight(1f))
        GameOverBottomBar(Modifier.align(Alignment.End))
    }
}


@Composable
fun ItemTime(title: String, complement: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = complement,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            fontSize = 28.sp
        )
    }
}


@Composable
fun ItemDetailView(image: Int, isPositive: Boolean, amountItems: Int, itemPoints: Int) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 2.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        MyImage(image = image, modifier = Modifier.size(70.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "x$amountItems",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = if (isPositive) "+ $itemPoints" else "- $itemPoints",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}


@Composable
fun GameOverTitle() {
    Text(
        text = "Game over",
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        color = Color.Black,
        modifier = Modifier.padding(vertical = 24.dp, horizontal = 12.dp),
        textAlign = TextAlign.Center
    )
}


@Composable
fun GameOverBottomBar(modifier: Modifier = Modifier) {
    MyImage(
        image = R.drawable.player,
        modifier = modifier.size(100.dp)
    )
    MyImage(
        image = R.drawable.bottom_bg,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier.fillMaxWidth()
    )
}


@Preview(showSystemUi = true)
@Composable
private fun Prev() {
    GameOverScreen(
        baguettePoints = 30,
        macaronPoints = 100,
        puffPoints = 30,
        fihshbonePoints = 20,
        bonePoints = 25,
        secondsTime = 299,
        totalScore = 115,
        username = "Alexis"
    )
}