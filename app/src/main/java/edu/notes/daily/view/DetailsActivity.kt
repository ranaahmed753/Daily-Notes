package edu.notes.daily.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import edu.notes.daily.R
import edu.notes.daily.util.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var backButton : ConstraintLayout

    private lateinit var title : TextView
    private lateinit var date : TextView
    private lateinit var notes : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_details)

        val noteTitle = intent.getStringExtra("title")
        val noteDate = intent.getStringExtra("date")
        val note = intent.getStringExtra("notes")
        val id = intent.getStringExtra("id")
        val color = intent.getStringExtra("color")

        constraintLayout = findViewById(R.id.constraintLayout)
        if(color.equals("default")){
            changeBackgroundColor("#AECBFA",constraintLayout)
        }else{
            changeBackgroundColor(color!!,constraintLayout)
        }

        backButton = findViewById(R.id.backButton)

        title = findViewById(R.id.title)
        date = findViewById(R.id.date)
        notes = findViewById(R.id.notes)

        title.text = noteTitle
        date.text = noteDate
        notes.text = note

        backButton.setOnClickListener {
            fadeInAnimation(backButton,this@DetailsActivity)
            navigate(this@DetailsActivity,MainActivity(),::finish)
        }

    }

    override fun onStart() {
        super.onStart()
        slideInLeftAnimation(constraintLayout,this@DetailsActivity)
    }
}