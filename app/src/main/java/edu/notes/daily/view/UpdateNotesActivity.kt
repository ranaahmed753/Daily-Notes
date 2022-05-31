package edu.notes.daily.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.notes.daily.R
import edu.notes.daily.util.*
import edu.notes.daily.viewmodel.UserViewModel

class UpdateNotesActivity : AppCompatActivity() {

    private lateinit var constraintLayout: ConstraintLayout

    private lateinit var title : EditText
    private lateinit var notes : EditText

    private lateinit var updateNotesTitleButton : ConstraintLayout
    private lateinit var updateNotesButton : ConstraintLayout

    private lateinit var updateNotesTitleButtonText : TextView
    private lateinit var updateNotesButtonText : TextView
    private lateinit var notesText : TextView
    private lateinit var backButton : ImageView

    private lateinit var userViewModel : UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_update_notes)

        val notesId = intent.getStringExtra("id")
        val notesTitle = intent.getStringExtra("title")
        val note = intent.getStringExtra("notes")
        val color = intent.getStringExtra("color")

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        constraintLayout = findViewById(R.id.constraintLayout)

        notesText = findViewById(R.id.notesText)

        if(color.equals("default")){
            changeTextColor("#AECBFA",notesText)
        }else{
            changeTextColor(color!!,notesText)
        }

        title = findViewById(R.id.titleEdittext)
        notes = findViewById(R.id.notesEdittext)

        title.setText(notesTitle)
        notes.setText(note)

        updateNotesTitleButton = findViewById(R.id.updateNotesTitleButton)
        updateNotesButton = findViewById(R.id.updateNotesButton)

        if(color.equals("default")){
            changeButtonBackground("#AECBFA",updateNotesTitleButton)
            changeButtonBackground("#AECBFA",updateNotesButton)
        }else{
            changeButtonBackground(color!!,updateNotesTitleButton)
            changeButtonBackground(color,updateNotesButton)
        }

        updateNotesTitleButtonText = findViewById(R.id.updateNotesTitleButtonText)
        updateNotesButtonText = findViewById(R.id.updateNotesButtonText)

        backButton = findViewById(R.id.backButton)

        if(color.equals("default")){
            changeBackButtonColor("#AECBFA",backButton)
        }else{
            changeBackButtonColor(color!!,backButton)
        }

        backButton.setOnClickListener {
            fadeInAnimation(backButton,this@UpdateNotesActivity)
            navigate(this@UpdateNotesActivity,MainActivity(),::finish)
        }

        updateNotesTitleButton.setOnClickListener {
            fadeInAnimation(updateNotesTitleButton,this@UpdateNotesActivity)
            userViewModel.updateNotesTitle(notesId!!,title,updateNotesTitleButtonText,this@UpdateNotesActivity)
//            userViewModel.getUpdatedNotesLiveData().observe(this, Observer { isNotesUpdated ->
//                if(isNotesUpdated){
//
//                }
//            })
        }

        updateNotesButton.setOnClickListener {
            fadeInAnimation(updateNotesButton,this@UpdateNotesActivity)
            userViewModel.updateNotes(notesId!!,notes,updateNotesButtonText,this@UpdateNotesActivity)
        }
    }

    override fun onStart() {
        super.onStart()
        slideInLeftAnimation(constraintLayout,this@UpdateNotesActivity)
    }
}