package com.example.lostandfound

import android.graphics.Color

data class Item(
    var description: String? = "",
    var lostFoundDate: String? = "",
    var lostBy: String? = "",
    var itemName: String? = "",
    var tstamp:String?="",
    var color:String=""
)