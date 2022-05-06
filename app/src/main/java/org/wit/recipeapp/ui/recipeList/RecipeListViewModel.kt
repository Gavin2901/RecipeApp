package org.wit.recipeapp.ui.recipeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.recipeapp.models.RecipeManager
import org.wit.recipeapp.models.RecipeModel

class RecipeListViewModel : ViewModel() {

    private val recipeList = MutableLiveData<List<RecipeModel>>()

    val observableRecipeList: LiveData<List<RecipeModel>>
        get() = recipeList

    init {
        load()
    }

    fun load() {
        recipeList.value = RecipeManager.findAll()
    }
}