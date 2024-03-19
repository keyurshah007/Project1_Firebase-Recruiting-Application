package com.example.project_part1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    private var adapter: MainAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val currentUserId = currentUser?.uid
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val query = FirebaseDatabase.getInstance().reference.child("Candidates").child(currentUserId?:"").child("friendsPosts")
        Log.d("query1", "Query: ${query.toString()}")
        val options = FirebaseRecyclerOptions.Builder<Posts>().setQuery(query, Posts::class.java).build()

        adapter = MainAdapter(options)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewMain)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val buttonConnect = findViewById<Button>(R.id.buttonConnect)
        buttonConnect.setOnClickListener {

            startActivity(Intent(this@MainActivity, CandidateActivity::class.java))
        }
    }
    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

//    override fun onStop() {
//        super.onStop()
//        adapter?.stopListening()
//    }
}