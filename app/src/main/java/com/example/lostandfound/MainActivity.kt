package com.example.lostandfound

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var linearLayout: LinearLayout
    lateinit var toolbar: Toolbar
    lateinit var navigationView: NavigationView
    var previousMenuItem:MenuItem?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        linearLayout = findViewById(R.id.linearLayout)
        toolbar = findViewById(R.id.toolbar)
        navigationView = findViewById(R.id.navigationView)
        setUpToolbar()
        supportActionBar?.title="Main Activity"
        navigationView.setCheckedItem(R.id.nav_item_one)
        val actionBarDrawerToggle=ActionBarDrawerToggle(this@MainActivity,drawerLayout, R.string.open_drawer, R.string.close_drawer)
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
                    supportActionBar?.title="My Profile"
                    navigationView.setCheckedItem(R.id.nav_item_one)
                    drawerLayout.closeDrawers()
                }

                R.id.nav_item_two->{

                    supportActionBar?.title="Inbox"
                    drawerLayout.closeDrawers()
                }
                R.id.nav_item_three->{
                    supportActionBar?.title="Find People"
                    drawerLayout.closeDrawers()
                }
                R.id.nav_item_four->{

                    supportActionBar?.title="Contacts"
                    drawerLayout.closeDrawers()
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
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
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