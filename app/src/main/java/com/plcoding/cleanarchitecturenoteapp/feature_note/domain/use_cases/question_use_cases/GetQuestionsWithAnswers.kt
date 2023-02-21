package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_cases.question_use_cases

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.QuestionRepository

class GetQuestionsWithAnswers(private val repository: QuestionRepository) {
    operator fun invoke(id: Int) {
        repository.getQuestionsWithAnswers(id)
    }
}