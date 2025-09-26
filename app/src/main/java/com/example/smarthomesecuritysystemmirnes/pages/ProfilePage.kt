package com.example.smarthomesecuritysystemmirnes.pages
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smarthomesecuritysystemmirnes.R
import com.example.smarthomesecuritysystemmirnes.viewmodel.ProfileViewModel


@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProfileViewModel = viewModel()
) {
    val userModel by viewModel.userModel.collectAsState()

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Your profile",
                style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "profile icon",
                modifier = Modifier.height(200.dp).fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = userModel.name,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text("Email:", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Text(text = userModel.email)
        }

        TextButton(
            onClick = {
                viewModel.signOut()
                navController.popBackStack()
                navController.navigate("auth")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sign out", fontSize = 18.sp)
        }
    }
}
