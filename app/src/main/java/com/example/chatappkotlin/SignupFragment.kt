package com.example.chatappkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupFragment : Fragment() {

    private lateinit var usernameEditText :EditText
    private lateinit var passwordEditText :EditText
    private lateinit var displayNameEditText: EditText
    private lateinit var btn : Button

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_signup, container, false)

        usernameEditText = view.findViewById(R.id.signup_username_ET)
        passwordEditText = view.findViewById(R.id.signup_password_ET)
        displayNameEditText = view.findViewById(R.id.displayName)
        btn = view.findViewById(R.id.submit_btn)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference().child("Users")
        firebaseAuth = FirebaseAuth.getInstance()


        btn.setOnClickListener {
            val username = usernameEditText.text.toString()
            val displayName = displayNameEditText.text.toString()
            val password = passwordEditText.text.toString()

            firebaseAuth.createUserWithEmailAndPassword("$username@chatapp.com",password).addOnCompleteListener{
                if (it.isSuccessful){
                    databaseReference.child(username).setValue(mapOf("displayName" to displayName))
                }
            }.addOnFailureListener{
                Toast.makeText(requireContext(),"SignUp Failed",Toast.LENGTH_LONG).show()
            }

        }


        return view
    }

}