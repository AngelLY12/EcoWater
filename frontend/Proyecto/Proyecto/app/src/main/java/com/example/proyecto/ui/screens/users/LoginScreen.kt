package com.example.login

import android.content.Context
import android.util.Log
import android.util.Patterns
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
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.sp
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
import com.example.proyecto.ui.theme.loginColor
import com.example.proyecto.ui.theme.mainColor
import com.example.proyecto.ui.viewModels.ToastViewModel
import com.google.firebase.messaging.FirebaseMessaging


@Composable
fun LoginScreen(navController: NavHostController, toastViewModel: ToastViewModel) {

    val context = LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val valid = email.isNotBlank() && password.isNotBlank()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = loginColor)
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
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Bienvenido", fontSize = 24.sp, fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it

                    },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )


                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it

                                    },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    enabled = !isLoading,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
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
                    color = Color(0xFF0E94E7),
                    fontSize = 14.sp,
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
                                saveLoginState(email, context)
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
                                                // Opcional: Reintentar o guardar el token localmente para enviar después
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
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp)
                ) {
                    Text("Iniciar Sesión")
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    enabled = !isLoading,
                    onClick = { navController.navigate("register") },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = mainColor),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, mainColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp)

                ) {
                    Text("Registrarse")
                }
            }
        }

    }
}
private fun saveLoginState(email: String, context: Context) {
    val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    prefs.edit().putString("user_email", email).apply()
}
