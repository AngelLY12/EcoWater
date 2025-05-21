package com.example.proyecto.ui.components.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto.ui.theme.CustomTheme
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.material3.IconButton
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.Icon
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height


@Composable
fun EditFieldModal(
    showModal: Boolean,
    title: String,
    initialValue: String,
    label:String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
) {
    var value by remember { mutableStateOf(initialValue) }

    val text=CustomTheme.textOnPrimary
    if (showModal) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = CustomTheme.textOnPrimary
                )
            },
            text = {
                OutlinedTextField(
                    placeholder ={Text(label)},
                    value = value,
                    onValueChange = { value = it },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = text,
                        unfocusedBorderColor = text,
                        focusedLabelColor = text,
                        cursorColor = text,
                        focusedTextColor = text,
                        unfocusedTextColor = text
                    )
                    )

            },

            confirmButton = {
                Button(
                    onClick = {
                        onConfirm(value)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =CustomTheme.modalButton,
                        contentColor = CustomTheme.textPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancelar", color = CustomTheme.outLinedButton)
                }
            },
            containerColor = CustomTheme.modalBackground,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
fun <T> EditDropdownModal(
    showModal: Boolean,
    title: String,
    options: List<T>,
    selectedOption: T,
    optionToText: (T) -> String,
    onDismiss: () -> Unit,
    onConfirm: (T) -> Unit
) {
    var selected by remember { mutableStateOf(selectedOption) }

    if (showModal) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = CustomTheme.textOnPrimary
                )
            },
            text = {
                CustomDropdown(
                    options = options,
                    selectedOption = selected,
                    onOptionSelected = { selected = it },
                    label = "Selecciona un tipo",
                    optionToText = optionToText,
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm(selected)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomTheme.modalButton,
                        contentColor = CustomTheme.textPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancelar", color = CustomTheme.outLinedButton)
                }
            },
            containerColor = CustomTheme.modalBackground,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
fun PasswordChangeModal(
    showModal: Boolean,
    title: String,
    onDismiss: () -> Unit,
    onConfirm: (currentPassword: String, newPassword: String) -> Unit
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var passwordCurrentVisible by remember { mutableStateOf(false) }
    var passwordNewVisible by remember { mutableStateOf(false) }
    var passwordConfirmNewVisible by remember { mutableStateOf(false) }

    if (showModal) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = CustomTheme.textOnPrimary
                )
            },
            text = {
                Column {
                    OutlinedTextField(
                        placeholder = { Text("Contraseña actual") },
                        value = currentPassword,
                        onValueChange = { currentPassword = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CustomTheme.textOnPrimary,
                            unfocusedBorderColor = CustomTheme.textOnPrimary,
                            focusedLabelColor = CustomTheme.textOnPrimary,
                            cursorColor = CustomTheme.textOnPrimary
                        ),
                        visualTransformation = if (passwordCurrentVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordCurrentVisible = !passwordCurrentVisible },
                                enabled = !isLoading,
                            ) {
                                Icon(
                                    imageVector = if (passwordCurrentVisible) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = if (passwordCurrentVisible) "Ocultar contraseña" else "Mostrar contraseña"
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        placeholder = { Text("Nueva contraseña") },
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CustomTheme.textOnPrimary,
                            unfocusedBorderColor = CustomTheme.textOnPrimary,
                            focusedLabelColor = CustomTheme.textOnPrimary,
                            cursorColor = CustomTheme.textOnPrimary
                        ),
                        visualTransformation = if (passwordNewVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordNewVisible = !passwordNewVisible },
                                enabled = !isLoading,
                            ) {
                                Icon(
                                    imageVector = if (passwordNewVisible) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = if (passwordNewVisible) "Ocultar contraseña" else "Mostrar contraseña"
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        placeholder = { Text("Confirmar nueva contraseña") },
                        value = confirmNewPassword,
                        onValueChange = { confirmNewPassword = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CustomTheme.textOnPrimary,
                            unfocusedBorderColor = CustomTheme.textOnPrimary,
                            focusedLabelColor = CustomTheme.textOnPrimary,
                            cursorColor = CustomTheme.textOnPrimary
                        ),
                        visualTransformation = if (passwordConfirmNewVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordConfirmNewVisible = !passwordConfirmNewVisible },
                                enabled = !isLoading,
                            ) {
                                Icon(
                                    imageVector = if (passwordConfirmNewVisible) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = if (passwordConfirmNewVisible) "Ocultar contraseña" else "Mostrar contraseña"
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newPassword == confirmNewPassword) {
                            onConfirm(currentPassword, newPassword)
                            onDismiss()
                        } else {
                            errorMessage = "Las contraseñas no coinciden."
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomTheme.modalButton,
                        contentColor = CustomTheme.textPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancelar", color = CustomTheme.textPrimary)
                }
            },
            containerColor = CustomTheme.modalBackground,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

