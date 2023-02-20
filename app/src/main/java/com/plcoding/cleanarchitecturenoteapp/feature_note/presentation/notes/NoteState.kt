package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes

import android.provider.ContactsContract.CommonDataKinds.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType


data class NoteState(
    val notes:List<com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note> = emptyList(),
    val order:NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible:Boolean = false
)
