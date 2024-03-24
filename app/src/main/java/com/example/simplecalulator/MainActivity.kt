package com.example.simplecalulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplecalulator.ui.theme.SimpleCalulatorTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleCalulatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorLayout()
                }
            }
        }
    }
}

// created composables

// layout
@Composable
fun CalculatorLayout() {
    // use of remember to update the mutable state of input to fields
    var amountInput1 by remember { mutableStateOf("") }
    var amountInput2 by remember { mutableStateOf("") }

    // updates the string variables of user input to doubles or null if not a number
    val input1 = amountInput1.toDoubleOrNull() ?: 0.0
    val input2 = amountInput2.toDoubleOrNull() ?: 0.0
    // use of remember to update the result
    var resultData  by remember { mutableDoubleStateOf(0.0) }

    // updates the displayed result to the UI display
    val result by remember(resultData) {
        derivedStateOf { resultData.toString() }
    }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.simple_calculator),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        EditNumberField(
            label = R.string.input1,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = amountInput1,
            onValueChange = {amountInput1 = it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        EditNumberField(
            label = R.string.input2,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            value = amountInput2,
            onValueChange = { amountInput2 = it },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth(),
        )
        Row(
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { resultData = add(input1, input2) }) {
                    Image(painter = painterResource(R.drawable.addition), contentDescription = null)
            }
            Button(
                onClick = { resultData = subtract(input1, input2) }) {
                    Image(painter = painterResource(R.drawable.subtraction), contentDescription = null)
            }
            Button(
                onClick = { resultData = multiply(input1, input2) }) {
                    Image(painter = painterResource(R.drawable.multiply), contentDescription = null)
            }
            Button(
                onClick = { resultData = divide(input1, input2) }) {
                    Text(text = " / ", color = Color.Black, fontSize = 20.sp)
            }
        }
        Text(
            text = stringResource(R.string.result, result),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))

    }

} // end of layout

// composable for the user inputs
@Composable
fun EditNumberField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions
    )
} // end of EditNumberField

// functions for buttons
fun add(input1: Double, input2: Double): Double {
    return (input1 + input2)
}

fun subtract(input1: Double, input2: Double): Double {
    return (input1 - input2)
}

fun divide(input1: Double, input2: Double): Double {
    return if (input2 != 0.0) {
        input1 / input2
    } else {
        Double.NaN // handle division by 0
    }
}

fun multiply(input1: Double, input2: Double): Double {
    return (input1 * input2)
}

// created previews
@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun CalculatorLayoutPreview() {
    SimpleCalulatorTheme {
        CalculatorLayout()
    }
}