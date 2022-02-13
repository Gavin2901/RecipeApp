package org.wit.recipeapp

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter :RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>(){
    private var rcpList: ArrayList<RecipeModel> = ArrayList()
    private var onClickItem:((RecipeModel)->Unit)?= null
    private var onClickDeleteItem:((RecipeModel)->Unit)?= null

    fun addItems(items:ArrayList<RecipeModel>){
        this.rcpList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (RecipeModel)->Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (RecipeModel) -> Unit ){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecipeViewHolder (
            LayoutInflater.from(parent.context).inflate(R.layout.card_items_rcp,parent,false)
            )


    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val rcp = rcpList[position]
        holder.bindView(rcp)
        holder.itemView.setOnClickListener { onClickItem?.invoke(rcp) }
        holder.deleteBtn.setOnClickListener{onClickDeleteItem?.invoke(rcp)}
    }

    override fun getItemCount(): Int {
        return rcpList.size
    }



    class RecipeViewHolder (var view: View): RecyclerView.ViewHolder(view){

        private var rName = view.findViewById<TextView>(R.id.rcpName)
        private var rDetails = view.findViewById<TextView>(R.id.rcpDetails)
        var deleteBtn = view.findViewById<Button>(R.id.deleteBtn)

        fun bindView(rcp:RecipeModel){

            rName.text = rcp.rName
            rDetails.text = rcp.rDetails
        }
    }
}