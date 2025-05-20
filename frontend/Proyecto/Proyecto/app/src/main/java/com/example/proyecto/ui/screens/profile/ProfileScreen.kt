package com.example.proyecto.ui.screens.profile


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proyecto.R
import com.example.proyecto.data.services.AuthApiService
import com.example.proyecto.model.auth.AuthRequest
import com.example.proyecto.ui.components.custom.CustomTextButton
import com.example.proyecto.ui.components.custom.EditFieldModal
import com.example.proyecto.ui.components.custom.PasswordChangeModal
import com.example.proyecto.ui.components.custom.ToastType
import com.example.proyecto.ui.components.layout.BottomNavigationBar
import com.example.proyecto.ui.components.layout.ColumnLayout
import com.example.proyecto.ui.theme.CustomTheme
import com.example.proyecto.ui.viewModels.AuthViewModel
import com.example.proyecto.ui.viewModels.ToastViewModel
import com.example.proyecto.ui.viewModels.UserViewModel


@Preview
@Composable
fun PreviewProfileScreen() {
    val navController = rememberNavController() // Corrige esta línea
    val authViewModel = AuthViewModel()
    val toastViewModel = ToastViewModel()
    ProfileScreenPreview(navController, authViewModel, toastViewModel)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    viewModel: UserViewModel = viewModel(),
    toastViewModel: ToastViewModel
) {
    val context = LocalContext.current
    val perfil = viewModel.userProfile.value
    val isLoading = viewModel.isLoading.value
    var isLoading2 by remember { mutableStateOf(false) }
    val lightGray = CustomTheme.cardBorder
    val interactionSource =
        remember { MutableInteractionSource() } // Instancia del InteractionSource


    var showEditName by remember { mutableStateOf(false) }
    var showEditLastName by remember { mutableStateOf(false) }
    var showEditeAge by remember { mutableStateOf(false) }
    var showEditPassword by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadUserProfile(context)
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        ColumnLayout {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center // Espacio entre elementos
            )
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.perfil),
                        contentDescription = "Imagen de perfil",
                        modifier = Modifier.height(100.dp)
                    )
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                perfil?.user_name.toString() + " " + perfil?.last_name.toString(),
                                color = CustomTheme.textOnPrimary,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                perfil?.email.toString(),
                                color = CustomTheme.textOnSecondary,
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showEditName = true },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                "Nombre",
                                style = MaterialTheme.typography.bodyLarge,
                                color = CustomTheme.textOnPrimary
                            )
                            Text(
                                perfil?.user_name.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomTheme.textOnSecondary
                            )
                        }
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Editar",
                            tint = CustomTheme.textOnPrimary
                        )
                    }
                    if (showEditName) {
                        EditFieldModal(
                            showModal = showEditName,
                            title = "Editar nombre",
                            initialValue = perfil?.user_name.toString(),
                            onDismiss = { showEditName = false },
                            onConfirm = { newName ->
                                val userEdit = perfil!!.copy(
                                    user_name = newName,
                                    // No incluir contraseña si no se quiere cambiar
                                    password = null
                                )
                                showEditName = false
                                viewModel.updateUserProfile(context, userEdit){
                                        success->
                                    if(success!=null){
                                        toastViewModel.showToast("Nombre actualizado con exito", ToastType.SUCCESS)
                                    }else{
                                        toastViewModel.showToast("Error al actualizar nombre", ToastType.ERROR)

                                    }
                                }
                            },
                            label = "Nombre del usuario"
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        color = CustomTheme.textOnPrimary
                    )


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showEditLastName = true },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                "Apellidos",
                                style = MaterialTheme.typography.bodyLarge,
                                color = CustomTheme.textOnPrimary
                            )
                            Text(
                                perfil?.last_name.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomTheme.textOnSecondary
                            )
                        }
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Editar",
                            tint = CustomTheme.textOnPrimary
                        )
                    }
                    if (showEditLastName) {
                        EditFieldModal(
                            showModal = showEditLastName,
                            title = "Editar apellidos",
                            initialValue = perfil?.last_name.toString(),
                            onDismiss = { showEditLastName = false },
                            onConfirm = { newLastName ->
                                val userEdit = perfil!!.copy(
                                    last_name = newLastName,
                                    // No incluir contraseña si no se quiere cambiar
                                    password = null
                                )
                                showEditLastName = false
                                viewModel.updateUserProfile(context, userEdit){
                                        success->
                                    if(success!=null){
                                        toastViewModel.showToast("Apellido actualizado con exito", ToastType.SUCCESS)
                                    }else{
                                        toastViewModel.showToast("Error al actualizar apellido", ToastType.ERROR)

                                    }
                                }
                            },
                            label = "Nombre del usuario"
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        color = CustomTheme.textOnPrimary
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showEditeAge = true },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                "Edad",
                                style = MaterialTheme.typography.bodyLarge,
                                color = CustomTheme.textOnPrimary
                            )
                            Text(
                                perfil?.age.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomTheme.textOnSecondary
                            )
                        }
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Editar",
                            tint = CustomTheme.textOnPrimary
                        )
                    }
                    if (showEditeAge) {
                        EditFieldModal(
                            showModal = showEditeAge,
                            title = "Editar edad",
                            initialValue = perfil?.age.toString(),
                            onDismiss = { showEditeAge = false },
                            onConfirm = { newAge ->
                                val userEdit = perfil!!.copy(
                                    age = newAge.toInt(),
                                    // No incluir contraseña si no se quiere cambiar
                                    password = null
                                )
                                showEditeAge = false
                                viewModel.updateUserProfile(context, userEdit){
                                        success->
                                    if(success!=null){
                                        toastViewModel.showToast("Edad actualizado con exito", ToastType.SUCCESS)
                                    }else{
                                        toastViewModel.showToast("Error al actualizar edad", ToastType.ERROR)

                                    }
                                }
                            },
                            label = "Nombre del usuario"
                        )
                    }



                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        color = CustomTheme.textOnPrimary
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showEditPassword = true },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                "Contraseña",
                                style = MaterialTheme.typography.bodyLarge,
                                color = CustomTheme.textOnPrimary
                            )
                            Text(
                                "*********",
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomTheme.textOnSecondary
                            )
                        }
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Editar",
                            tint = CustomTheme.textOnPrimary
                        )
                    }
                    if (showEditPassword) {
                        // Llamar al modal
                        PasswordChangeModal(
                            showModal = showEditPassword,
                            title = "Cambiar Contraseña",
                            onDismiss = { showEditPassword = false },
                            onConfirm = { currentPassword, newPassword ->

                                isLoading2 = true

                                // Validar la contraseña actual
                                val authRequest =
                                    AuthRequest(email = perfil?.email ?: "", password = currentPassword)
                                AuthApiService.authUser(authRequest, context) { authResponse ->
                                    isLoading2 = false
                                    if (authResponse != null) {
                                        // Si la contraseña actual es válida, actualizar la contraseña
                                        val userEdit =
                                            perfil!!.copy(password = newPassword) // Actualizar el perfil
                                        viewModel.updateUserProfile(
                                            context,
                                            userEdit
                                        ) // Llamada al ViewModel
                                        toastViewModel.showToast("Contraseña actualizada con exito", ToastType.SUCCESS)

                                    } else {
                                        // Mostrar error si la contraseña actual no coincide
                                        toastViewModel.showToast("Contraseña actual incorrecta", ToastType.WARNING)

                                    }
                                }
                            }
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        color = CustomTheme.textOnPrimary
                    )

                    CustomTextButton(
                        text = "Cerrar sesión",
                        onClick = {
                            authViewModel.logoutSilently(context)
                            navController.navigate("login") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                                toastViewModel.showToast("Se ha cerrado tu sesión", ToastType.INFO)
                            }
                        },
                        textColor = CustomTheme.deleteText,
                    )
                }


                }
            }






    }
}




@Composable
fun ProfileScreenPreview(navController: NavHostController, authViewModel: AuthViewModel, toastViewModel: ToastViewModel) {
    Surface(color = CustomTheme.background, modifier = Modifier.fillMaxSize()) {
        ProfileScreen(navController = navController, authViewModel = authViewModel, toastViewModel = toastViewModel)
    }
}
