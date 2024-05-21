package com.example.capstoneproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.connections.RetrofitClient
import com.example.capstoneproject.connections.UserCrud
import com.example.capstoneproject.connections.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Retrofit


class MainActivity : AppCompatActivity() {
    private lateinit var retrofit: Retrofit
    private val mainScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private lateinit var user: UserCrud

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        retrofit = RetrofitClient.create()
        user = retrofit.create(UserCrud::class.java)

        val uname = findViewById<EditText>(R.id.editTextText)
        val pwd = findViewById<EditText>(R.id.editTextText2)
        val email = findViewById<EditText>(R.id.editTextText3)

        val btn = findViewById<Button>(R.id.button)

        btn.setOnClickListener {
           // createApiCall(uname.text.toString(), pwd.text.toString(), email.text.toString())
            validateInputs(uname.text.toString(), pwd.text.toString(), email.text.toString())

        }
        val loginbtn = findViewById<Button>(R.id.button2)
        loginbtn.setOnClickListener(View.OnClickListener
        {
            openLoginLayout()
        })
    }

    private fun openLoginLayout() {
        startActivity(Intent(this, LoginActivity::class.java))
    }


    private fun createApiCall(uname: String, pwd: String, email: String) {
        mainScope.launch {

            val app = Users(
                userName = uname,
                email = email,
                password = pwd
            )

            val response = user.createUser(app)
            if (response.isSuccessful) {
                val statusCode = response.body() ?: -1
                Log.i("@Responsecode", "res:$statusCode")
                val message = "Registration successful. Welcome,${uname}!"
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(
                    this@MainActivity,
                    "An unknown conflict occurred.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
    private fun validateInputs(username: String, password: String, email: String) {
        // Check for blank username
        if (username.isBlank()) {
            showAlertDialog("Error", "Username cannot be blank")
            return
        }

//         Check for username already exists (You need to implement this logic)
//        if (isUsernameExists(username)) {
//            showAlertDialog("Error", "Username already exists")
//            return
//        }

        // Check for blank password
        if (password.isBlank()) {
            showAlertDialog("Error", "Password cannot be blank")
            return
        }

        // Check for blank email
        if (email.isBlank()) {
            showAlertDialog("Error", "Email cannot be blank")
            return
        }
        createApiCall(username, password, email)

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
    }







