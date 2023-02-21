package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_cases.question_use_cases

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.QuestionRepository

class InsertPDF(
    private val questionRepository: QuestionRepository,
    private val noteRepository: NoteRepository
    ) {

    suspend operator fun invoke(lines:Array<String>){

    }

}