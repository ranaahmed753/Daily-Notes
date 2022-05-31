package edu.notes.daily.model

import android.app.Application
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import edu.notes.daily.data.bindings.adapter.NotesAdapter
import edu.notes.daily.data.datamodel.Notes
import edu.notes.daily.util.toast
import edu.notes.daily.view.MainActivity
import org.w3c.dom.Text
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class UserRepository : Application() {
    var userMutableLiveData : MutableLiveData<FirebaseUser>
    var loginMutableLiveData : MutableLiveData<FirebaseUser>
    var deleteNotesLiveData : MutableLiveData<Boolean>
    var updateNotesLiveData : MutableLiveData<Boolean>
    var isLoginLiveData : MutableLiveData<String>
    var hasNotesLiveData : MutableLiveData<Boolean>
    var userAuth : FirebaseAuth
    var userRef : DatabaseReference
    var notesRef : DatabaseReference
    lateinit var query : Query
    var userMap : HashMap<String,String>
    var notesMap : HashMap<String,String>

    init {
        userMutableLiveData = MutableLiveData()
        loginMutableLiveData = MutableLiveData()
        deleteNotesLiveData = MutableLiveData()
        updateNotesLiveData = MutableLiveData()
        isLoginLiveData = MutableLiveData()
        hasNotesLiveData = MutableLiveData()
        userAuth = FirebaseAuth.getInstance()
        userRef = FirebaseDatabase.getInstance().getReference().child("Users")
        notesRef = FirebaseDatabase.getInstance().getReference().child("Notes")
        userMap = HashMap()
        notesMap = HashMap()

    }

    fun signup(email : String,password : String,confirmPassword : String,signupText : TextView,context : Context){
        signupText.text = "Creating User...."
        userAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                userMap.put("email",email)
                userMap.put("password",password)
                userRef.child(userAuth.currentUser?.uid!!).setValue(userMap).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        userMutableLiveData.postValue(userAuth.currentUser)
                        hasNotesLiveData.postValue(false)
                        signupText.text = "Signup"
                        toast("user created successfully",context)
                    }else{
                        userMutableLiveData.postValue(null)
                        hasNotesLiveData.postValue(false)
                        signupText.text = "Signup"
                        toast("opps!something went wrong!!",context)
                    }
                }
            }else{
                userMutableLiveData.postValue(null)
                toast("user not created!!",context)
            }
        }
    }


    fun getUserLiveData() : MutableLiveData<FirebaseUser>{
        return userMutableLiveData
    }


    fun login(email: String,password: String,loginText : TextView,context: Context){
        loginText.text = "Authenticating...."
        userAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                loginMutableLiveData.postValue(userAuth.currentUser)
                loginText.text = "Sign In"
                toast("authentication successfull",context)
            }else{
                loginMutableLiveData.postValue(null)
                loginText.text = "Sign In"
                toast("authentication failed!!",context)
            }
        }
    }


    fun getLoginLiveData() : MutableLiveData<FirebaseUser>{
        return loginMutableLiveData
    }

    fun getAuth() : FirebaseAuth{
        return userAuth
    }


    fun createNotes(title : EditText,notes : EditText,addNotesText : TextView,date : String,context : Context){
        addNotesText.text = "Creating Notes...."
        notesMap.put("title", title.text.toString())
        notesMap.put("notes",notes.text.toString())
        notesMap.put("date",date)
        notesMap.put("color","default")
        notesRef.child(userAuth.currentUser?.uid!!).push().setValue(notesMap).addOnCompleteListener { task ->
            if(task.isSuccessful){
                hasNotesLiveData.postValue(true)
                addNotesText.text = "Add Notes"
                toast("notes created successfully",context)
                title.text.clear()
                notes.text.clear()
            }else{
                hasNotesLiveData.postValue(false)
                addNotesText.text = "Add Notes"
                toast("notes created failed!!",context)
            }
        }
    }


    fun getAllNotes(notesList: ArrayList<Notes>, notesAdapter: NotesAdapter){
            notesRef.child(userAuth.currentUser?.uid!!).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    notesList.clear()
                    for(datasnapshot in snapshot.children){
                        val title = datasnapshot.child("title").value.toString()
                        val notes = datasnapshot.child("notes").value.toString()
                        val date = datasnapshot.child("date").value.toString()
                        val color = datasnapshot.child("color").value.toString()
                        notesList.add(Notes(title,notes,date,datasnapshot.key.toString(),color))

                    }
                    notesAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    fun getAllNoteLiveData() : LiveData<Boolean> {
        return hasNotesLiveData
    }


    fun deleteNotes(notesId : String,context: Context){
        notesRef.child(userAuth.currentUser?.uid!!).child(notesId).removeValue().addOnCompleteListener { task ->
            if(task.isSuccessful){
                deleteNotesLiveData.postValue(true)
                toast("notes deleted",context)
            }else{
                deleteNotesLiveData.postValue(false)
                toast("notes deletion failed!!",context)
            }
        }
    }

    fun getDeletedNotesLiveData() : MutableLiveData<Boolean>{
        return deleteNotesLiveData
    }

    fun updateNotesTitle(notesId : String,title : EditText,updateNotesTitleButtonText : TextView,context: Context) {

        updateNotesTitleButtonText.text = "Updating....."
        notesRef.child(userAuth.currentUser?.uid!!)
            .child(notesId)
            .child("title")
            .setValue(title.text.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateNotesLiveData.postValue(true)
                    toast("notes title updated", context)
                    updateNotesTitleButtonText.text = "Update Notes Title"
                } else {
                    updateNotesLiveData.postValue(false)
                    toast("notes title updated failed!!", context)
                    updateNotesTitleButtonText.text = "Update Notes Title"
                }

            }
    }

    fun getUpdatedNotesLiveData() : MutableLiveData<Boolean>{
        return updateNotesLiveData
    }

    fun updateNotes(notesId : String,editedNotes : EditText,updateNotesButtonText : TextView,context: Context){
        updateNotesButtonText.text = "Updating....."
        notesRef.child(userAuth.currentUser?.uid!!)
            .child(notesId)
            .child("notes")
            .setValue(editedNotes.text.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toast("notes updated", context)
                    updateNotesButtonText.text = "Update Notes"
                } else {
                    toast("notes updated failed!!", context)
                    updateNotesButtonText.text = "Update Notes"
                }

            }
    }


    fun changeThemeColor(notesId : String,color : String,position : Int){
        notesRef.child(userAuth.currentUser?.uid!!)
            .child(notesId)
            .child("color")
            .setValue(color)

    }

    fun searchNotes(notesList : ArrayList<Notes>,searchText : String,notesAdapter: NotesAdapter){
        val  queryRef : DatabaseReference
        queryRef = FirebaseDatabase.getInstance().getReference().child("Notes").child(userAuth.currentUser?.uid!!)
        query =  queryRef
            .orderByChild("title")
            .startAt(searchText.toString())
            .endAt(searchText.toString()+"\uf8ff")

        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                notesList.clear()
                for(datasnapshot in snapshot.children){
                    val title = datasnapshot.child("title").value.toString()
                    val notes = datasnapshot.child("notes").value.toString()
                    val date = datasnapshot.child("date").value.toString()
                    val color = datasnapshot.child("color").value.toString()
                    notesList.add(Notes(title,notes,date,datasnapshot.key.toString(),color))

                }
                notesAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


}