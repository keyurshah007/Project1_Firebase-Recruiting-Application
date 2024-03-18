package com.example.project_part1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.firebase.ui.database.FirebaseRecyclerOptions

class CandidateActivity : AppCompatActivity() {

    private var adapter: CandidateAdapter? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_candidate)
//
//        val query = FirebaseDatabase.getInstance().reference.child("Candidates")
//        val options = FirebaseRecyclerOptions.Builder<Candidate>().setQuery(query, Candidate::class.java).build()
//
//        adapter = CandidateAdapter(options)
//        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewCandidate)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapter
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate)

        val query = FirebaseDatabase.getInstance().reference.child("Candidates")
        val options = FirebaseRecyclerOptions.Builder<Candidate>().setQuery(query, Candidate::class.java).build()

        adapter = CandidateAdapter(options)
        val rView: RecyclerView = findViewById(R.id.recyclerViewCandidate)
        rView.layoutManager = LinearLayoutManager(this)
        rView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }
}
