package com.example.lostandfound

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        // Initialize Firebase Auth
        auth = Firebase.auth
        database = Firebase.database.reference
        joinus.setOnClickListener {
            val name:String = signup_name.text.toString()
            val email:String = signup_email.text.toString()
            val password:String = signup_password.text.toString()
            if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                Toast.makeText(this@SignupActivity, "Fields cannot be left blank", Toast.LENGTH_SHORT).show()
            }
            else {
                createUser(name, email, password)
            }
        }
        gotologin.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun createUser(name: String, email:String, password:String){
        Log.d("SignupActivity", "Name is $name")
        Log.d("SignupActivity", "Email is $email")
        Log.d("SignupActivity", "Password is $password")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SUCCESS", "createUserWithEmail:success")
                    val intent = Intent(this@SignupActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    val user = auth.currentUser
                    updateDatabase(user, name)
                    Toast.makeText(baseContext, "Authentication Successful", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("FAILURE", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication Failed",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateDatabase(user:FirebaseUser?, name: String) {
        if(user == null){
            Toast.makeText(baseContext, "Authentication Failed", Toast.LENGTH_SHORT).show()
            return
        }
        val uid = user.uid
        Log.d("SignupActivity", "Uid is $uid")
        database.child("users").child(uid).child("name").setValue(name)
    }
}
