package com.estfigbui.todoapp.addtasks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.estfigbui.todoapp.addtasks.ui.model.TaskModel

@Composable
fun TasksScreen(tasksViewModel: TasksViewModel) {
    val showDialog: Boolean by tasksViewModel.showDialog.observeAsState(false)

    Box(modifier = Modifier.fillMaxSize()) {
        MyAddTaskDialog(
            show = showDialog,
            onDismiss = { tasksViewModel.onDialogClosed() },
            onTaskAdded = { tasksViewModel.onTaskAdded(it) })
        MyFabAdd(Modifier.align(Alignment.BottomEnd), tasksViewModel)
        MyTasksList(tasksViewModel)
    }
}

@Composable
fun MyTasksList(tasksViewModel: TasksViewModel) {
    val tasksList: List<TaskModel> = tasksViewModel.allTasks

    LazyColumn {
        items(tasksList, key = { it.id })
        { task ->
            MyTaskItem(taskModel = task, tasksViewModel = tasksViewModel)
        }
    }
}

@Composable
fun MyTaskItem(taskModel: TaskModel, tasksViewModel: TasksViewModel) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    tasksViewModel.onTaskRemoved(taskModel)
                })
            },
        elevation = 8.dp
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = taskModel.task,
                Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )
            Checkbox(
                checked = taskModel.selected,
                onCheckedChange = { tasksViewModel.onCheckBoxSelected(taskModel) })
        }
    }
}

@Composable
fun MyFabAdd(modifier: Modifier, tasksViewModel: TasksViewModel) {
    FloatingActionButton(onClick = {
        tasksViewModel.onShowDialog()
    }, modifier = modifier.padding(16.dp)) {
        Icon(Icons.Filled.Add, contentDescription = "")
    }
}

@Composable
fun MyAddTaskDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdded: (String) -> Unit) {
    var myTask by rememberSaveable { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Add task",
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.SemiBold
                )
                MySpacer(size = 16)
                TextField(
                    value = myTask,
                    onValueChange = { myTask = it },
                    singleLine = true,
                    maxLines = 1
                )
                MySpacer(size = 16)
                Button(onClick = {
                    onTaskAdded(myTask)
                    myTask = ""
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Add")
                }
            }
        }
    }
}

@Composable
fun MySpacer(size: Int) {
    Spacer(modifier = Modifier.size(size.dp))
}