package org.wit.recipeapp.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import org.wit.recipeapp.R
import org.wit.recipeapp.databinding.RecipeDetailsFragmentBinding
import timber.log.Timber

class RecipeDetails : Fragment() {

    companion object {
        fun newInstance() = RecipeDetails()
    }

    private val args by navArgs<RecipeDetailsArgs>()

    private lateinit var recipeDetailViewModel: RecipeDetailsViewModel

    private lateinit var viewModel: RecipeDetailsViewModel

    private var _fragBinding: RecipeDetailsFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = RecipeDetailsFragmentBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        recipeDetailViewModel = ViewModelProvider(this)[RecipeDetailsViewModel::class.java]
        recipeDetailViewModel.observableRecipe.observe(viewLifecycleOwner, Observer { render() })

        //val view = inflater.inflate(R.layout.rating_detail_fragment, container, false)
        return root
    }

    private fun render() {
        fragBinding.recipevm = recipeDetailViewModel
        Timber.i("Retrofit fragBinding.recipevm == $fragBinding.recipevm")
    }

    override fun onResume() {
        super.onResume()
        recipeDetailViewModel.getRecipe(args.recipeid)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecipeDetailsViewModel::class.java)

    }
}