package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.InvalidNoteException
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_cases.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases:NoteUseCases,
    savedStateHandle:SavedStateHandle
):ViewModel() {
    private var _noteTitle = mutableStateOf(NoteTextFieldState(hint = "Enter title"))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private var _noteContent = mutableStateOf(NoteTextFieldState(hint = "Enter some content"))
    val noteContent : State<NoteTextFieldState> = _noteContent

    private var _pdfState = mutableStateOf(InsertPdfFileState.Inactive)
    val pdfState:State<InsertPdfFileState> = _pdfState

    private var _noteColor  = mutableStateOf<Int>(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private var _eventFlow  = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId:Int? = null
    init{
        savedStateHandle.get<Int>("noteId")?.let {
            if(it != -1){
                viewModelScope.launch {
                    noteUseCases.getNoteNoteUseCase(it)?.also {note->
                        currentNoteId = note.id
                        _noteTitle.value = _noteTitle.value.copy(text = note.title, isHintVisible = false)
                        _noteContent.value = _noteContent.value.copy(text = note.content, isHintVisible = false)
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }
    fun onEvent(event:AddEditNoteEvent){
        when(event){
            is AddEditNoteEvent.EnteredTitle ->{
                _noteTitle.value = _noteTitle.value.copy(text=event.value)
            }
            is AddEditNoteEvent.ChangeTitleFocus ->{
                _noteTitle.value = _noteTitle.value.copy(isHintVisible = !event.focusState.isFocused && _noteTitle.value.text.isBlank())
            }
            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = _noteContent.value.copy(text=event.value)
            }
            is AddEditNoteEvent.ChangeContentFocus ->{
                _noteContent.value = _noteContent.value.copy(isHintVisible = !event.focusState.isFocused && _noteContent.value.text.isBlank())
            }
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddEditNoteEvent.SaveNote ->{
                viewModelScope.launch {
                    try{
                        noteUseCases.addNote(
                            Note(
                                title = _noteTitle.value.text,
                                content = _noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = _noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    }catch (e:InvalidNoteException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message.toString()
                            )
                        )
                    }
                }
            }
            is AddEditNoteEvent.InsertPDF ->{
                viewModelScope.launch {

                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowSnackbar(val message:String):UiEvent()
        object SaveNote:UiEvent()
    }


}