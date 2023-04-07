package com.estfigbui.todoapp.addtasks.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estfigbui.todoapp.addtasks.domain.DeleteTaskUseCase
import com.estfigbui.todoapp.addtasks.domain.GetTasksUseCase
import com.estfigbui.todoapp.addtasks.domain.InsertTaskUseCase
import com.estfigbui.todoapp.addtasks.domain.UpdateTaskUseCase
import com.estfigbui.todoapp.addtasks.ui.TasksUiState.Success
import com.estfigbui.todoapp.addtasks.ui.TasksUiState.Error
import com.estfigbui.todoapp.addtasks.ui.TasksUiState.Loading
import com.estfigbui.todoapp.addtasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val insertTaskUseCase: InsertTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getTasksUseCase: GetTasksUseCase,
) : ViewModel() {

    val uiState: StateFlow<TasksUiState> = getTasksUseCase().map(::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    fun onDialogClosed() {
        _showDialog.value = false
    }

    fun onTaskAdded(task: String) {
        _showDialog.value = false

        viewModelScope.launch {
            insertTaskUseCase(TaskModel(task = task))
        }
    }

    fun onShowDialog() {
        _showDialog.value = true
    }

    fun onCheckBoxSelected(task: TaskModel) {
        viewModelScope.launch {
            updateTaskUseCase(task.copy(selected = !task.selected))
        }
    }

    fun onTaskRemoved(taskModel: TaskModel) {
        viewModelScope.launch {
            deleteTaskUseCase(taskModel)
        }
    }
}