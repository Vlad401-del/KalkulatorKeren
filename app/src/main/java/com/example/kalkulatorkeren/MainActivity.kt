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
import androidx.compose.ui.tooling.preview.Preview
import com.example.kalkulatorkeren.ui.theme.KalkulatorKerenTheme

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

@Composable
fun CalculatorApp() {
// code

    val buttonSpacing = 8.dp

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) { //padding

        //layar tampilan menggunakan teks
        Text(
            text = "0",
            fontSize = 48.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        //row 1
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(buttonSpacing))
        {  //fillmaxwidth mengisi lebar layar, arrangement spacedby memberi jarak tetap di antara komponen row
            Button(onClick = {/*later*/}, modifier = Modifier.padding(4.dp)) {Text("7")}
            Button(onClick = {/*later*/}, modifier = Modifier.padding(4.dp)) {Text("8")}
            Button(onClick = {/*later*/}, modifier = Modifier.padding(4.dp)) {Text("9")}
            Button(onClick = {/*later*/}, modifier = Modifier.padding(4.dp)) {Text("/")}
        }

        // tombol lainnya
    }
}

