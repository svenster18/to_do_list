package com.mohamadrizki.to_dolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.mohamadrizki.to_dolist.database.ToDo
import com.mohamadrizki.to_dolist.ui.theme.ToDoListTheme

class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val toDoList = remember { mutableStateListOf<ToDo>() }
            mainViewModel = obtainViewModel(this@MainActivity)
            mainViewModel.getAllToDos().observe(this) { toDos ->
                if (toDos != null) {
                    toDoList.addAll(toDos)
                }
            }
            ToDoListTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopAppBar({ Text("To Do List") }) },
                )
                { innerPadding ->
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            var toDo by remember { mutableStateOf("") }
                            TextField(
                                value = toDo,
                                label = { Text("What do you do today?") },
                                onValueChange = { value ->
                                    toDo = value
                                },
                                modifier = Modifier
                                    .padding(innerPadding)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Button(
                                onClick = {
                                    mainViewModel.insert(ToDo(toDo = toDo))
                                },
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                Text("Submit")
                            }
                        }
                        ToDoList(toDoList, onClick = { toDo -> mainViewModel.delete(toDo = toDo)})
                    }
                }
            }
        }
    }
}

private fun obtainViewModel(activity: ComponentActivity): MainViewModel {
    val factory = ViewModelFactory.getInstance(activity.application)
    return ViewModelProvider(activity, factory)[MainViewModel::class.java]
}

@Composable
fun ToDoCard(toDo: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround) {
        var checked by remember { mutableStateOf(false) }

        Checkbox(checked = checked, onCheckedChange = { checked = it })

        Spacer(modifier = Modifier.width(4.dp))
        val textColor by animateColorAsState(if (checked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground)

        Text(
            color = textColor,
            text = toDo,
            style = MaterialTheme.typography.bodyMedium.copy(
                textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None
            )
        )

        IconButton(onClick = onClick) { Icon(Icons.Filled.Delete, contentDescription = "Delete") }
    }
}

@Composable
fun ToDoList(toDoList: List<ToDo>, onClick: (ToDo) -> Unit) {
    LazyColumn {
        items(toDoList) { toDo ->
            ToDoCard(toDo.toDo ?:"", onClick = { onClick(toDo) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToDoListPreview() {
    ToDoListTheme {
        ToDoCard("Create To Do List App")
    }
}