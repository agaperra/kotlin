package com.geekbrains.kotlin_lessons.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.models.Actor
import com.geekbrains.kotlin_lessons.utils.Constants
import com.squareup.picasso.Picasso

class SearchActorsAdapter :
        RecyclerView.Adapter<SearchActorsAdapter.ActorSearchViewHolder>() {

    private val actor = arrayListOf<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ActorSearchViewHolder(
            itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.actor_item, parent, false)
    )


    fun addItems(actors: ArrayList<Actor>) = this.actor.addAll(actors)

    fun clearItems() = this.actor.clear()

    override fun onBindViewHolder(holder: ActorSearchViewHolder, position: Int) =
            holder.bindMovie(actor[position])

    override fun getItemCount(): Int = actor.size

    inner class ActorSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var poster: ImageView
        private val actorName = itemView.findViewById<TextView>(R.id.actorName)

        fun bindMovie(actors: Actor) {
            itemView.apply {
                poster = findViewById(R.id.actorPhoto)

                Picasso.get().load("${Constants.imageURL}${actors.profile_path}")
                        .placeholder(R.drawable.ic_baseline_no_photography_48)
                        .into(poster)

                actorName.text=actors.name?.replace(" ", "\n")

            }


        }

    }

}