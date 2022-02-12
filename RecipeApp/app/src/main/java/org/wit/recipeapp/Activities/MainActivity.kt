package org.wit.recipeapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
    private lateinit var updateBtn: Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: RecipeAdapter? = null
    private var rcp:RecipeModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)


        addBtn.setOnClickListener{addRecipe()}
        viewBtn.setOnClickListener{getRecipe()}
        updateBtn.setOnClickListener{updateRecipe()}

        //this brings up a popup of recipe name when recipe is clicked
        adapter?.setOnClickItem {
            Toast.makeText(this,it.rName,Toast.LENGTH_SHORT).show()

        editName.setText(it.rName)
        editDetails.setText(it.rDetails)
        rcp = it
        }
      adapter?.setOnClickDeleteItem{
            deleteRecipe(it.id)
     }
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

    private fun updateRecipe() {
        val rName = editName.text.toString()
        val rDetails = editDetails.text.toString()

        if (rName == rcp?.rName && rDetails == rcp?.rDetails) {
            Toast.makeText(this, "Recipe not changed", Toast.LENGTH_SHORT).show()
            return
        }
        if (rcp == null) return
            val rcp = RecipeModel(id = rcp!!.id, rName = rName, rDetails = rDetails)
            val status = sqLiteHelper.updateRecipe(rcp)
            if(status > -1){
                clearEditText()
                getRecipe()
            }else{
               Toast.makeText(this, "Could not update", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteRecipe(id:Int){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete this recipe?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){
            dialog,_->
            sqLiteHelper.deleteRecipeById(id)
            getRecipe()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){
                dialog,_->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
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
        updateBtn = findViewById(R.id.updatebtn)
        recyclerView = findViewById(R.id.recyclerView)
    }
}