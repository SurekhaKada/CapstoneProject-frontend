package com.example.capstoneproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.connections.RetrofitClient
import com.example.capstoneproject.connections.UserCrud
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class LoginActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private val mainScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        retrofit = RetrofitClient.create()
        val login=retrofit.create(UserCrud::class.java)
        val userName=findViewById<EditText>(R.id.unameText)
        val password = findViewById<EditText>(R.id.pwdtext)
        val logBtn = findViewById<Button>(R.id.lgbtn)
//        val samplebtn=findViewById<Button>(R.id.sample)
//
//        samplebtn.setOnClickListener {
//            startActivity(Intent(this, ListOfInvoices::class.java))
//        }

        logBtn.setOnClickListener {

            val userName = userName.text.toString()
            val password = password.text.toString()

            validateInputs(userName, password)


        }
    }

    private fun validateInputs(userName: String, password: String) {
        if (userName.isBlank()) {
            showAlertDialog("Error", "Username cannot be blank")
            return
        }
        if (password.isBlank()) {
            showAlertDialog("Error", "Password cannot be blank")
            return
        }

        loginCheck(userName, password)

    }

    private  fun loginCheck(userName: String, password: String) {

        val login=retrofit.create(UserCrud::class.java)
        mainScope.launch {
            try {
                val response = login.getUser(userName,password)
                if (response.isSuccessful) {
                    val userResponse = response.body()
                     Log.i("@login", "u Id : $userResponse ")
                    if (userResponse!= null) {
                        val sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE)
                        val editor = sharedPref.edit()
                        val userId = response.body() ?: -1 // Handle potential null response
                        editor.putInt("user_id", userId)
                        editor.apply()
                        showToast("Login successful!! UserID :$userResponse")
                        val intent = Intent(this@LoginActivity, EditInvoiceActivity::class.java)
                        startActivity(intent)

                    }
                    else {

                        showToast("Login failed (empty response)")

                    }

                } else {
                    // Handle login failure
                    val error = response.errorBody()?.string() ?: "Unknown error"
                    showToast("login failed: $error")
                }

            } catch (e: Exception) {
                showToast("Please check your connection and try again!!.")

            Log.e("LoginActivity", "Network error during login:", e)
        }

    }
    }

    private fun openNewActivity() {
        startActivity(Intent(this, ListOfInvoices::class.java))
    }

    private fun showAlertDialog(title: String, message: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}

