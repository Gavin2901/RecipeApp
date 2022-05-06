package org.wit.recipeapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeModel(var id: Long = 0,
                         val name: String = "N/A",
                         val description: String = "N/A", ) : Parcelable