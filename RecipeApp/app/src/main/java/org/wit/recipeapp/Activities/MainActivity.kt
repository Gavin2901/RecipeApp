package org.wit.recipeapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.recipeapp.R
import org.wit.recipeapp.RecipeAdapter
import org.wit.recipeapp.RecipeModel
import org.wit.recipeapp.SQLiteHelper

class MainActivity : AppCompatActivity() {
    private lateinit var editName : EditText
    private lateinit var editDetails : EditText
    private lateinit var addBtn: Button
    private lateinit var viewBtn: Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: RecipeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        addBtn.setOnClickListener{addRecipe()}
        viewBtn.setOnClickListener{getRecipe()}
    }


    private fun addRecipe(){
        val rName = editName.text.toString()
        val rDetails = editDetails.text.toString()

        if(rName.isEmpty() || rDetails.isEmpty()){
            Toast.makeText(this,"Please Enter Content Here", Toast.LENGTH_SHORT).show()
        }else{
            val rcp = RecipeModel(rName = rName, rDetails = rDetails)
            val status = sqLiteHelper.insertRecipe(rcp)
            //checks if insert is successful or not
            if(status > -1) {
                Toast.makeText(this, "Recipe Added", Toast.LENGTH_SHORT).show()
                clearEditText()
            }else{
                Toast.makeText(this,"Recipe Was Not Saved",Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun getRecipe(){
        val rcpList = sqLiteHelper.getAllRecipes()
        Log.e("Get Recipe", "${rcpList.size}")
        adapter?.addItems(rcpList)
    }
    private fun clearEditText(){
        editName.setText("")
        editDetails.setText("")
        editName.requestFocus()
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecipeAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView(){
        editName = findViewById(R.id.editName)
        editDetails = findViewById(R.id.editDetails)
        addBtn = findViewById(R.id.addbtn)
        viewBtn = findViewById(R.id.viewbtn)
        recyclerView = findViewById(R.id.recyclerView)
    }
}