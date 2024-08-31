package edu.ws2024.a01.am.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
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
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.ws2024.a01.am.R
import edu.ws2024.a01.am.navigation.Routes
import edu.ws2024.a01.am.ui.components.BackgroundImageScreen
import edu.ws2024.a01.am.ui.components.MyImage

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImageScreen()

        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            MyImage(
                image = R.drawable.bottom_bg,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "French dessert\ncollector",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 34.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))


            Button(
                onClick = dropUnlessResumed {
                    navController.navigate(Routes.UsernameScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text(text = "Game Start", fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(50.dp))

            MyImage(
                image = R.drawable.bottom_bg,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun Prev() {
    HomeScreen()
}