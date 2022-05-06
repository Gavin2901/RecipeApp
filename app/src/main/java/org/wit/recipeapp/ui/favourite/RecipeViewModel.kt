package org.wit.recipeapp.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.recipeapp.models.RecipeManager
import org.wit.recipeapp.models.RecipeModel

class RecipeViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addRecipe(recipe: RecipeModel) {
        status.value = try {
            RecipeManager.create(recipe)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}