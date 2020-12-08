package com.example.myproxywithactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myproxywithactivity.ModelsAdapter.MyViewHolder

internal class ModelsAdapter(private val ModelsList: List<Model>) :
    RecyclerView.Adapter<MyViewHolder>() {
    inner class MyViewHolder(view: View) : ViewHolder(view) {
        var title: TextView

        init {
            title = view.findViewById<View>(R.id.textView) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = ModelsList[position]
        holder.title.setText(model.title)
    }

    override fun getItemCount(): Int {
        return ModelsList.size
    }
}