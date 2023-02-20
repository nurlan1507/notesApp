package com.plcoding.cleanarchitecturenoteapp.di

import android.app.Application
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.room.Room
import androidx.room.RoomDatabase
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.data_source.NoteDao
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.data_source.NoteDatabase
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.repository.NoteRepositoryImpl
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(app:Application):NoteDatabase{
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun noteRepository(db:NoteDatabase):NoteRepository{
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun noteUseCases(noteRepository:NoteRepository):NoteUseCases{
        return NoteUseCases(
            GetNotesUseCase(noteRepository),
            DeleteNoteUseCase(noteRepository),
            AddNote(noteRepository),
            GetNoteUseCase(noteRepository)
        )
    }
}