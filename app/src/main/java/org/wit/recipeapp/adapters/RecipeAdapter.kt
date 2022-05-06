package org.wit.recipeapp.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.recipeapp.R
import org.wit.recipeapp.databinding.CardRecipeBinding
import org.wit.recipeapp.models.RecipeModel


interface RecipeClickListener {
    fun onRecipeClick(recipe: RecipeModel)
}
class RecipeAdapter (
    private var recipies: List<RecipeModel>,
    private val listener: RecipeClickListener
)
    : RecyclerView.Adapter<RecipeAdapter.MainHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val recipe = recipies[holder.adapterPosition]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int = recipies.size

    inner class MainHolder( val binding : CardRecipeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(recipe: RecipeModel) {
            binding.root.tag = recipe
            binding.recipe = recipe
            binding.executePendingBindings()
            binding.root.setOnClickListener { listener.onRecipeClick(recipe) }
        }
    }
}