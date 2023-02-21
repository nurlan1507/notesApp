package com.plcoding.cleanarchitecturenoteapp.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Answer
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Question

@Database(
    entities = [Note::class, Question::class, Answer::class],
    version = 3
)
abstract class NoteDatabase:RoomDatabase() {

    abstract val noteDao:NoteDao
    abstract val questionDao:QuestionDao

    companion object{
        const val DATABASE_NAME = "notes_db"
    }

}