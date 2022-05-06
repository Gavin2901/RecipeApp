package org.wit.recipeapp.models

import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object RecipeManager : RecipeStore {

    private val recipies = ArrayList<RecipeModel>()

    override fun findAll(): List<RecipeModel> {
        return recipies
    }

    override fun findById(id:Long) : RecipeModel? {
        val foundRecipe: RecipeModel? = recipies.find { it.id == id }
        return foundRecipe
    }

    override fun create(recipie: RecipeModel) {
        recipie.id = getId()
        recipies.add(recipie)
        logAll()
    }

    fun logAll() {
        Timber.v("** Recipie List **")
        recipies.forEach { Timber.v("Recipie add ${it}") }
    }
}