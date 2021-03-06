package com.example.onlineveterinarydoctor.presentation.nav_login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.onlineveterinarydoctor.MainActivity
import com.example.onlineveterinarydoctor.R
import com.example.onlineveterinarydoctor.databinding.ScreenLoginBinding
import com.example.onlineveterinarydoctor.presentation.nav_login.models.Account
import com.example.onlineveterinarydoctor.utils.scope
import com.example.onlineveterinarydoctor.utils.showToast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import java.lang.Exception

class LoginScreen:Fragment(R.layout.screen_login) {

    private val binding by viewBinding(ScreenLoginBinding::bind)
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var firebaseDatabase : FirebaseDatabase
    private lateinit var reference : DatabaseReference

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("doctors")

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity() , gso)


        cvSignInGoogle.setOnClickListener {
            signIn()
        }


    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent , 1)
    }

    override fun onActivityResult(requestCode : Int , resultCode : Int , data : Intent?) {
        super.onActivityResult(requestCode , resultCode , data)

        if (requestCode == 1) {
            val task : Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)

        }
    }


    private fun handleSignInResult(completedTask : Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account.idToken ?: "")

        } catch (e : ApiException) {
            showToast("E r r o r 1: $e")
        }

    }

    private fun firebaseAuthWithGoogle(idToken : String) {
        val credential = GoogleAuthProvider.getCredential(idToken , null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    reference.addListenerForSingleValueEvent(object:ValueEventListener {
                        override fun onDataChange(snapshot : DataSnapshot) {
                            try {

                                val children = snapshot.children
                                var isHave = false
                                for (child in children) {
                                    if (child.key == user?.uid) {
                                        isHave = true
                                        break
                                    }
                                }

                                if (!isHave) {
                                    val account = Account(
                                        uid = user?.uid.toString() ,
                                        displayName = user?.displayName.toString() ,
                                        email = user?.email.toString() ,
                                        phoneNumber = user?.phoneNumber ?: "" ,
                                        photoUrl = user?.photoUrl.toString() ,
                                    )
                                    reference.child(user?.uid ?: "").setValue(account)
                                        .addOnCompleteListener {
                                            if (it.isSuccessful) {
                                                val intent =
                                                    Intent(
                                                        requireActivity() ,
                                                        MainActivity::class.java
                                                    )
                                                startActivity(intent)
                                            } else {
                                                showToast("${it.exception?.message}")
                                            }
                                        }
                                } else {
                                    val intent =
                                        Intent(requireActivity() , MainActivity::class.java)
                                    startActivity(intent)
                                }
                            } catch (e : Exception) {
                                showToast(e.message.toString())
                            }
                        }

                        override fun onCancelled(error : DatabaseError) {
                            showToast(error.message)
                        }
                    })

                } else {
                    showToast("error2:${task.exception?.message.toString()}")
                }
            }
    }


}