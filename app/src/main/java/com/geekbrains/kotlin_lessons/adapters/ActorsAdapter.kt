package com.geekbrains.kotlin_lessons.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.kotlin_lessons.Constants
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.models.Actor
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso

class ActorsAdapter : RecyclerView.Adapter<ActorsAdapter.ActorsViewHolder>() {

    private val actor = arrayListOf<Actor>()

    inner class ActorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val actorPhoto = itemView.findViewById<RoundedImageView>(R.id.actorPhoto)
        private val actorName = itemView.findViewById<TextView>(R.id.actorName)

        fun bindActor(actor: Actor) {
            Picasso.get().load("${Constants.imageURL}${actor.profile_path}")
                    .placeholder(R.drawable.ic_baseline_no_photography_56)
                    .into(actorPhoto)

            actorName.text = actor.name?.replace(" ", "\n")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ActorsViewHolder(
            itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.actor_item, parent, false)
    )

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) =
            holder.bindActor(actor = actor[position])

    override fun getItemCount() = actor.count()

    fun addItems(actors: List<Actor>) = actor.addAll(actors)
}