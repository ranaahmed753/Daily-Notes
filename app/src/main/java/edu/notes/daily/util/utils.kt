package edu.notes.daily.util

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.snackbar.Snackbar
import edu.notes.daily.R
import top.defaults.drawabletoolbox.Constants
import top.defaults.drawabletoolbox.DrawableBuilder




fun toast(message : String,context : Context){
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()

}

fun fadeInAnimation(widget : View,context: Context){
    widget.startAnimation(AnimationUtils.loadAnimation(context,android.R.anim.fade_in))
}

fun slideInLeftAnimation(widget : View,context: Context){
    widget.startAnimation(AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left))
}

fun navigate(currentContext: Context,targetContext: Context,finish : () -> Unit){
    val intent = Intent(currentContext,targetContext::class.java)
    currentContext.startActivity(intent)
    finish()
}

fun changeTheme(color : String,widget: ConstraintLayout){
        val drawable = DrawableBuilder()
        .rectangle()
        .solidColor(Color.parseColor(color))
        .topLeftRadius(20)
        .topRightRadius(20)
        .bottomLeftRadius(20)
        .bottomRightRadius(20)
        .build()
        widget.background = drawable
}

fun changeBackgroundColor(color : String,widget: View){
    val drawable = DrawableBuilder()
        .rectangle()
        .solidColor(Color.parseColor(color))
        .build()
    widget.background = drawable
}

fun changeButtonBackground(color : String,widget: ConstraintLayout){
    val drawable = DrawableBuilder()
        .rectangle()
        .solidColor(Color.parseColor(color))
        .topLeftRadius(999)
        .topRightRadius(999)
        .bottomLeftRadius(999)
        .bottomRightRadius(999)
        .build()
    widget.background = drawable
}

fun changeTextColor(color: String,widget: TextView){
    widget.setTextColor(Color.parseColor(color))
}

fun changeBackButtonColor(color : String,widget: ImageView){
    widget.setColorFilter(Color.parseColor(color))
}

fun changeEditTextStyle(color: String,widget: EditText){

    widget.highlightColor = Color.parseColor(color)
}

fun slideInRightAnimation(widget : View,context: Context){
    widget.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_in_left))
}
