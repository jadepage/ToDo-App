package com.jadepage.todo.networking

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ToDoAPI {

//  The value is where it's pointing to (/ = Home).
    @GET("/")
    fun getToDoItems(): Call<ItemsList>

//  The value is where it's pointing to (/add = add).
    @POST("/add")
    fun addTodoItem(@Body item: Item): Call<ItemsList>
}