package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model

import androidx.room.*



@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="question_id")
    val id:Int,
    @ColumnInfo(name="quiz_id")
    val quizId:Int,
    val text:String
)


@Entity(tableName="answers")
data class Answer(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="answer_id")
    val id:Int,
    @ColumnInfo(name = "question_id")
    val questionId:Int,
    val text:String,
    val isCorrect:Boolean
)

data class QuestionAndAnswer(
    @Embedded
    val question:Question,
    @Relation(
        parentColumn = "question_id",
        entityColumn = "question_id",
    )
    val answers:List<Answer>
)