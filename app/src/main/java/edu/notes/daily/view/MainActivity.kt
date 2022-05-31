package edu.notes.daily.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.notes.daily.R
import edu.notes.daily.data.bindings.adapter.NotesAdapter
import edu.notes.daily.data.bindings.viewholder.NotesViewHolder
import edu.notes.daily.data.datamodel.Notes
import edu.notes.daily.util.*
import edu.notes.daily.viewmodel.UserViewModel
import kotlin.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    private lateinit var addNoteButton : ImageView
    private lateinit var navigationToggleButton : ImageView

    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var searchConstraintLayout : ConstraintLayout

    private lateinit var recyclerView: RecyclerView

    private lateinit var searchText : EditText

    private lateinit var dailyText : TextView
    private lateinit var notesText : TextView

    private lateinit var notesList : ArrayList<Notes>
    private lateinit var notesAdapter : NotesAdapter
    private lateinit var auth : FirebaseAuth

    private var hasNotes : Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        auth = userViewModel.getUserAuth()

        recyclerView = findViewById(R.id.recyclerview)

        constraintLayout = findViewById(R.id.constraintLayout)
        addNoteButton = findViewById(R.id.addNoteButton)
        searchConstraintLayout = findViewById(R.id.searchConstraintLayout)

        searchText = findViewById(R.id.search)
        dailyText = findViewById(R.id.dailyText)
        notesText = findViewById(R.id.notesText)

        navigationToggleButton = findViewById(R.id.navigationToggleButton)

        notesList = arrayListOf()
        notesAdapter = NotesAdapter(notesList,this@MainActivity)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = notesAdapter
        userViewModel.getAllNotes(notesList,notesAdapter)
        userViewModel.getAllNotesLivedata().observe(this@MainActivity, Observer { isNotesAdded ->
            println("The Notes Status :- $isNotesAdded")
            if(isNotesAdded){
                userViewModel.getAllNotes(notesList,notesAdapter)

            }else{
                toast("nothing found",this@MainActivity)
            }
        })

        searchText.addTextChangedListener { editableText ->
                if(editableText?.length != 0){
                    userViewModel.searchNotes(notesList,editableText.toString(),notesAdapter)
                }else{

                    userViewModel.getAllNotes(notesList,notesAdapter)

                }
        }

        addNoteButton.setOnClickListener {
            fadeInAnimation(addNoteButton,this@MainActivity)
            navigate(this@MainActivity,CreateNotesActivity(),::finish)
        }

        navigationToggleButton.setOnClickListener {
            fadeInAnimation(navigationToggleButton,this@MainActivity)
            initBottomNavigation()
        }



    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser == null){
            navigate(this,SignupActivity(),::finish)
        }
        slideInLeftAnimation(constraintLayout,this@MainActivity)

    }

   private fun initBottomNavigation(){
        val bottomDialog = BottomSheetDialog(this@MainActivity,R.style.BottomSheetStyle)
        bottomDialog.setContentView(R.layout.menu)
        bottomDialog.setCanceledOnTouchOutside(true)
        val logoutText = bottomDialog.findViewById<TextView>(R.id.logout)
        bottomDialog.show()
        logoutText?.setOnClickListener {
            fadeInAnimation(logoutText,this@MainActivity)
            navigate(this@MainActivity,SignupActivity(),::finish)

        }
    }
}