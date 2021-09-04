package com.geekbrains.kotlin_lessons.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.kotlin_lessons.R


class AlsoKnownAsAdapter : RecyclerView.Adapter<AlsoKnownAsAdapter.AlsoViewHolder>() {

    private val listAlso = arrayListOf<String>()

    inner class AlsoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(also: String) {
            itemView.findViewById<TextView>(R.id.alsoItem).text = also
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AlsoViewHolder(
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_also_known, parent, false)
    )

    override fun onBindViewHolder(holder: AlsoViewHolder, position: Int) =
        holder.bind(listAlso[position])

    override fun getItemCount() = listAlso.count()

    fun setAlso(newAlso: List<String>) = listAlso.addAll(newAlso)

    fun clearItems() = this.listAlso.clear()


}