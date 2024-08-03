package com.example.chatappkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class SignupFragment : Fragment() {

    private lateinit var usernameEditText :EditText
    private lateinit var passwordEditText :EditText
    private lateinit var displayNameEditText: EditText
    private lateinit var btn : Button



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

        usernameEditText = view.findViewById(R.id.username)
        passwordEditText = view.findViewById(R.id.password)
        displayNameEditText = view.findViewById(R.id.displayName)
        btn = view.findViewById(R.id.submit_btn)

        btn.setOnClickListener {
            val username = usernameEditText.text.toString()
            val displayName = displayNameEditText.text.toString()
            val password = passwordEditText.text.toString()


        }


        return view
    }

}