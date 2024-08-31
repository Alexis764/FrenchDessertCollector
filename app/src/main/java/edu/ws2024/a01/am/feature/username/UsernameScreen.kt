package edu.ws2024.a01.am.feature.username

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
import edu.ws2024.a01.am.ui.components.BackgroundImageScreen
import edu.ws2024.a01.am.ui.components.MyImage

@Composable
fun UsernameScreen(
    navController: NavHostController = rememberNavController(),
    usernameViewModel: UsernameViewModel = hiltViewModel()
) {
    val username by usernameViewModel.username.observeAsState("")
    val isError by usernameViewModel.isError.observeAsState(false)

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
                text = "Player Name",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 34.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            UsernameTextField(username, isError) {
                usernameViewModel.onUsernameChanged(it)
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = dropUnlessResumed {
                    if (username.trim().isNotEmpty()) {
                        navController.navigate(Routes.GameScreen.createRoute(username)) {
                            popUpTo(Routes.UsernameScreen.route) {
                                inclusive = true
                            }
                        }

                    } else {
                        usernameViewModel.showIsError()
                    }
                },
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(
                    text = "Start",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.Black
                )
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


@Composable
fun UsernameTextField(value: String, isError: Boolean, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        label = {
            Text(text = "Input your name")
        },
        supportingText = {
            if (isError) {
                Text(text = "Username required")
            }
        },
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            unfocusedLabelColor = Color.White,
            focusedLabelColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color.White
        )
    )
}


@Preview(showSystemUi = true)
@Composable
private fun Prev() {
    UsernameScreen()
}