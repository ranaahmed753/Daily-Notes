package edu.notes.daily.data.bindings.viewholder

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import edu.notes.daily.R
import edu.notes.daily.data.datamodel.Notes
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import edu.notes.daily.util.changeTheme
import edu.notes.daily.util.fadeInAnimation
import edu.notes.daily.util.toast
import edu.notes.daily.view.MainActivity
import edu.notes.daily.viewmodel.UserViewModel


class NotesViewHolder(itemView : View,context: Context) : RecyclerView.ViewHolder(itemView) {

    var title : TextView
    var date : TextView
    var constraintLayout : ConstraintLayout
    var menuButton : ConstraintLayout
    var changeThemeButton : ConstraintLayout
    var userViewModel : UserViewModel

    init {
        title = itemView.findViewById(R.id.title)
        date = itemView.findViewById(R.id.date)
        constraintLayout = itemView.findViewById(R.id.constraintLayout)
        menuButton = itemView.findViewById(R.id.menuButton)
        changeThemeButton = itemView.findViewById(R.id.changeThemeButton)
        userViewModel = ViewModelProvider(context as MainActivity).get(UserViewModel::class.java)

    }

    fun bind(notes : Notes){
        title.text = notes.title
        date.text = notes.date
    }

    fun bindBackgroundColor(widget: ConstraintLayout,color : String){
        if(color.equals("default")){
            changeTheme("#AECBFA",widget)
        }else{
            changeTheme(color,widget)
        }

    }

    fun onClick(widget : ConstraintLayout, context: Context, onDoSomething : (position : Int, holder : NotesViewHolder) -> Unit,position: Int,holder: NotesViewHolder){
        widget.setOnClickListener {
            fadeInAnimation(widget,context)
            onDoSomething(position,holder)

        }
    }

    fun onMenuClick(widget: View,context: Context,onDoHere : (position: Int, holder: NotesViewHolder) -> Unit,position: Int,holder: NotesViewHolder){
        widget.setOnClickListener {
            fadeInAnimation(widget,context)
            onDoHere(position,holder)
        }
    }

    fun fadeInAnim(widget: View,context: Context){
        fadeInAnimation(widget,context)
    }

    fun onChangeTheme(widget: View,position: Int,holder: NotesViewHolder,context: Context,notesId : String){
        widget.setOnClickListener {
            fadeInAnimation(widget,context)
            var bottomDialog = BottomSheetDialog(context as MainActivity,R.style.BottomSheetStyle)

            bottomDialog.setContentView(R.layout.bottom_nav_item)
            bottomDialog.setCanceledOnTouchOutside(true)
            bottomDialog.apply {
                BottomSheetBehavior.PEEK_HEIGHT_AUTO
            }
            val pinkColorTheme = bottomDialog.findViewById<ConstraintLayout>(R.id.pinkColorTheme)
            val greenGrayColorTheme = bottomDialog.findViewById<ConstraintLayout>(R.id.greenGrayColorTheme)
            val orangeColorTheme = bottomDialog.findViewById<ConstraintLayout>(R.id.orangeColorTheme)
            val skyColorTheme = bottomDialog.findViewById<ConstraintLayout>(R.id.skyColorTheme)
            val lightYellowColorTheme = bottomDialog.findViewById<ConstraintLayout>(R.id.lightYellowColorTheme)
            val lightVioletColorTheme = bottomDialog.findViewById<ConstraintLayout>(R.id.lightVioletColorTheme)
            val nightSkyColorTheme = bottomDialog.findViewById<ConstraintLayout>(R.id.nightSkyColorTheme)
            val lightGreenColorTheme = bottomDialog.findViewById<ConstraintLayout>(R.id.lightGreenColorTheme)
            val slimOrangeColorTheme = bottomDialog.findViewById<ConstraintLayout>(R.id.slimOrangeColorTheme)


            bottomDialog.show()
            pinkColorTheme?.setOnClickListener {
                fadeInAnimation(pinkColorTheme,context)
                userViewModel.changeThemeColor(notesId,"#D193DC",position)
                changeTheme("#D193DC",holder.constraintLayout)
            }
            greenGrayColorTheme?.setOnClickListener {
                fadeInAnimation(greenGrayColorTheme,context)
                userViewModel.changeThemeColor(notesId,"#90A4AE",position)
                changeTheme("#90A4AE",holder.constraintLayout)
            }
            orangeColorTheme?.setOnClickListener {
                fadeInAnimation(orangeColorTheme,context)
                userViewModel.changeThemeColor(notesId,"#FFAA93",position)
                changeTheme("#FFAA93",holder.constraintLayout)
            }
            skyColorTheme?.setOnClickListener {
                fadeInAnimation(skyColorTheme,context)
                userViewModel.changeThemeColor(notesId,"#8BD2C2",position)
                changeTheme("#8BD2C2",holder.constraintLayout)
            }
            lightYellowColorTheme?.setOnClickListener {
                fadeInAnimation(lightYellowColorTheme,context)
                userViewModel.changeThemeColor(notesId,"#FFF373",position)
                changeTheme("#FFF373",holder.constraintLayout)
            }
            lightVioletColorTheme?.setOnClickListener {
                fadeInAnimation(lightVioletColorTheme,context)
                userViewModel.changeThemeColor(notesId,"#C8C8FB",position)
                changeTheme("#C8C8FB",holder.constraintLayout)
            }
            nightSkyColorTheme?.setOnClickListener {
                fadeInAnimation(nightSkyColorTheme,context)
                userViewModel.changeThemeColor(notesId,"#B8CEE6",position)
                changeTheme("#B8CEE6",holder.constraintLayout)
            }
            lightGreenColorTheme?.setOnClickListener {
                fadeInAnimation(lightGreenColorTheme,context)
                userViewModel.changeThemeColor(notesId,"#C4F09D",position)
                changeTheme("#C4F09D",holder.constraintLayout)
            }
            slimOrangeColorTheme?.setOnClickListener {
                fadeInAnimation(slimOrangeColorTheme,context)
                userViewModel.changeThemeColor(notesId,"#F7DECD",position)
                changeTheme("#F7DECD",holder.constraintLayout)
            }
        }
    }

    interface onItemClick{
        fun onNavigateToDetailsPage(position: Int, holder: NotesViewHolder)
        fun onMenu(position: Int, holder: NotesViewHolder)
    }


}