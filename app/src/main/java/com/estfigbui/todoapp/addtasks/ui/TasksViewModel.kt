package com.estfigbui.todoapp.addtasks.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.semantics.Role.Companion.Checkbox
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.estfigbui.todoapp.addtasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor() : ViewModel() {

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    private val _allTasks = mutableStateListOf<TaskModel>()
    val allTasks: List<TaskModel> = _allTasks

    fun onDialogClosed() {
        _showDialog.value = false
    }

    fun onTaskAdded(task: String) {
        _allTasks.add(TaskModel(task = task))
        _showDialog.value = false
    }

    fun onShowDialog() {
        _showDialog.value = true
    }

    fun onCheckBoxSelected(task: TaskModel) {
        val index = _allTasks.indexOf(task)
        _allTasks[index] = _allTasks[index].let {
            it.copy(selected = !it.selected)
        }
    }

    fun onTaskRemoved(taskModel: TaskModel) {
        val task = _allTasks.find { it.id == taskModel.id }
        _allTasks.remove(task)
    }
}