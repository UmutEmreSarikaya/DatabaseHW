package com.example.databasehw

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.databasehw.ui.theme.DatabaseHWTheme

class AddSparePartActivity : ComponentActivity() {
    private lateinit var sparePartRepository: SparePartRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sparePartRepository = SparePartRepository(this)

        setContent {
            DatabaseHWTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(sparePartRepository)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(sparePartRepository: SparePartRepository) {
        Column(
            Modifier
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current
            val categoryText = remember { mutableStateOf("") }
            val nameText = remember { mutableStateOf("") }
            val stockText = remember { mutableStateOf("") }
            val priceText = remember { mutableStateOf("") }

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(5.dp),
                text = "Add A New Spare Part",
                fontSize = 24.sp
            )

            TextField(value = categoryText.value,
                onValueChange = { categoryText.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                placeholder = { Text("Category ID") })

            TextField(value = nameText.value,
                onValueChange = { nameText.value = it },
                placeholder = { Text("Name") })

            TextField(value = stockText.value,
                onValueChange = { stockText.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                placeholder = { Text("Stock Count") })

            TextField(value = priceText.value,
                onValueChange = { priceText.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                placeholder = { Text("Price") })

            Button(onClick = {
                val sparePart = SparePart(
                    -1,
                    categoryText.value.toInt(),
                    nameText.value,
                    stockText.value.toInt(),
                    priceText.value.toLong()
                )
                sparePartRepository.addSparePart(sparePart)
                val activity = context as Activity
                activity.finish()
            }) {
                Text(text = "Add Spare Part")
            }
        }
    }
}