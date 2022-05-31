package edu.notes.daily.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import edu.notes.daily.R
import edu.notes.daily.util.slideInLeftAnimation
import edu.notes.daily.util.slideInRightAnimation

class NavigationDrawerActivity : AppCompatActivity() {
    private lateinit var constraintLayout: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)
        constraintLayout = findViewById(R.id.constraintLayout)
    }

    override fun onStart() {
        super.onStart()
        slideInRightAnimation(constraintLayout,this)
    }
}