package edu.notes.daily.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import edu.notes.daily.data.bindings.adapter.NotesAdapter
import edu.notes.daily.data.datamodel.Notes
import edu.notes.daily.model.UserRepository
import edu.notes.daily.util.toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UserViewModel : ViewModel(){
    var userRepository : UserRepository
    var userMutableLiveData : MutableLiveData<FirebaseUser>
    var loginMutableLiveData : MutableLiveData<FirebaseUser>
    var deleteNotesLiveData : MutableLiveData<Boolean>
    var updateNotesLiveData : MutableLiveData<Boolean>
    var hasNotesLiveData : LiveData<Boolean>
    var auth : FirebaseAuth

    init {
        userRepository = UserRepository()
        userMutableLiveData = userRepository.getUserLiveData()
        loginMutableLiveData = userRepository.getLoginLiveData()
        deleteNotesLiveData = userRepository.getDeletedNotesLiveData()
        updateNotesLiveData = userRepository.getUpdatedNotesLiveData()
        hasNotesLiveData = userRepository.getAllNoteLiveData()
        auth = userRepository.getAuth()

    }

    fun signup(email : EditText,password : EditText,confirmPassword : EditText,signupText : TextView,context : Context){
        val useremail = email.text.toString()
        val userpassword = password.text.toString()
        val userconfirmpassword = confirmPassword.text.toString()

        if(TextUtils.isEmpty(useremail) || TextUtils.isEmpty(userpassword) || TextUtils.isEmpty(userconfirmpassword)){
            toast("fields are empty!!",context)
        }else{
            if(userconfirmpassword.equals(userpassword)){
                userRepository.signup(useremail,userpassword,userconfirmpassword,signupText,context)
            }else{
                toast("password not matched!!",context)
            }

        }

    }
    fun getUserLiveData() : MutableLiveData<FirebaseUser>{
        return userMutableLiveData
    }

    fun login(email : EditText,password : EditText,loginText : TextView,context : Context){
        val useremail = email.text.toString()
        val userpassword = password.text.toString()
        if(TextUtils.isEmpty(useremail) || TextUtils.isEmpty(userpassword)){
            toast("fields are empty!!",context)
        }else{
            userRepository.login(useremail,userpassword,loginText,context)
        }
    }

    fun getLoginLiveData() : MutableLiveData<FirebaseUser>{
        return loginMutableLiveData
    }


    fun getUserAuth() : FirebaseAuth{
        return auth
    }

    fun createNotes(title : EditText,notes : EditText,addNotesText : TextView,context : Context){
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        if(TextUtils.isEmpty(title.text.toString()) || TextUtils.isEmpty(notes.text.toString())){
            toast("please create your notes!!",context)
        }else{
            userRepository.createNotes(title,notes,addNotesText,currentDate,context)
        }
    }

    fun getAllNotes(notesList: ArrayList<Notes>,notesAdapter: NotesAdapter){
        userRepository.getAllNotes(notesList,notesAdapter)
    }

    fun getAllNotesLivedata() : LiveData<Boolean>{

        return hasNotesLiveData
    }

    fun deleteNotes(notesId : String,context: Context){
        userRepository.deleteNotes(notesId,context)
    }

    fun getDeleteNoteLiveDta() : MutableLiveData<Boolean>{
        return deleteNotesLiveData
    }

    fun updateNotesTitle(notesId : String, title : EditText,updateNotesButtonText : TextView, context: Context){

        if(TextUtils.isEmpty(title.text.toString())){
            toast("please fill the form!!",context)
        }else{
            userRepository.updateNotesTitle(notesId, title, updateNotesButtonText,context)
        }


    }

    fun getUpdatedNotesLiveData() : MutableLiveData<Boolean>{
        return updateNotesLiveData
    }

    fun updateNotes(notesId : String,editedNotes : EditText,updateNotesButtonText : TextView,context: Context){
        if(TextUtils.isEmpty(editedNotes.text.toString())){
            toast("please fill the form!!",context)
        }else{
            userRepository.updateNotes(notesId, editedNotes, updateNotesButtonText,context)
        }
    }

    fun changeThemeColor(notesId : String,color : String,position : Int){
        userRepository.changeThemeColor(notesId,color,position)
    }

    fun searchNotes(notesList : ArrayList<Notes>,searchText : String,notesAdapter: NotesAdapter){
        userRepository.searchNotes(notesList,searchText,notesAdapter)
    }

}