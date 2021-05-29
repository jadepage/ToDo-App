package com.jadepage.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jadepage.todo.databinding.ActivityMainBinding
import com.jadepage.todo.models.Todo
import com.jadepage.todo.networking.Network
import com.jadepage.todo.recyclerview.ToDoListAdapter

class MainActivity : AppCompatActivity() {

    private val todoListAdapter = ToDoListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.todoItems.apply {
            adapter = todoListAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        Network.getTodoItems { toDos ->
            todoListAdapter.submitList(toDos)
        }

        binding.addNewItem.setOnClickListener {
            val dialog = AddToDoDialog { onAdd(it) }
            dialog.show(supportFragmentManager, "AddToDoDialog")
        }
    }

    private fun onAdd(item: Todo) {
        Network.addItem(item) { toDos ->
            todoListAdapter.submitList(toDos)
        }
    }
}