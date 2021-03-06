package com.example.lostandfound

import android.content.Intent
import android.graphics.Color

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi

import androidx.appcompat.widget.AppCompatRadioButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_form.*
import java.time.LocalDateTime

class FormActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var navigationView: NavigationView
    var previousMenuItem: MenuItem?=null
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        auth = Firebase.auth
        database = Firebase.database.reference

        postformbutton.setOnClickListener{
            var tstamp= LocalDateTime.now().toString()
            val itemname:String = item_name.text.toString()
            val desecription:String = description.text.toString()
            val lostfoundDate:String = dateid.text.toString()
            val id = radiogroup.checkedRadioButtonId
            val radioButton=findViewById<RadioButton>(id)
            val posttype:String= radioButton.text.toString()
            val user= auth.currentUser
            if(user == null){
                Toast.makeText(baseContext, "Authentication Failed", Toast.LENGTH_SHORT).show()
            }
            else{
                val currentuser=user.uid
                if(posttype.isEmpty()||desecription.isEmpty()||lostfoundDate.isEmpty()){
                    Toast.makeText(this@FormActivity, "Fields cannot be left blank", Toast.LENGTH_SHORT).show()
                }
                else{
                    val form = formdataClass(currentuser,itemname,lostfoundDate,desecription,tstamp)
                    val key=database.child(posttype).push().key.toString()
                    database.child(posttype).child(key).setValue(form)
                    database.child("users").child(currentuser).child(posttype).push().setValue(key)
                    description.text.clear()
                    dateid.text.clear()
                    item_name.text.clear()
                    Toast.makeText(this@FormActivity, "Request Posted", Toast.LENGTH_SHORT).show()
                }
            }
        }
        //RADIO BTN UI
        radioButton2.setOnClickListener(View.OnClickListener {
            radioButton2.setTextColor(Color.WHITE)
            lostbutton.setTextColor(Color.parseColor("#2980b9"))
        })

        lostbutton.setOnClickListener(View.OnClickListener {
            lostbutton.setTextColor(Color.WHITE)
            radioButton2.setTextColor(Color.parseColor("#2980b9"))
        })

        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        navigationView = findViewById(R.id.navigationView)
        setUpToolbar()
        supportActionBar?.title="Lost/Found Request"
        navigationView.setCheckedItem(R.id.nav_item_four)
        val actionBarDrawerToggle=
            ActionBarDrawerToggle(this@FormActivity,drawerLayout, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            if(previousMenuItem != null){
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it
            when(it.itemId)
            {
                R.id.nav_item_one->{
                    val intent = Intent(this@FormActivity, MainActivity::class.java)
                    startActivity(intent)
                }

                R.id.nav_item_two->{
                    startActivity(Intent(this@FormActivity, LostActivity::class.java))
                }
                R.id.nav_item_three->{
                    startActivity(Intent(this@FormActivity, FoundActivity::class.java))
                }
                R.id.nav_item_four->{
                    val intent = Intent(this@FormActivity, FormActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_item_five->{

                    supportActionBar?.title="News and Updates"
                    drawerLayout.closeDrawers()
                }
                R.id.nav_item_six->{

                    supportActionBar?.title="Credits"
                    drawerLayout.closeDrawers()
                }
                R.id.nav_item_seven->{

                    supportActionBar?.title="News and Updates"
                    drawerLayout.closeDrawers()
                }
                R.id.nav_item_eight->{

                    supportActionBar?.title="IIT Bhilai"
                    drawerLayout.closeDrawers()
                }
                R.id.nav_item_nine->{
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this@FormActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id==android.R.id.home)
        {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawers()
    }
}