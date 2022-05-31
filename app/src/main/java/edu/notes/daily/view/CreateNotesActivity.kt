package edu.notes.daily.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import edu.notes.daily.R
import edu.notes.daily.util.fadeInAnimation
import edu.notes.daily.util.navigate
import edu.notes.daily.util.slideInLeftAnimation
import edu.notes.daily.viewmodel.UserViewModel

class CreateNotesActivity : AppCompatActivity() {
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var addNotesButton : ConstraintLayout

    private lateinit var backButton : ImageView

    private lateinit var addNotesText : TextView
    private lateinit var title : EditText
    private lateinit var notes : EditText

    private lateinit var userViewModel : UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_create_notes)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        constraintLayout = findViewById(R.id.constraintLayout)

        title = findViewById(R.id.titleEdittext)
        notes = findViewById(R.id.notesEdittext)

        addNotesText = findViewById(R.id.addNotesText)

        addNotesButton = findViewById(R.id.addNotesButton)
        backButton = findViewById(R.id.backButton)

        backButton.setOnClickListener {
            fadeInAnimation(backButton,this@CreateNotesActivity)
            navigate(this@CreateNotesActivity,MainActivity(),::finish)
        }

        addNotesButton.setOnClickListener {
            fadeInAnimation(addNotesButton,this@CreateNotesActivity)
            userViewModel.createNotes(title,notes,addNotesText,this@CreateNotesActivity)
        }
    }

    override fun onStart() {
        super.onStart()
        slideInLeftAnimation(constraintLayout,this@CreateNotesActivity)
    }
}