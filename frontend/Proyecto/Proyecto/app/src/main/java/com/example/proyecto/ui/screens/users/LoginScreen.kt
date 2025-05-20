package com.example.login

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavHostController
import com.example.login.services.UserApiService
import com.example.proyecto.model.auth.AuthRequest
import com.example.proyecto.R
import com.example.proyecto.data.services.AuthApiService
import com.example.proyecto.ui.components.layout.BottomNavItem
import com.example.proyecto.ui.components.custom.ToastType
import com.example.proyecto.ui.screens.users.OutlinedTextFieldLR
import com.example.proyecto.ui.theme.CustomTheme
import com.example.proyecto.ui.viewModels.AuthViewModel


import com.example.proyecto.ui.viewModels.ToastViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.messaging.FirebaseMessaging


@Composable
fun LoginScreen(navController: NavHostController, toastViewModel: ToastViewModel, authViewModel: AuthViewModel) {

    val context = LocalContext.current
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        authViewModel.onGoogleAuthResult(
            result = result,
            context = context,
            onSuccess = {
                toastViewModel.showToast("Inicio con Google exitoso", ToastType.SUCCESS)
                navController.navigate(BottomNavItem.Home.route)
            },
            onError = {
                toastViewModel.showToast(it, ToastType.ERROR)
                Log.e("AUTH_ERROR", "Response: ${it}")

            }
        )
    }

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val valid = email.isNotBlank() && password.isNotBlank()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = CustomTheme.gradient)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.Blue
            )
        }
        Image(
            painter = painterResource(id = R.drawable.title),
            contentDescription = "EcoWater Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Fit
        )

        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = CustomTheme.cardPrimary),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min=300.dp,max=400.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Bienvenido", color = CustomTheme.textOnPrimary, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

                OutlinedTextFieldLR(
                    value = email,
                    onValueChange = {email = it},
                    label = { Text("Correo electrónico") },
                    enabled = !isLoading,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                OutlinedTextFieldLR(
                    value = password,
                    onValueChange = {password = it},
                    label = { Text("Contraseña") },
                    enabled = !isLoading,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible },
                            enabled = !isLoading,
                        ) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

                )
                Text(
                    text = "Olvide mi contraseña",
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 8.dp)
                        .clickable { /* Navegar o mostrar mensaje */ },
                    color = CustomTheme.textOnPrimary,
                    style = MaterialTheme.typography.bodySmall ,
                    textDecoration = TextDecoration.Underline
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {

                        isLoading = true
                        val authRequest = AuthRequest(email = email, password = password)
                        AuthApiService.authUser(authRequest, context) { authResponse ->
                            isLoading = false
                            if (authResponse != null) {
                                //saveLoginState(email, context)
                                FirebaseMessaging.getInstance().token.addOnCompleteListener {
                                        task->
                                    if(task.isSuccessful){
                                        val token= task.result
                                        Log.d("FCM_TOKEN", "Token generado: $token")
                                        UserApiService.updateTokenFMC(token, context) { response ->
                                            if (response != null) {
                                                Log.d("FCM_TOKEN", "Backend actualizado en login")
                                            } else {
                                                Log.e("FCM_TOKEN", "Error al actualizar token en login")
                                            }                                        }
                                    }else {
                                        Log.e("FCM_TOKEN", "Error al obtener token", task.exception)
                                    }
                                }
                                toastViewModel.showToast("Inicio de sesión exitoso", ToastType.SUCCESS)
                                navController.navigate(BottomNavItem.Home.route)
                            } else {
                                toastViewModel.showToast("Credenciales incorrectas", ToastType.ERROR)
                            }
                        }

                    },
                    enabled = !isLoading && valid,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomTheme.normalButton,
                        contentColor = CustomTheme.textPrimary
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp)
                ) {
                    Text("Iniciar Sesión", color = CustomTheme.textPrimary)
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    enabled = !isLoading,
                    onClick = { navController.navigate("register") },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = CustomTheme.cardPrimary),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp,CustomTheme.cardBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp)

                ) {
                    Text("Registrarse", color = CustomTheme.textSecondary)
                }



            }
        }
        Spacer(modifier = Modifier.width(12.dp))

        Button(
            onClick = {

                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("272820450827-0r5goasrqcpid8n14og9g01m5pao7aof.apps.googleusercontent.com") // Reemplaza con el tuyo
                    .requestEmail()
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                googleSignInLauncher.launch(googleSignInClient.signInIntent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = CustomTheme.cardPrimary),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)

            ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = "Google Logo",
                tint = Color.Unspecified,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))

            Text("Iniciar con Google", color = CustomTheme.textPrimary)

        }

    }
}
/*
private fun saveLoginState(email: String, context: Context) {
    val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    prefs.edit().putString("user_email", email).apply()
}

 */
