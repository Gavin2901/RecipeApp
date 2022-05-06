package org.wit.recipeapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.recipeapp.models.RecipeManager
import org.wit.recipeapp.models.RecipeModel

class RecipeDetailsViewModel : ViewModel() {
    private val recipe = MutableLiveData<RecipeModel>()

    val observableRecipe: LiveData<RecipeModel>
        get() = recipe


    fun getRecipe(id: Long) {
        recipe.value = RecipeManager.findById(id)
    }
}