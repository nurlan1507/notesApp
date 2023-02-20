package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_cases

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.InvalidNoteException
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository

class AddNote(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: Note){
        if(note.title.isBlank()){
            throw InvalidNoteException("The title of the note can not be empty")
        }
        if(note.content.isBlank()){
            throw InvalidNoteException("The content of the note can not be empty")
        }
        noteRepository.insertNote(note)
    }
}