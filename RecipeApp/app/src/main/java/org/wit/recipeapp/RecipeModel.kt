package org.wit.recipeapp

import java.util.*

data class RecipeModel (
    var id: Int = getAutoId(),
    var rName: String = "",
    var rDetails: String = ""
    ){
        companion object{
            fun getAutoId():Int{
                val random = Random()
                return random.nextInt(100)
            }

        }

    }
