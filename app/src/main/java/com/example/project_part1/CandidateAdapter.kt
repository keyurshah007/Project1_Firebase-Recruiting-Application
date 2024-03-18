package com.example.project_part1


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

//class CandidateAdapter(options: FirebaseRecyclerOptions<Candidate>) :
//    FirebaseRecyclerAdapter<Candidate, CandidateAdapter.ViewHolder>(options) {
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val name: TextView = itemView.findViewById(R.id.txtName)
//        val image: ImageView = itemView.findViewById(R.id.imageViewImage)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.candidateitem, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Candidate) {
//        holder.name.text = model.name
//        Glide.with(holder.image.context).load(model.image).into(holder.image)
//    }


//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.firebase.ui.database.FirebaseRecyclerAdapter
//import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CandidateAdapter(options: FirebaseRecyclerOptions<Candidate>) :
    FirebaseRecyclerAdapter<Candidate, CandidateAdapter.MyViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Candidate) {
        val storRef: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(model.image)
        Glide.with(holder.img.context).load(storRef).into(holder.img)
        holder.txtName.text = model.name
        
    }

    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.candidateitem, parent, false)) {
        val txtName: TextView = itemView.findViewById(R.id.txtName)

        val img: ImageView = itemView.findViewById(R.id.imgCandidate)
    }
}

