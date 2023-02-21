package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_cases.question_use_cases

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Answer
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.QuestionRepository

class DeleteAnswer(private val repository: QuestionRepository) {
    suspend operator fun invoke(answer: Answer){
        repository.deleteAnswer(answer)
    }
}