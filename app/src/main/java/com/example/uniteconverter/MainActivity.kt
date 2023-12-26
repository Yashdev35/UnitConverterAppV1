package com.example.uniteconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uniteconverter.ui.theme.UniteConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniteConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
           UnitConverter()
                }
            }
        }
    }
}
//Composables are at the heart of UI development with Jetpack Compose.
// They are functions that define a part of the UI and can be reused and composed together to build a complete UI.
@Composable
fun UnitConverter(){
    //here we are setting up all the state values that we are going to use in our app like if drop down is open or not open and user input etc
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Centimeters ") }
    var outputUnit by remember { mutableStateOf("Meters ") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    val conversionFactor = remember { mutableStateOf(0.01) }
    val oConverstionFactor = remember { mutableStateOf(1.0) }
    val customTextstyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Color.Red
    )
    //the logic is we take input form user convert it into standard val then again convert it into desired output
    val context = LocalContext.current
    //here we are building the logic of the app
    fun converter(){
        //?: is called the elvis operator what it will do is if the input value is null then it will return 0.0
        // if it is not null then it will return the value inputValueDouble will store it
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result = (inputValueDouble * conversionFactor.value * 10000 / oConverstionFactor.value).roundToInt() / 10000.0 //using round off to reduce the size of number
        outputValue = result.toString()

    }
    //modifier is used to modify the ui elements
    //for spacing we can either use padding or spacer
    //if we want a perticular element to be spaced then we use padding, for consistent spacing we use spacer
    //padding is easy but not reusable , space bar is complex
    Column(
        Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
         //column is the parent layout element and modifying it we allow to align the elements
    ){
        //Row , Column , Box are layout elements or parent containers
             //Parent containers in app development are like big boxes that hold and organize smaller boxes (components) inside them.
             // These big boxes help to arrange, align, and manage the smaller parts of your app, like buttons, text, images, and other containers
        //In column all the ui elements will be stacked below each other
        Text(text = "Unit Converter", modifier = Modifier.padding(8.dp), style = customTextstyle)
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
            inputValue = it
            converter()
            //when input value changes the onValueChange returns it
            // this is an anonymous function, which means it has no name. and exicutes when the value is changed
            //Here goes what should happen after changing the value
            //outline text field is like the edit text where user gives in put , the rectangle
        },
            label = { Text(text = "Enter Value") },
            modifier = Modifier.padding(8.dp)
            //label is a composable that is used to give a label to the text field
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(text = "Result : $outputValue $outputUnit", modifier = Modifier.padding(8.dp) , style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.padding(6.dp))
        Row {
            //in row all the ui elements will be stacked next to each other
            Box {
// box is a layout element like row and columns , used ot make complex uis
                //input unit button
                Button(onClick = { iExpanded = true }) {
                    Text(text = "input is: $inputUnit")
                    Icon(Icons.Default.ArrowDropDown , contentDescription = " ")
                }
                DropdownMenu(expanded = iExpanded, onDismissRequest = {
                    //on dissmiss is called when we click outside the drop down menu
                    iExpanded = false
                }) {
                    DropdownMenuItem(
                        text = { Text(text = "Centimeters") },
                        onClick = {
                            inputUnit = "Centimeters"
                            iExpanded = false
                            conversionFactor.value = 0.01
                            converter()
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Meters") },
                        onClick = {
                            inputUnit = "meters"
                            iExpanded = false
                            conversionFactor.value = 1.0
                            converter()
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Kilometers") },
                        onClick = {
                            inputUnit = "Kilometers"
                            iExpanded = false
                            conversionFactor.value = 1000.0
                            converter()
                        })
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Box {
                //output unit button
               Button(onClick = { oExpanded = true }) {
                   Text(text = "output is: $outputUnit")
                   Icon(Icons.Default.ArrowDropDown , contentDescription = " ")
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = {
                    //on dissmiss is called when we click outside the drop down menu
                    oExpanded = false
                }) {
                    DropdownMenuItem(
                        text = { Text(text = "Centimeters") },
                        onClick = {
                            outputUnit = "Centimeters"
                            oExpanded = false
                            oConverstionFactor.value = 0.01
                            converter()
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Meters") },
                        onClick = {
                            outputUnit = "Meters"
                            oExpanded = false
                            oConverstionFactor.value = 1.0
                            converter()
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Kilometers") },
                        onClick = {
                            outputUnit = "Kilometers"
                            oExpanded = false
                            oConverstionFactor.value = 1000.0
                            converter()
                        })
                }
            }

           }
        }

        }
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
//Creating our own preview
@Preview(showBackground = true)
@Composable
fun UnitConverterPreview(){
    UnitConverter()
}
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    UniteConverterTheme {
//        UnitConverter()
//    }
//}