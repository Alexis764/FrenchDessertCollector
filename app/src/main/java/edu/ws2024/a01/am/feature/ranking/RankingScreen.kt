package edu.ws2024.a01.am.feature.ranking

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
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
import edu.ws2024.a01.am.core.database.RankingEntity
import edu.ws2024.a01.am.navigation.Routes
import edu.ws2024.a01.am.ui.components.MyImage
import edu.ws2024.a01.am.ui.theme.BlackAlpha2

@Composable
fun RankingScreen(
    navController: NavHostController = rememberNavController(),
    rankingViewModel: RankingViewModel = hiltViewModel()
) {
    val rankingList = rankingViewModel.rankingList

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

        RankingTitle()

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(rankingList) { index, item ->
                val position = index + 1
                RankingItemView(position, item)
            }
        }

        RankingBottomBar {
            navController.navigate(Routes.UsernameScreen.route) {
                popUpTo(Routes.RankingScreen.route) { inclusive = true }
            }
        }
    }
}


@SuppressLint("DefaultLocale")
@Composable
fun RankingItemView(position: Int, item: RankingEntity) {
    val minutes = item.secondsTime / 60
    val secondsRemaining = item.secondsTime % 60

    Card(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(containerColor = BlackAlpha2)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            Column(
                Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Rank",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp
                )

                Text(
                    text = position.toString(),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            VerticalDivider()

            Column(
                Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Name: ${item.name}",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    text = "Time: ${String.format("%02d:%02d", minutes, secondsRemaining)}",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    text = "Score: ${item.score}",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}


@Composable
fun RankingTitle() {
    Text(
        text = "Rankings",
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        color = Color.Black,
        modifier = Modifier.padding(vertical = 24.dp, horizontal = 12.dp),
        textAlign = TextAlign.Center
    )
}


@Composable
fun RankingBottomBar(onRetryButtonClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = dropUnlessResumed { onRetryButtonClick() },
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text(text = "Tap To Retry", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.weight(1f))
        MyImage(
            image = R.drawable.player,
            modifier = Modifier.size(100.dp)
        )
    }
    MyImage(
        image = R.drawable.bottom_bg,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier.fillMaxWidth()
    )
}


@Preview(showSystemUi = true)
@Composable
private fun Prev() {
    RankingScreen()
}