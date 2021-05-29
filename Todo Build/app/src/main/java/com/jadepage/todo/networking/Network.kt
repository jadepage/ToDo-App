package com.jadepage.todo.networking

import android.util.Log
import com.jadepage.todo.models.Priority
import com.jadepage.todo.models.Todo
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object Network {

    private val items = mutableListOf<Todo>()

    private val client = OkHttpClient()

    private val toDoAPI: ToDoAPI
        get() {
            return Retrofit.Builder()
                .baseUrl("http://10.0.0.37:3003")
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(ToDoAPI::class.java)
        }

    private class ToDoCallback(private val onSuccess: (List<Todo>) -> Unit) : Callback<ItemsList>{
        override fun onResponse(call: Call<ItemsList>, response: Response<ItemsList>) {
            val toDos = response.body()?.items?.map { it.toToDo() } ?: emptyList()
            items.clear()
            items.addAll(toDos)
            onSuccess(items)
        }

        override fun onFailure(call: Call<ItemsList>, t: Throwable) {
            Log.v("Networking", "Error! $t")
        }
    }

    fun getTodoItems(onSuccess: (List<Todo>) -> Unit) {
        if(items.isEmpty()) {
            onSuccess(items)
        }
        toDoAPI.getToDoItems().enqueue(ToDoCallback(onSuccess))
    }

    fun addItem(todo: Todo, onSuccess: (List<Todo>) -> Unit) {
        toDoAPI.addTodoItem(todo.toItem()).enqueue(ToDoCallback(onSuccess))
    }

    private fun Item.toToDo(): Todo {
        return Todo(
            name = item,
            priority = Priority.of(priority)
        )
    }

    private fun Todo.toItem(): Item {
        return Item(
            item = name,
            priority = priority.priorityIndex
        )
    }
}
