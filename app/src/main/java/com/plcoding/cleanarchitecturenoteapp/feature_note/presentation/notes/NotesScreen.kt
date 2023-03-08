package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components.NoteItem
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components.OrderSection
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NotesScreen(
    navController: NavController,
    salamViewModel: SalamViewModel,
    viewModel: NotesViewModel = hiltViewModel(),
){
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val salamState = salamViewModel.salam
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                          navController.navigate(Screen.AddEditNoteScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary,

            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add a note")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Your notes",
                    style = MaterialTheme.typography.h4
                )
                IconButton(
                    onClick = {viewModel.onEvent(NotesEvent.ToggleOrderSelection)},
                ) {
                    Icon(Icons.Default.Sort, contentDescription = "Sort")
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteOrder = state.order,
                    onOrderChange = {viewModel.onEvent(NotesEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(modifier = Modifier.height(20.dp), text="ss ${salamState.value}")
            TextField(value = "", onValueChange ={it->salamViewModel.change(it)} )
            LazyColumn(modifier = Modifier.fillMaxSize()){
              items(state.notes){note->
                  NoteItem(
                      note = note,
                      modifier = Modifier
                          .fillMaxWidth()
                          .clickable {
                              navController.navigate(Screen.AddEditNoteScreen.route + "?noteId=${note.id}&noteColor=${note.color}")
                          },
                      onDelete = {
                          viewModel.onEvent(NotesEvent.Delete(note=note))
                          scope.launch {
                              var result = scaffoldState.snackbarHostState.showSnackbar(message = "Note deleted", actionLabel = "Undo")
                              if(result == SnackbarResult.ActionPerformed){
                                  viewModel.onEvent(NotesEvent.RestoreNote)
                              }
                          }
                      }
                  )
                  Spacer(modifier = Modifier.height(16.dp))
              }
            }

        }
    }






}