package com.estfigbui.todoapp.addtasks.domain

import com.estfigbui.todoapp.addtasks.data.TaskRepository
import com.estfigbui.todoapp.addtasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    operator fun invoke(): Flow<List<TaskModel>> = taskRepository.tasks
}