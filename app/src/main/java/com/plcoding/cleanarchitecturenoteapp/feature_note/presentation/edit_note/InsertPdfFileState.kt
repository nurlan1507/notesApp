package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.edit_note

sealed class  InsertPdfFileState{
    object Inactive:InsertPdfFileState()
    object Loading:InsertPdfFileState()
    data class EndedError(val error:String):InsertPdfFileState()
    object EndedSuccess:InsertPdfFileState()
}