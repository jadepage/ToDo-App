package com.jadepage.todo.networking

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/*
JSON File Would Look Something Like This

 { -> Everytime you see one of these, this indicates
      that it is a JSON object

     "items": [ -> The square bracket indicates an array of objects
     {
       "item": "Take out trash",
       "priority": 0
     },
     {
       "item": "Buy eggs",
       "priority": 1
     },
     {
       "item": "Clean the house",
       "priority": 3
     },
   ]
 }
*/

@JsonClass(generateAdapter = true)
data class ItemsList(
    @Json(name = "items") val items: List<Item>
)

@JsonClass(generateAdapter = true)
data class Item(
    @Json(name = "item") val item: String,
    @Json(name = "priority") val priority: Int
)
