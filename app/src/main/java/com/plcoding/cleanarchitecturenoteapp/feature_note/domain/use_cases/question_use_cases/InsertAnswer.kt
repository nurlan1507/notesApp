package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_cases.question_use_cases

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Answer
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.QuestionRepository

class InsertAnswer(private val repository: QuestionRepository) {
    suspend operator fun invoke(answer: Answer){
        repository.addAnswer(answer)
    }
}