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
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.textfield.TextInputEditText
import de.hdodenhof.circleimageview.CircleImageView
import edu.notes.daily.R
import edu.notes.daily.util.fadeInAnimation
import edu.notes.daily.util.navigate
import edu.notes.daily.util.slideInLeftAnimation
import edu.notes.daily.util.toast
import edu.notes.daily.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var constraintLayout: ConstraintLayout

    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var loginText : TextView

    private lateinit var mLogoIllusttration : CircleImageView
    private lateinit var backButton : ImageView

    private lateinit var loginButton : ConstraintLayout

    private lateinit var userViewModel : UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_login)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        mLogoIllusttration = findViewById(R.id.loginIllustration)

        constraintLayout = findViewById(R.id.constraintLayout)

        email = findViewById(R.id.emailEditText)
        password = findViewById(R.id.passwordEditText)

        loginButton = findViewById(R.id.loginButton)
        loginText = findViewById(R.id.loginText)
        backButton = findViewById(R.id.backButton)

        loginButton.setOnClickListener {
            fadeInAnimation(loginButton,this)
            userViewModel.login(email!!,password!!,loginText!!,this)
            userViewModel.getLoginLiveData().observe(this, Observer { user ->
                if(user != null){
                    navigate(this@LoginActivity,MainActivity(),::finish)
                }else{
                    toast("bad authentication",this)
                }
            })
        }

        backButton.setOnClickListener {
            fadeInAnimation(backButton,this@LoginActivity)
            navigate(this@LoginActivity,SignupActivity(),::finish)
        }
    }

    override fun onStart() {
        super.onStart()
        slideInLeftAnimation(constraintLayout!!,this@LoginActivity)
    }
}