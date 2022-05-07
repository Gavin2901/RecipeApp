package org.wit.recipeapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.firebase.auth.FirebaseUser
import org.wit.recipeapp.databinding.ActivityMainBinding
import org.wit.recipeapp.databinding.NavHeaderMainBinding
import org.wit.recipeapp.ui.auth.LoggedInViewModel
import org.wit.recipeapp.ui.auth.Login
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navHeaderMainBinding : NavHeaderMainBinding
    private lateinit var loggedInViewModel : LoggedInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        drawerLayout = binding.drawerLayout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(binding.appBarMain.toolbar)

        val navHostFragment = supportFragmentManager.
        findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        /*val navController = findNavController(R.id.nav_host_fragment_content_main)*/
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_list, R.id.nav_recipe, R.id.nav_about
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    public override fun onStart() {
        super.onStart()
        loggedInViewModel = ViewModelProvider(this).get(LoggedInViewModel::class.java)
        loggedInViewModel.liveFirebaseUser.observe(this, Observer { firebaseUser ->
     /*       if (firebaseUser != null) {
                //val currentUser = loggedInViewModel.liveFirebaseUser.value
                *//*if (currentUser != null)*//* updateNavHeader(loggedInViewModel.liveFirebaseUser.value!!)
            }*/
        })
    }

    private fun updateNavHeader(currentUser: FirebaseUser) {
        var headerView = binding.navView.getHeaderView(0)
        navHeaderMainBinding = NavHeaderMainBinding.bind(headerView)
        navHeaderMainBinding.navHeaderEmail.text = currentUser.email
    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun signOut(item: MenuItem) {
        loggedInViewModel.logOut()
        val intent = Intent(this, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


}