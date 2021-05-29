package com.jadepage.todo

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.jadepage.todo.databinding.DialogAddTodoBinding
import com.jadepage.todo.models.Priority
import com.jadepage.todo.models.Todo


class AddToDoDialog(
    private val onAdd: (Todo) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        val binding = DialogAddTodoBinding.inflate(inflater)
        val builder = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setPositiveButton(R.string.add) {_, _ ->
                onPositiveSelected(binding)
            }
                .setNegativeButton(R.string.cancel) {_, _ ->
                    // do nothing!
                }
        return builder.create()
    }

    private fun onPositiveSelected(binding: DialogAddTodoBinding) {
        val name = binding.newTodoName.editText?.text?.toString() ?: ""
        val prioritySelected = binding.groupPriority.checkedRadioButtonId
        when {
            name.isBlank() -> {
                Toast.makeText(
                        requireContext(),
                        "Please enter an item",
                        Toast.LENGTH_LONG
                ).show()
            }
            prioritySelected == -1 -> {
                Toast.makeText(
                        requireContext(),
                        "Please enter a priority",
                        Toast.LENGTH_LONG
                ).show()
            }
            else -> {
                val priority = Priority.from(prioritySelected)
                val toDo = Todo(name, priority)
                onAdd(toDo)
            }
        }
    }
}