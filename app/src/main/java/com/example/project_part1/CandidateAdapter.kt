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

class CandidateAdapter(options: FirebaseRecyclerOptions<Candidates>) :
    FirebaseRecyclerAdapter<Candidates, CandidateAdapter.MyViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Candidates) {
        holder.bind(model)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)

            intent.putExtra("candidate_name", model.name)
            intent.putExtra("candidate_image", model.image)
            intent.putExtra("candidate_id", getRef(position).key)
            holder.itemView.context.startActivity(intent)
        }
    }

    inner class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.candidateitem, parent, false)) {

        private val txtName: TextView = itemView.findViewById(R.id.txtName)
        private val imageView: ImageView = itemView.findViewById(R.id.imgCandidate)

        fun bind(candidate: Candidates) {
            txtName.text = candidate.name
            val storageReference: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(candidate.image)
            Glide.with(itemView.context).load(storageReference).into(imageView)
        }
    }
}
