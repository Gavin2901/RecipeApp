package org.wit.recipeapp.ui.recipeList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.recipeapp.MainActivity
import org.wit.recipeapp.R
import org.wit.recipeapp.adapters.RecipeAdapter
import org.wit.recipeapp.adapters.RecipeClickListener
import org.wit.recipeapp.databinding.FragmentListBinding

import org.wit.recipeapp.models.RecipeModel


class RecipeListFragment : Fragment(), RecipeClickListener {

    var recipe = RecipeModel()
    lateinit var app: MainActivity
    private var _fragBinding: FragmentListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var recipeListViewModel: RecipeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.app_name)

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        recipeListViewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)
        recipeListViewModel.observableRecipeList.observe(viewLifecycleOwner, Observer { recipes ->
            recipes?.let { render(recipes) }
        })

        return root
    }


//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return NavigationUI.onNavDestinationSelected(item,
//            requireView().findNavController()) || super.onOptionsItemSelected(item)
//    }

    private fun render(recipeList: List<RecipeModel>) {
        fragBinding.recyclerView.adapter = RecipeAdapter(recipeList,this)
        if (recipeList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onRecipeClick(recipe: RecipeModel) {
        val action = RecipeListFragmentDirections.actionNavListToRecipeDetails(recipe.id)
        findNavController().navigate(action)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RecipeListFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    override fun onResume() {
        super.onResume()
        recipeListViewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

}
