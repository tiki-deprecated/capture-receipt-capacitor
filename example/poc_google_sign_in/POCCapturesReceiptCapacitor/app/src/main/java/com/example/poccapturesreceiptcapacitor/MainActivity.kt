package com.example.poccapturesreceiptcapacitor

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.poccapturesreceiptcapacitor.ui.theme.POCCapturesReceiptCapacitorTheme
import com.microblink.core.InitializeCallback
import com.microblink.digital.BlinkReceiptDigitalSdk
import com.microblink.digital.GmailClient


class MainActivity : ComponentActivity() {
    val googleId ="463504001942-sbanru86vdf0da2jh38rl769gug0togi.apps.googleusercontent.com"

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        val gmailClient = GmailClient(this, googleId)
//        gmailClient.onAccountAuthorizationActivityResult(requestCode, resultCode, data)
//            .addOnSuccessListener { signInAccount ->
//
//            }.addOnFailureListener {
//                //Set error display
//            }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            POCCapturesReceiptCapacitorTheme {
                Greeting(this, googleId)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun Greeting( activity: MainActivity, googleId: String,modifier: Modifier = Modifier) {

    val statusInit = remember { mutableStateOf("Fez nada esse carai") }
    val errorInit = remember { mutableStateOf("") }
    val respInit  = remember { mutableStateOf(0) }

    val statusLogin = remember { mutableStateOf("Fez nada esse carai") }
    val errorLogin = remember { mutableStateOf("") }
    val respLogin  = remember { mutableStateOf(0) }

    val statusVerify = remember { mutableStateOf("Fez nada esse carai") }
    val errorVerify = remember { mutableStateOf("") }
    val respVerify  = remember { mutableStateOf(0) }
    val accountVerify = remember { mutableStateOf("") }

    val statusScan = remember { mutableStateOf("Fez nada esse carai") }
    val errorScan = remember { mutableStateOf("") }
    val respScan  = remember { mutableStateOf(0) }
    val accountScan = remember { mutableStateOf("") }

    val statusLogout = remember { mutableStateOf("Fez nada esse carai") }
    val errorLogout = remember { mutableStateOf("") }
    val respLogout  = remember { mutableStateOf(0) }

    val gmailSignIn = GoogleSignIn()


    val brush = remember {
        Brush.linearGradient(
            colors = listOf(
                Color(0xFFe81416),
                Color(0xFFffa500),
                Color(0xFFfaeb36),
                Color(0xFF79c314),
                Color(0xFF487de7),
                Color(0xFF4b369d),
                Color(0xFF70369d),
            )
        )
    }
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 10.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(70.dp))
            Text(
                text = "Teste desse carai",
                modifier = modifier,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(70.dp))

//            init
            Text(
                text = statusInit.value,
                modifier = modifier,
                color = if (respInit.value == 0) MaterialTheme.colorScheme.primary else if (respInit.value == 1) Color.Green else MaterialTheme.colorScheme.tertiary
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (respInit.value == 2) {
                Text(
                    text = errorInit.value,
                    modifier = modifier,
                    color = Color.Red,
                    fontSize = 10.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable {
                        gmailSignIn.Initializer(activity ,statusInit, errorInit, respInit, googleId)
                    },
                border = BorderStroke(2.dp, brush)

            ){
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Iniciar esse carai",
                        modifier = modifier,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))


//            Login

            Text(
                text = statusLogin.value,
                modifier = modifier,
                color = if (respLogin.value == 0) MaterialTheme.colorScheme.primary else if (respLogin.value == 1) Color.Green else MaterialTheme.colorScheme.tertiary
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (respLogin.value == 2) {
                Text(
                    text = errorLogin.value,
                    modifier = modifier,
                    color = Color.Red,
                    fontSize = 10.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable {
                        gmailSignIn.login(activity, statusLogin, errorLogin, respLogin)
                    },
                border = BorderStroke(2.dp, brush)

            ){
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Logar esse carai",
                        modifier = modifier,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))


//            Verify

            Text(
                text = statusVerify.value,
                modifier = modifier,
                color = if (respVerify.value == 0) MaterialTheme.colorScheme.primary else if (respVerify.value == 1) Color.Green else MaterialTheme.colorScheme.tertiary
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (respVerify.value == 1) {
                Text(
                    text = accountVerify.value,
                    modifier = modifier,
                    color = Color.Green,
                    fontSize = 10.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            if (respVerify.value == 2) {
                Text(
                    text = errorVerify.value,
                    modifier = modifier,
                    color = Color.Red,
                    fontSize = 10.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable {
                        gmailSignIn.verifyUser(activity, statusVerify, errorVerify, respVerify, accountVerify)
                    },
                border = BorderStroke(2.dp, brush)

            ){
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Verificar esse carai",
                        modifier = modifier,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

//          Scan
            Text(
                text = statusScan.value,
                modifier = modifier,
                color = if (respScan.value == 0) MaterialTheme.colorScheme.primary else if (respScan.value == 1) Color.Green else MaterialTheme.colorScheme.tertiary
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (respScan.value == 1) {
                Text(
                    text = accountScan.value,
                    modifier = modifier,
                    color = Color.Green,
                    fontSize = 10.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            if (respScan.value == 2) {
                Text(
                    text = errorScan.value,
                    modifier = modifier,
                    color = Color.Red,
                    fontSize = 10.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable {
                        gmailSignIn.scan(activity, statusScan, errorScan, respScan, accountScan)
                    },
                border = BorderStroke(2.dp, brush)

            ){
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Scaniar esse carai",
                        modifier = modifier,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

//          Logout
            Text(
                text = statusLogout.value,
                modifier = modifier,
                color = if (respLogout.value == 0) MaterialTheme.colorScheme.primary else if (respLogout.value == 1) Color.Green else MaterialTheme.colorScheme.tertiary
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (respLogout.value == 2) {
                Text(
                    text = errorLogout.value,
                    modifier = modifier,
                    color = Color.Red,
                    fontSize = 10.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable {
                        gmailSignIn.logoutUser(activity, statusLogout, errorLogout, respLogout)
                    },
                border = BorderStroke(2.dp, brush)

            ){
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Scaniar esse carai",
                        modifier = modifier,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

        }
    }
}


