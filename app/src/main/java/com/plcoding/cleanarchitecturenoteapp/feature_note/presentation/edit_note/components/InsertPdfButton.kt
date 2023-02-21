package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.edit_note.components

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.MutableLiveData
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.edit_note.InsertPdfFileState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("Range", "UnrememberedMutableState")
@Composable
fun InsertPdfButton(text:String, context: Context, onClick:()->Unit){
    val ctx = context
    val pdfUri = remember { mutableStateOf<Uri?>(null) }
    var state by remember {
        mutableStateOf<InsertPdfFileState>(InsertPdfFileState.Inactive)
    }


    val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null && uri.scheme == "content") {
            pdfUri.value = uri
        }
        Log.d("YAHOO", "URA")
        Log.d("YAHOO", "URA")
        pdfUri.value = uri
        state = InsertPdfFileState.Loading
    }


    @SuppressLint("Range")
    fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            ctx.contentResolver.query(uri, null, null, null, null)?.use {
                if (it.moveToFirst()) {
                    result = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != -1) {
                result = result?.substring(cut!! + 1)
            }
        }
        return result
    }


    Column {
        Button(
            onClick = {
                getContent.launch("application/pdf") },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Insert your quiz")
        }
        if(pdfUri.value != null){
            AlertDialog(
                onDismissRequest = { pdfUri.value = null},
                title = {Text(text = "Add a pdf file of quiz")},
                text = {Text(text = "Do you want to add the selected PDF file?")},
                confirmButton = {
                    Button(modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp)
                        .height(40.dp)
                        .wrapContentWidth()
                        , onClick = {

                        try {
                            pdfUri?.value.let {
                                Log.d("ISLOADING", (state is InsertPdfFileState.Loading).toString())
                                val inputStream = ctx.contentResolver.openInputStream(it!!)
                                val filename = getFileName(it)
                                val pdfReader = PdfReader(inputStream)
                                val pageText = PdfTextExtractor.getTextFromPage(pdfReader, 1)
                                Log.d("Filename", filename.toString())
                            }
                        }catch (e:Exception){
                        }
                    }) {
                        LaunchedEffect(Unit){
                            launch {
                                while (true){
                                    delay(1000)
                                }
                            }
                        }
                        if(state is InsertPdfFileState.Loading){
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.Black
                            )
                            Text(text = "Add")
                        }

                        }
                },
                dismissButton = {
                    Button(modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp)
                        .height(40.dp)
                        .wrapContentWidth(),
                        onClick = {
                        pdfUri?.value.let {
                            Toast.makeText(ctx,"You addded a PDF", Toast.LENGTH_LONG).show()
                        }
                    }) {
                        Text(text = "Not add")
                    }
                },
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            )

        }
    }

}
