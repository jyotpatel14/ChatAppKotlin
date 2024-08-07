package com.example.chatappkotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue


class LoginFragment : Fragment() {

    private lateinit var usernameEditText : EditText
    private lateinit var passwordEditText : EditText
    private lateinit var btn : Button

    private lateinit var userPreferences: UserPreferences

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

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
        val view =  inflater.inflate(R.layout.fragment_login, container, false)

        usernameEditText = view.findViewById(R.id.login_username_ET)
        passwordEditText = view.findViewById(R.id.login_password_ET)
        btn = view.findViewById(R.id.submit_btn)
        userPreferences = UserPreferences(requireContext())

        usernameEditText.imeOptions = EditorInfo.IME_ACTION_NEXT
        passwordEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        shiftFocusFrom(usernameEditText,passwordEditText)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference().child("Users")
        firebaseAuth = FirebaseAuth.getInstance()

        btn.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            firebaseAuth.signInWithEmailAndPassword("$username@chatapp.com",password).addOnCompleteListener{
                if (it.isSuccessful){
                    databaseReference.child(username)
                        .addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                Log.d("Firebase", "Snapshot value: ${snapshot.value}")
                                var displayName: String?= null
                                if (snapshot.exists()){
                                    displayName = snapshot.child("displayName").getValue<String>()
                                    Toast.makeText(requireContext(), "$displayName $username",Toast.LENGTH_LONG).show()
                                }
                                // val displayName = snapshot.child("displayName").value.toString()


                                if(displayName!=null){

                                    userPreferences.saveUserData(username,displayName)

                                    val intent = Intent(requireContext(),MainActivity::class.java)
                                    startActivity(intent)
                                    requireActivity().finish()
                                }
                            }
                            override fun onCancelled(error: DatabaseError) {
                                Toast.makeText(requireContext(),"DisplayName Not Found",Toast.LENGTH_LONG).show()
                            }
                        })

                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(),"Login failed",Toast.LENGTH_LONG).show()
            }

        }


        return view
    }

    private fun shiftFocusFrom(viewCurr : EditText, nextView : EditText) =

        //the Edit Texts required to be inputType = single Line

        viewCurr.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                nextView.requestFocus()
                true
            } else {
                false
            }
        }

}