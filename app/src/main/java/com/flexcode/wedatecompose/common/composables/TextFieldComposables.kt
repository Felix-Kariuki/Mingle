package com.flexcode.wedatecompose.common.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.flexcode.wedatecompose.R.drawable as AppIcon
import com.flexcode.wedatecompose.R.string as AppText

@Composable
fun BasicField(
    @StringRes text: Int,
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        singleLine = true,
        modifier = modifier.width(66.dp),
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(text = stringResource(text)) },
        shape = RoundedCornerShape(10.dp)
    )

}

@Composable
fun EmailField(
    value: String,
    icon: ImageVector,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(stringResource(AppText.email)) },
        leadingIcon = {
            Icon(
                imageVector = icon, contentDescription = stringResource(
                    AppText.email
                )
            )
        },
        shape = RoundedCornerShape(10.dp)
    )
}

@Composable
fun PasswordField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier){
    PasswordField(value = value, placeholder = AppText.password, onNewValue =onNewValue,modifier)
}

@Composable
fun ConfirmPasswordField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    PasswordField(value = value, placeholder = AppText.confirmPassword, onNewValue =onNewValue,modifier)
}

@Composable
private fun PasswordField(
    value: String,
    @StringRes placeholder: Int,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
){
    var isVisible by remember { mutableStateOf(false) }

    val icon =
        if (isVisible) painterResource(AppIcon.ic_visibility_on)
        else painterResource(AppIcon.ic_visibility_off)

    val visualTransformation =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(text = stringResource(placeholder)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = stringResource(
            AppText.password_invisible
        )) },
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(painter = icon, contentDescription = stringResource(AppText.password_visible))
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(10.dp)
    )
}