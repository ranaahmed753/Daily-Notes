package edu.notes.daily.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import edu.notes.daily.R
import edu.notes.daily.util.fadeInAnimation
import edu.notes.daily.util.navigate
import edu.notes.daily.util.slideInLeftAnimation
import edu.notes.daily.util.toast
import edu.notes.daily.viewmodel.UserViewModel

class SignupActivity : AppCompatActivity() {
    private lateinit var constraintLayout : ConstraintLayout
    private lateinit var signupButton : ConstraintLayout

    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var confirmPassword : EditText

    private lateinit var signupText : TextView

    private lateinit var forwardButton : ImageView

    private lateinit var userViewModel : UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_signup)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        signupText = findViewById(R.id.signupText)
        email = findViewById(R.id.emailEditText)
        password = findViewById(R.id.passwordEditText)
        confirmPassword = findViewById(R.id.confirmPasswordEditText)

        constraintLayout = findViewById(R.id.constraintLayout)
        signupButton = findViewById(R.id.signupButton)
        forwardButton = findViewById(R.id.backButton)

        signupButton?.setOnClickListener {
            fadeInAnimation(signupButton!!,this)
            userViewModel.signup(email!!,password!!,confirmPassword!!,signupText,this)
            userViewModel.getUserLiveData().observe(this, Observer { user ->
                if(user != null){
                    navigate(this@SignupActivity,LoginActivity(),::finish)
                }else{
                   toast("null user",this)
                }
            })
        }

        forwardButton.setOnClickListener {
            fadeInAnimation(forwardButton,this@SignupActivity)
            navigate(this@SignupActivity,LoginActivity(),::finish)
        }


    }

    override fun onStart() {
        super.onStart()
        slideInLeftAnimation(constraintLayout!!,this)
    }
}