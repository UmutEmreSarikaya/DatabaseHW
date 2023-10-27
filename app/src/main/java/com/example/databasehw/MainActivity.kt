package com.example.databasehw

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.databasehw.ui.theme.DatabaseHWTheme

class MainActivity : ComponentActivity() {
    private lateinit var categoryRepository: CategoryRepository
    private lateinit var sparePartRepository: SparePartRepository
    private lateinit var categoricalParts: MutableList<SparePart>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoryRepository = CategoryRepository(this)
        sparePartRepository = SparePartRepository(this)

        categoryRepository.createCategories()
        sparePartRepository.createSpareParts()

        setContent {
            DatabaseHWTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    categoricalParts = sparePartRepository.partsByCategoryID(1)
                    MainScreen()
                }
            }
        }
    }

    @Composable
    fun MainScreen() {
        val categories = categoryRepository.getCategories()

        val expanded = remember { mutableStateOf(false) }
        val chosenCategory = remember { mutableStateOf(categories[0]) }

        Column(
            Modifier.fillMaxSize(), Arrangement.Top, Alignment.Start
        ) {
            val context = LocalContext.current
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .height(35.dp)
            ) {
                Row(
                    Modifier
                        .clickable {
                            expanded.value = !expanded.value
                        }
                        .align(Alignment.TopStart)) {
                    Text(text = chosenCategory.value.definition, color = Color.White)
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                DropdownMenu(expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }) {
                    categories.forEach {

                        DropdownMenuItem(text = {
                            Text(text = it.definition)
                        }, onClick = {
                            chosenCategory.value = it

                            expanded.value = false

                            categoricalParts =
                                sparePartRepository.partsByCategoryID(chosenCategory.value.categoryID)
                        })
                    }
                }
            }
            ShowSparePartList(lst = categoricalParts)
            FloatingActionButton(modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = CircleShape,
                onClick = {
                    val intent = Intent(context, AddSparePartActivity::class.java)
                    startActivity(intent)
                }) {
                Icon(Icons.Filled.Add, "Large floating action button")
            }
        }
    }

    @Composable
    fun ShowSparePart(sparePart: SparePart) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Text(text = sparePart.name, style = MaterialTheme.typography.headlineMedium)
            Spacer(
                Modifier
                    .padding(1.dp)
                    .fillMaxWidth(0.7F)
                    .background(Color.DarkGray)
                    .height(0.5.dp)
            )
            Text(
                text = "Stock Count: ${sparePart.stockCount}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(
                Modifier
                    .padding(1.dp)
                    .fillMaxWidth(0.7F)
                    .background(Color.DarkGray)
                    .height(0.5.dp)
            )
            Text(text = "Price: ${sparePart.price} TL", style = MaterialTheme.typography.bodyMedium)
            Spacer(
                Modifier
                    .padding(1.dp)
                    .fillMaxWidth(0.7F)
                    .background(Color.Red)
                    .height(1.5.dp)
            )
        }
    }

    @Composable
    fun ShowSparePartList(lst: List<SparePart>) {
        LazyColumn(
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.Bottom,
            userScrollEnabled = true
        ) {
            this.items(lst) {
                ShowSparePart(it)
            }
        }
    }
}