package com.plcoding.cleanarchitecturenoteapp.feature_note.data.repository

import com.plcoding.cleanarchitecturenoteapp.feature_note.data.data_source.QuestionDao
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Answer
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Question
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.QuestionAndAnswer
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow

class QuestionRepositoryImpl(
    private val dao: QuestionDao
): QuestionRepository {
    override fun getQuestions(): Flow<List<Question>> {
        return dao.getQuestions()
    }

    override fun getQuestionsWithAnswers(quizId: Int): Flow<List<QuestionAndAnswer>> {
        return   dao.getQuestionsWithAnswers(quizId)
    }

    override suspend fun createQuestion(question: Question) {
        dao.createQuestion(question)
    }

    override suspend fun addAnswer(answer: Answer) {
        dao.addAnswer(answer)
    }

    override suspend fun deleteQuestion(question: Question) {
        dao.deleteQuestion(question = question)
    }

    override suspend fun deleteAnswer(answer: Answer) {
        dao.deleteAnswer(answer)
    }
}