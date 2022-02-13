package org.wit.recipeapp
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context,DatabaseName,null, DatabaseVersion) {
    //TODO
   companion object{
       private const val DatabaseVersion=5
       private const val DatabaseName= "recipe.db"
       private const val TBL_Recipe = "tbl_recipe"
       private const val ID = "id"
       private const val Name = "rName"
       private const val Details = "rDetails"
   }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblRecipe = ("Create Table " + TBL_Recipe + "("
                + ID + " INTEGER PRIMARY KEY,"
                + Name + " TEXT,"
                + Details + " TEXT" + ")" )
        db?.execSQL(createTblRecipe)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("Drop Table If Exists $TBL_Recipe")
        onCreate(db)
    }

    fun insertRecipe(rcp: RecipeModel): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID,rcp.id)
        contentValues.put(Name, rcp.rName)
        contentValues.put(Details, rcp.rDetails)

        val successful = db.insert(TBL_Recipe, null, contentValues)
        db.close()
        return successful
    }

    fun getAllRecipes(): ArrayList<RecipeModel>{
        val rcpList: ArrayList<RecipeModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_Recipe"
        val db = this.readableDatabase

        val cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery, null)

        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()

        }
        var id: Int
        var rName: String
        var rDetails: String

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                rName = cursor.getString(cursor.getColumnIndexOrThrow("rName"))
                rDetails = cursor.getString(cursor.getColumnIndexOrThrow("rDetails"))
                val rcp =RecipeModel(id = id, rName = rName, rDetails = rDetails)
                rcpList.add(rcp)
            }while (cursor.moveToNext())
        }
        return rcpList
    }

    fun updateRecipe(rcp: RecipeModel): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, rcp.id)
        contentValues.put(Name, rcp.rName)
        contentValues.put(Details, rcp.rDetails)

        val successful = db.update(TBL_Recipe, contentValues, "id=" + rcp.id, null)
        db.close()
        return successful
    }

    fun deleteRecipeById(id:Int): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val successful = db.delete(TBL_Recipe, "id=$id", null)
        db.close()
        return successful
    }
}