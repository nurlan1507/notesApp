package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_cases.NoteUseCases
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class NotesViewModel @Inject constructor(
    private val noteUseCases:NoteUseCases
):ViewModel(){
    private val _state = mutableStateOf<NoteState>(NoteState())
    val state: State<NoteState> =  _state
    private var recentlyDeletedNote : Note? = null

    private var getNotesJob: Job? = null

    init{
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event:NotesEvent){
        when(event){
            is NotesEvent.Order ->{
                if(state.value.order::class == event.noteOrder::class && state.value.order.orderType == event.noteOrder.orderType){
                    return
                }

            }
            is NotesEvent.Delete ->{
                viewModelScope.launch{
                    noteUseCases.deleteNoteUseCase(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.RestoreNote ->{
                viewModelScope.launch{
                    noteUseCases.addNote(recentlyDeletedNote?:return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvent.ToggleOrderSelection ->{
                _state.value = _state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(order:NoteOrder){
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotesUseCase(order)
            .onEach {notes ->
                _state.value = _state.value.copy(
                    notes, order
                )
            }
            .launchIn(viewModelScope)
    }
}