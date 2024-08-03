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


class LoginFragment : Fragment() {

    private lateinit var usernameEditText : EditText
    private lateinit var passwordEditText : EditText
    private lateinit var btn : Button

    private lateinit var userPreferences: UserPreferences

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

        usernameEditText = view.findViewById(R.id.username)
        passwordEditText = view.findViewById(R.id.password)
        btn = view.findViewById(R.id.submit_btn)
        userPreferences = UserPreferences(requireContext())

        usernameEditText.imeOptions = EditorInfo.IME_ACTION_NEXT
        passwordEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        shiftFocusFrom(usernameEditText,passwordEditText)

        btn.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username == "admin" && password == "admin") {
                userPreferences.saveUserData("admin","admin")
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
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