package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_cases.question_use_cases

data class QuestionUseCases(
    val getQuestionsWithAnswers: GetQuestionsWithAnswers,
    val inserQuestion: InserQuestion,
    val insertAnswer: InsertAnswer,
    val deleteQuestion: DeleteQuestion,
    val deleteAnswer: DeleteAnswer
)