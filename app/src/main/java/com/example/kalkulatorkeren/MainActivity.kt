package com.example.kalkulatorkeren

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.* // all API dari package composer.runtime
import org.mariuszgromada.math.mxparser.Expression // import parser
import androidx.compose.foundation.shape.CircleShape // import lingkaran
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.kalkulatorkeren.ui.theme.Gray
import com.example.kalkulatorkeren.ui.theme.KalkulatorKerenTheme
import com.example.kalkulatorkeren.ui.theme.LightGray
import com.example.kalkulatorkeren.ui.theme.Orange
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.foundation.focusable
import androidx.compose.ui.input.key.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KalkulatorKerenTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background)
                {
                    //kalkulator nanti
                    CalculatorApp()
                }
            }
        }
    }
}

@Composable //function untuk button
fun CalculatorButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit){ //dengan paramater menerima teks dan modifier (opsional)
    val (containerColor, contentColor) = when (text) {
        "+", "-", "x", "/" -> Orange to Color.White
        "C", "DEL", "%" -> LightGray to Color.Black
        "=" -> Orange to Color.White
        else -> Gray to Color.White
    }

    // logika font size
    val fontSize = when (text) {
        "DEL" -> 24.sp //supaya tidak keluar dari lingkaran
        else -> 32.sp
    }

    Button(
        onClick = onClick, //meneruskan paramater ke komponen Button
        shape = CircleShape, // bentuk lingkaran
        modifier = modifier.height(90.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ){
        Text(text = text, fontSize = fontSize)
    }
}


@Composable
fun CalculatorApp() {
// code

    //variabel ini untuk menyimpan teks di layar. Mutable state of artinya nilai awal 0
    // remember supaya tidak hilang
    var displayText by remember { mutableStateOf("0") } // memori

    // FocusRequester mengatur atau meminta fokus pada composable misal TextField
    // dibungkus dengan remember supaya objek tidak dibuat ulang saat recompose.
    val focusRequester = remember {FocusRequester()}

    fun onButtonClick(buttontext: String) {
        when (buttontext) {
            "C" -> {
                displayText = "0"
            }
            "DEL" -> {
                if (displayText.length > 1) {
                    displayText = displayText.dropLast(1) //drop last menghapus 1 karakter paling belakang dari String
                } else {
                    displayText = "0"
                }
            }
            "=" -> {
                try {
                    val expression = Expression(displayText.replace("x", "*")) //parser membaca x menjadi *
                    val result = expression.calculate()

                    if (!result.isNaN()) {
                        displayText = result.toString()
                    } else {
                        displayText = "Error" //persamaan yang tidak valid
                    }
                } catch (e: Exception) {
                    displayText = "Error"
                }
            }
            else -> {
                if (displayText == "0") {
                    displayText = buttontext
                } else {
                    displayText += buttontext
                }
            }
        }
    }

    val buttonSpacing = 8.dp

    Column(
        modifier = Modifier.
        fillMaxSize().padding(horizontal = 16.dp, vertical = 32.dp)
            .focusRequester(focusRequester) //menghubungkan focusRequester
            .focusable() // membuat column ini bisa difokus
            .onKeyEvent { event -> //memasang pendengar keyboard
                if (event.type == KeyEventType.KeyDown) {
                    when (event.key) {
                        Key.One, Key.NumPad1 -> onButtonClick("1")
                        Key.Two, Key.NumPad2 -> onButtonClick("2")
                        Key.Three, Key.NumPad3 -> onButtonClick("3")
                        Key.Four, Key.NumPad4 -> onButtonClick("4")
                        Key.Five, Key.NumPad5 -> onButtonClick("5")
                        Key.Six, Key.NumPad6 -> onButtonClick("6")
                        Key.Seven, Key.NumPad7 -> onButtonClick("7")
                        Key.Eight, Key.NumPad8 -> onButtonClick("8")
                        Key.Nine, Key.NumPad9 -> onButtonClick("9")
                        Key.Zero, Key.NumPad0 -> onButtonClick("0")
                        Key.Period, Key.NumPadDot -> onButtonClick(".")
                        Key.Plus, Key.NumPadAdd -> onButtonClick("+")
                        Key.Minus, Key.NumPadSubtract -> onButtonClick("-")
                        Key.Multiply, Key.NumPadMultiply -> onButtonClick("x")
                        Key.Slash, Key.NumPadDivide -> onButtonClick("/")
                        Key.Enter, Key.NumPadEnter -> onButtonClick("=")
                        Key.Backspace -> onButtonClick("DEL")
                        Key.C -> onButtonClick("C")
                    }
                    return@onKeyEvent true // sistem tau sudah menangani event ini
                }
                return@onKeyEvent false //sistem menangani event lain seperti keyUp
            }, verticalArrangement = Arrangement.Bottom //semua item ditempatkan di bawah column
        ) { //padding

        //layar tampilan menggunakan teks
        Text(
            text = displayText, //menampilkan nilai dari displayText
            fontSize = 72.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            maxLines = 1
        )

        //row 1
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(buttonSpacing))
        {  //fillmaxwidth mengisi lebar layar, arrangement spacedby memberi jarak tetap di antara komponen row
            CalculatorButton(text = "C", modifier = Modifier.weight(2f), onClick = {onButtonClick("C")})
            CalculatorButton(text = "DEL", modifier = Modifier.weight(1f), onClick = {onButtonClick("DEL")})
//          CalculatorButton(text = "%", modifier = Modifier.weight(1f), onClick = {onButtonClick("%")}) //belum dipakai
            CalculatorButton(text = "/", modifier = Modifier.weight(1f), onClick = {onButtonClick("/")})
        }

        //row 2
        Row(modifier = Modifier.fillMaxWidth().padding(top = buttonSpacing), horizontalArrangement = Arrangement.spacedBy(buttonSpacing))
        {  //fillmaxwidth mengisi lebar layar, arrangement spacedby memberi jarak tetap di antara komponen row
            CalculatorButton(text = "7", modifier = Modifier.weight(1f), onClick = {onButtonClick("7")})
            CalculatorButton(text = "8", modifier = Modifier.weight(1f), onClick = {onButtonClick("8")})
            CalculatorButton(text = "9", modifier = Modifier.weight(1f), onClick = {onButtonClick("9")})
            CalculatorButton(text = "x", modifier = Modifier.weight(1f), onClick = {onButtonClick("x")})
        }

        //row 3
        Row(modifier = Modifier.fillMaxWidth().padding(top = buttonSpacing), horizontalArrangement = Arrangement.spacedBy(buttonSpacing))
        {  //fillmaxwidth mengisi lebar layar, arrangement spacedby memberi jarak tetap di antara komponen row
            CalculatorButton(text = "4", modifier = Modifier.weight(1f), onClick = {onButtonClick("4")})
            CalculatorButton(text = "5", modifier = Modifier.weight(1f), onClick = {onButtonClick("5")})
            CalculatorButton(text = "6", modifier = Modifier.weight(1f), onClick = {onButtonClick("6")})
            CalculatorButton(text = "-", modifier = Modifier.weight(1f), onClick = {onButtonClick("-")})
        }

        //row 4
        Row(modifier = Modifier.fillMaxWidth().padding(top = buttonSpacing), horizontalArrangement = Arrangement.spacedBy(buttonSpacing))
        {  //fillmaxwidth mengisi lebar layar, arrangement spacedby memberi jarak tetap di antara komponen row
            CalculatorButton(text = "1", modifier = Modifier.weight(1f), onClick = {onButtonClick("1")})
            CalculatorButton(text = "2", modifier = Modifier.weight(1f), onClick = {onButtonClick("2")})
            CalculatorButton(text = "3", modifier = Modifier.weight(1f), onClick = {onButtonClick("3")})
            CalculatorButton(text = "+", modifier = Modifier.weight(1f), onClick = {onButtonClick("+")})
        }

        //row bawahh
        Row(modifier = Modifier.fillMaxWidth().padding(top = buttonSpacing), horizontalArrangement = Arrangement.spacedBy(buttonSpacing))
        {  //fillmaxwidth mengisi lebar layar, arrangement spacedby memberi jarak tetap di antara komponen row
            CalculatorButton(text = "0", modifier = Modifier.weight(2f), onClick = {onButtonClick("0")})
            CalculatorButton(text = ".", modifier = Modifier.weight(1f), onClick = {onButtonClick(".")})
            CalculatorButton(text = "=", modifier = Modifier.weight(1f), onClick = {onButtonClick("=")})

        }
    }
    // saat composable pertama kali ditampilkan, otomatis beri fokus ke komponen terkait
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}