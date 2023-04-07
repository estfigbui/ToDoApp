package com.estfigbui.todoapp.addtasks.domain

import com.estfigbui.todoapp.addtasks.data.TaskRepository
import com.estfigbui.todoapp.addtasks.ui.model.TaskModel
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(taskModel: TaskModel) = taskRepository.delete(taskModel)
}