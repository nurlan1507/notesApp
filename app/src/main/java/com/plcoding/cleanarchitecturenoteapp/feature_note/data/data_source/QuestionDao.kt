package com.plcoding.cleanarchitecturenoteapp.feature_note.data.data_source

import androidx.room.*
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Answer
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Question
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.QuestionAndAnswer
import kotlinx.coroutines.flow.Flow


@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions")
    fun getQuestions(): Flow<List<Question>>

    @Query("SELECT * FROM questions where quiz_id = :quizId")
    fun getQuestionsWithAnswers(quizId:Int): Flow<List<QuestionAndAnswer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createQuestion(question: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAnswer(answer: Answer)

    @Delete
    suspend fun deleteQuestion(question:Question)

    @Delete
    suspend fun deleteAnswer(answer:Answer)
}