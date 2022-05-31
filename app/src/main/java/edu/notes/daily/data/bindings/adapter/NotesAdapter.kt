package edu.notes.daily.data.bindings.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import edu.notes.daily.R
import edu.notes.daily.data.bindings.viewholder.NotesViewHolder
import edu.notes.daily.data.datamodel.Notes
import edu.notes.daily.util.fadeInAnimation
import edu.notes.daily.util.navigate
import edu.notes.daily.view.DetailsActivity
import edu.notes.daily.view.MainActivity
import edu.notes.daily.view.UpdateNotesActivity
import edu.notes.daily.viewmodel.UserViewModel

class NotesAdapter(var notesList : ArrayList<Notes>,var notesContext : Context) : RecyclerView.Adapter<NotesViewHolder>(),NotesViewHolder.onItemClick {
    var userViewModel : UserViewModel

    init {
        userViewModel = ViewModelProvider(notesContext as MainActivity).get(UserViewModel::class.java)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes,parent,false)
        return NotesViewHolder(view,parent.context)

    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.fadeInAnim(holder.constraintLayout,notesContext)
        holder.bind(notesList[position])
        holder.onClick(holder.constraintLayout,notesContext,::onNavigateToDetailsPage,position,holder)
        holder.onMenuClick(holder.menuButton,notesContext,::onMenu,position,holder)
        holder.onChangeTheme(holder.changeThemeButton,position,holder,notesContext,notesList[position].id)
        holder.bindBackgroundColor(holder.constraintLayout,notesList[position].color)

    }

    override fun getItemCount(): Int {
        return notesList.size

    }


    override fun onNavigateToDetailsPage(position: Int, holder: NotesViewHolder) {
        val intent = Intent(notesContext,DetailsActivity::class.java)
        intent.putExtra("title",notesList[position].title)
        intent.putExtra("notes",notesList[position].notes)
        intent.putExtra("date",notesList[position].date)
        intent.putExtra("id",notesList[position].id)
        intent.putExtra("color",notesList[position].color)
        notesContext.startActivity(intent)
        (notesContext as MainActivity).finish()
    }


    override fun onMenu(position: Int, holder: NotesViewHolder) {

        var updateNotes : TextView? = null
        var deleteNotes : TextView? = null
        var cancelText : TextView? = null

        val dialog = Dialog(notesContext)
        dialog.setContentView(R.layout.custom_popup)
        dialog.window!!.setBackgroundDrawable(getDrawable(notesContext,R.drawable.custom_popup_back))
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        updateNotes = dialog.findViewById(R.id.updateNotesText)
        deleteNotes = dialog.findViewById(R.id.deleteNotesText)
        cancelText = dialog.findViewById(R.id.cancelText)

        dialog.setCancelable(false)
        dialog.show()

        updateNotes.setOnClickListener {
            fadeInAnimation(updateNotes,notesContext)
            val intent = Intent(notesContext,UpdateNotesActivity::class.java)
            intent.putExtra("id",notesList[position].id)
            intent.putExtra("title",notesList[position].title)
            intent.putExtra("notes",notesList[position].notes)
            intent.putExtra("color",notesList[position].color)
            notesContext.startActivity(intent)
            (notesContext as MainActivity).finish()

            dialog.cancel()
        }


        deleteNotes.setOnClickListener {
            fadeInAnimation(deleteNotes,notesContext)
            userViewModel.deleteNotes(notesList[position].id,notesContext)
            userViewModel.getDeleteNoteLiveDta().observe(notesContext as MainActivity, Observer { isNoteDeleted ->
                if(isNoteDeleted){
                    dialog.cancel()
                    //MainActivity().onDeleteToNotify(position)
                }else{
                    dialog.cancel()
                }
            })
        }


        cancelText.setOnClickListener {
            fadeInAnimation(cancelText,notesContext)
            dialog.cancel()
        }

    }

}