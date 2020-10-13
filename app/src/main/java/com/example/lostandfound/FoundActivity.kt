package com.example.lostandfound

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_lost.*

class FoundActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var navigationView: NavigationView
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    var previousMenuItem: MenuItem?=null
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_found)
        database = Firebase.database.reference
        database.keepSynced(true)
        var colorList: MutableList<String> = arrayListOf("#81ecec","#74b9ff","#a29bfe","#00cec9","#0984e3","#6c5ce7")
        var lostItemsList: MutableList<Item> = arrayListOf()
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val temp = dataSnapshot.child("Found").children
                for (item in temp){
                    var desc = item.child("description").value.toString()
                    var itemName = item.child("itemname").value.toString()
                    var lostFoundDate = "Date Found: "+item.child("lostfoundDate").value.toString()
                    var postById = item.child("postedbyid").value.toString()
                    var person = "Found By: "+dataSnapshot.child("users").child(postById).child("name").value.toString()
                    var tstamp = "Posted at: "+item.child("tstamp").value.toString()
                    lostItemsList.add(Item(desc,lostFoundDate,person,itemName,tstamp,colorList[(0..5).random()]))
                }
//
//                Log.i("Last mein","Main List of items are: $lostItemsList")

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("LostActivity", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler_view.layoutManager = layoutManager

        val adapter = LostAdapter(this, lostItemsList)
        recycler_view.adapter = adapter
        database.addValueEventListener(postListener)
        //Once fetched then viewing the lost objects
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recycler_view)
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE

        //Navigation drawer
        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        navigationView = findViewById(R.id.navigationView)
        setUpToolbar()
        supportActionBar?.title="Found Items"
        navigationView.setCheckedItem(R.id.nav_item_three)
        val actionBarDrawerToggle=
            ActionBarDrawerToggle(this@FoundActivity,drawerLayout, R.string.open_drawer, R.string.close_drawer)
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
                    val intent = Intent(this@FoundActivity, MainActivity::class.java)
                    startActivity(intent)
                }

                R.id.nav_item_two->{
                    startActivity(Intent(this@FoundActivity, LostActivity::class.java))
                }
                R.id.nav_item_three->{
                    startActivity(Intent(this@FoundActivity, FoundActivity::class.java))
                }
                R.id.nav_item_four->{
                    val intent = Intent(this@FoundActivity, FormActivity::class.java)
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
                    val intent = Intent(this@FoundActivity, LoginActivity::class.java)
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


