package com.example.project_part1

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainAdapter(options: FirebaseRecyclerOptions<Posts>) :
    FirebaseRecyclerAdapter<Posts, MainAdapter.MyViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Posts) {
        holder.bind(model)
//        holder.itemView.setOnClickListener {
//            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
//
//            intent.putExtra("candidate_name", model.name)
//            intent.putExtra("candidate_image", model.image)
//            intent.putExtra("candidate_id", getRef(position).key)
//            holder.itemView.context.startActivity(intent)
//        }
    }

    inner class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.posts_layout, parent, false)) {

        private val txtPosts: TextView = itemView.findViewById(R.id.posts_display)


        fun bind(candidate: Posts) {
            txtPosts.text = candidate.post
        }
    }
}
