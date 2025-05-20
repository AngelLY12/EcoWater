package com.example.login

import android.content.Context
import android.util.Patterns
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import androidx.core.content.edit
import androidx.navigation.NavHostController
import com.example.login.models.User
import com.example.login.services.UserApiService
import com.example.proyecto.R
import com.example.proyecto.ui.components.indicators.ProgressIndicatorStep
import com.example.proyecto.ui.components.custom.ToastType
import com.example.proyecto.ui.screens.users.OutlinedTextFieldLR
import com.example.proyecto.ui.theme.CustomTheme
import com.example.proyecto.ui.theme.Darkblue
import com.example.proyecto.ui.viewModels.ToastViewModel
import com.google.gson.Gson


@Composable
fun RegisterScreen(navController: NavHostController, toastViewModel: ToastViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var passworConfirmVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("createUser", Context.MODE_PRIVATE)
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var passwordConfirmError by remember { mutableStateOf<String?>(null) }

    val valid = emailError == null && passwordError == null && passwordConfirmError==null &&
            email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = CustomTheme.gradient)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.title),
            contentDescription = "EcoWater Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Fit
        )
        ProgressIndicatorStep(currentStep = 1, totalSteps = 2)
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = CustomTheme.cardPrimary),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(460.dp)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Regístrate", color = CustomTheme.textOnPrimary, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text("Datos de inicio",  color = CustomTheme.textOnPrimary, style = MaterialTheme.typography.titleMedium)


                    OutlinedTextFieldLR(
                        value = email,
                        onValueChange = { email = it
                            emailError = if (email.isBlank()) {
                                "El correo no puede estar vacío"
                            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                "Correo no válido"
                            } else null
                        },
                        label = { Text("Correo electrónico") },
                        isError = emailError != null,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        trailingIcon = {
                            Icon(
                                imageVector = if(emailError!=null) Icons.Filled.Info else Icons.Filled.Check,
                                contentDescription = if(emailError!=null) "Email no valido" else "Email valido"
                            )
                        }
                    )

                    OutlinedTextFieldLR(
                        value = password,
                        onValueChange = { password = it
                            passwordError = if (password.length < 6) {
                                "La contraseña debe tener al menos 6 caracteres"
                            } else null
                        },
                        label = { Text("Contraseña") },
                        isError = passwordError != null,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible },
                            ) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )


                    if (passwordError != null) {
                        Text(passwordError ?: "", color = Color.Red, fontSize = 10.sp)
                    }

                    OutlinedTextFieldLR(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it
                            passwordConfirmError = if (password != confirmPassword) {
                                "Las contraseñas no coinciden"
                            } else null
                        },
                        label = { Text("Confirmar contraseña") },
                        isError = passwordConfirmError != null,
                        visualTransformation = if (passworConfirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passworConfirmVisible = !passworConfirmVisible },
                            ) {
                                Icon(
                                    imageVector = if (passworConfirmVisible) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = if (passworConfirmVisible) "Ocultar contraseña" else "Mostrar contraseña"
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

                    )
                    if (passwordConfirmError != null) {
                        Text(passwordConfirmError ?: "", color = Color.Red, fontSize = 10.sp)
                    }
                    Spacer(modifier = Modifier.height(12.dp))



                    Button(
                        onClick = {
                            when {
                                email.isBlank() || password.isBlank() || confirmPassword.isBlank() ->
                                    toastViewModel.showToast("Por favor, completa todos los campos.", ToastType.ERROR)
                                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                                    toastViewModel.showToast("Correo electrónico no válido.", ToastType.ERROR)

                                password.length < 6 ->
                                    toastViewModel.showToast("La contraseña debe tener al menos 6 caracteres.", ToastType.ERROR)

                                password != confirmPassword ->
                                    toastViewModel.showToast("Las contraseñas no coinciden.", ToastType.ERROR)

                                else -> {
                                    val gson = Gson()
                                    val user = User(email = email, password = password)
                                    val json = gson.toJson(user)
                                    sharedPreferences.edit() { putString("user_data", json) }
                                    navController.navigate("dataUser")
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CustomTheme.normalButton,
                            contentColor = CustomTheme.textPrimary
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(38.dp),
                        enabled = valid
                    ) {
                        Text("Continuar", color = CustomTheme.textPrimary)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = { navController.navigate("login") },
                        colors = ButtonDefaults.outlinedButtonColors(CustomTheme.cardPrimary),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.dp, Darkblue),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(38.dp)
                    ) {
                        Text("Iniciar sesión", color = CustomTheme.textSecondary)
                    }

                }
            }
        }



    }
}



@Composable
fun DataUser(navController: NavHostController, toastViewModel: ToastViewModel) {


    var userName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val gson = Gson()
    val sharedPreferences = context.getSharedPreferences("createUser", Context.MODE_PRIVATE)
    val json = sharedPreferences.getString("user_data", null)
    val userRequest = json?.let { gson.fromJson(it, User::class.java) }

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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Retroceder",
                    tint = Color.White
                )
            }

            Image(
                painter = painterResource(id = R.drawable.title),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Fit

            )
        }

        ProgressIndicatorStep(currentStep = 2, totalSteps = 2)
        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = CustomTheme.cardPrimary),
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
                    Text("Regístrate",color = CustomTheme.textOnPrimary, style = MaterialTheme.typography.titleLarge , fontWeight = FontWeight.Bold)
                    Text("Datos personales", color = CustomTheme.textOnPrimary, style = MaterialTheme.typography.titleMedium)

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextFieldLR(
                        value = userName,
                        enabled = !isLoading,
                        onValueChange = { userName = it },
                        label = { Text("Nombre") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                    )

                    OutlinedTextFieldLR(
                        value = lastName,
                        enabled = !isLoading,
                        onValueChange = { lastName = it },
                        label = { Text("Apellidos") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )

                    OutlinedTextFieldLR(
                        value = age,
                        enabled = !isLoading,
                        onValueChange = { age = it },
                        label = { Text("Edad") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

                    )

                    Spacer(modifier = Modifier.height(12.dp))


                    Button(
                        onClick = {
                            isLoading = true
                            val userAge = age.toIntOrNull()
                            if (userName.isBlank() || lastName.isBlank() || age.isBlank()) {
                                toastViewModel.showToast("Por favor, completa todos los campos.", ToastType.ERROR)
                                isLoading = false

                            } else if (userAge == null || userAge <= 0) {
                                toastViewModel.showToast("Edad inválida.", ToastType.ERROR)
                                isLoading = false

                            } else {
                                val user = userRequest?.copy(
                                    user_name = userName,
                                    last_name = lastName,
                                    age = userAge
                                )
                                if (user != null) {
                                    UserApiService.create(user, context) { response ->
                                        isLoading = false
                                        if (response != null) {
                                            toastViewModel.showToast("Usuario creado exitosamente", ToastType.SUCCESS)
                                            navController.navigate("login")
                                        } else {
                                            toastViewModel.showToast("Error al registrar usuario", ToastType.ERROR)
                                        }
                                    }
                                } else {
                                    toastViewModel.showToast("Error interno al crear usuario", ToastType.ERROR)
                                }
                            }
                        },
                        enabled = !isLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CustomTheme.normalButton,
                            contentColor = CustomTheme.textPrimary
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(38.dp)
                    ) {
                        Text("Crear cuenta", color=CustomTheme.textPrimary)
                    }
                }
            }

        }

    }
}




