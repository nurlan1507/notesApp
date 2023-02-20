package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_cases

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNote:AddNote,
    val getNoteNoteUseCase:GetNoteUseCase
)