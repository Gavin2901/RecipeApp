package org.wit.recipeapp.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.wit.recipeapp.R
import org.wit.recipeapp.databinding.FragmentRecipeBinding
import org.wit.recipeapp.models.RecipeModel

class RecipeFragment : Fragment() {

    private var _fragBinding: FragmentRecipeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val fragBinding get() = _fragBinding!!
    private lateinit var recipeViewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _fragBinding = FragmentRecipeBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_addrecipe)

        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        recipeViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })



        setButtonListener(fragBinding)
        return root;
    }

    fun setButtonListener(layout: FragmentRecipeBinding) {
        layout.addButton.setOnClickListener {
            val recipeName = layout.nameText.text.toString()
            val recipeDescription = layout.descriptionText.text.toString()
            recipeViewModel.addRecipe(RecipeModel(name = recipeName, description = recipeDescription))
            Toast.makeText(context, recipeName,Toast.LENGTH_LONG ).show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    //findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.action_recipeerror), Toast.LENGTH_LONG).show()
        }
    }
}