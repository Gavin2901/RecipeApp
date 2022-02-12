package org.wit.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter :RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>(){
    private var rcpList: ArrayList<RecipeModel> = ArrayList()

    fun addItems(items:ArrayList<RecipeModel>){
        this.rcpList = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecipeViewHolder (
            LayoutInflater.from(parent.context).inflate(R.layout.card_items_rcp,parent,false)
            )


    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val rcp = rcpList[position]
        holder.bindView(rcp)
    }

    override fun getItemCount(): Int {
        return rcpList.size
    }



    class RecipeViewHolder (var view: View): RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.rcpId)
        private var rName = view.findViewById<TextView>(R.id.rcpName)
        private var rDetails = view.findViewById<TextView>(R.id.rcpDetails)
        private var deleteBtn = view.findViewById<Button>(R.id.deleteBtn)

        fun bindView(rcp:RecipeModel){
            id.text = rcp.id.toString()
            rName.text = rcp.rName
            rDetails.text = rcp.rDetails
        }
    }
}