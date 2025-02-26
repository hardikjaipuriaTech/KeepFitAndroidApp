package com.material.tortoise.view.screens

import java.text.SimpleDateFormat
import java.util.*

fun dateFormat(dateToParse: Date?): String {
    val formatter = SimpleDateFormat("dd-MMM-yyyy")
    val formatted = formatter.format(dateToParse)
    return formatted.toString()

}