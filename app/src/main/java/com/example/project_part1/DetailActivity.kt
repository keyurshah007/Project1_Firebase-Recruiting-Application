package com.example.project_part1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DetailActivity : AppCompatActivity() {


    private lateinit var candidateId: String
    private lateinit var emailTextView: TextView


    private lateinit var phoneNumberTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val candidateName = intent.getStringExtra("candidate_name")
        val candidateImage = intent.getStringExtra("candidate_image")
        candidateId = intent.getStringExtra("candidate_id") ?: ""

        val textViewName: TextView = findViewById(R.id.textViewName)
        val detailImage: ImageView = findViewById(R.id.detailImage)
        emailTextView = findViewById(R.id.textViewEmail)
        phoneNumberTextView = findViewById(R.id.textViewNo)

        textViewName.text = candidateName
        Glide.with(this).load(candidateImage).into(detailImage)

        fetchCandidateDetails()

        val btnDetail: Button = findViewById(R.id.btnDetail)
        btnDetail.setOnClickListener {
            addFriend()
        }

        val backCandidateImageView: ImageView = findViewById(R.id.backDetail)
        backCandidateImageView.setOnClickListener {
            val intent = Intent(this, CandidateActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun fetchCandidateDetails() {
        val database = FirebaseDatabase.getInstance()
        val candidatesRef = database.getReference("Candidates").child(candidateId)
        candidatesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val candidate = dataSnapshot.getValue(Candidates::class.java)
                candidate?.let {
                    emailTextView.text = candidate.email
                    phoneNumberTextView.text = candidate.phoneNumber
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    private fun addFriend() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val currentUserId = currentUser?.uid

        if (currentUserId != null) {
            val database = FirebaseDatabase.getInstance()
            val currentUserFriendsRef = database.getReference("Candidates").child(currentUserId).child("friendsPosts")

            val friendIdToAdd = candidateId
            val candidateRef = database.getReference("Candidates").child(friendIdToAdd)
            candidateRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val candidate = dataSnapshot.getValue(Candidates::class.java)
                    candidate?.let {
                        // Get the "posts" of the friend
                        val friendPostsRef = candidateRef.child("posts")
                        friendPostsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(postsSnapshot: DataSnapshot) {
                                // Fetch existing posts of friends
                                currentUserFriendsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(existingPostsSnapshot: DataSnapshot) {
                                        val existingPosts = existingPostsSnapshot.value as? Map<String, Any>
                                        val newPosts = postsSnapshot.value as? Map<String, Any>

                                        if (existingPosts != null && newPosts != null) {
                                            val mergedPosts = existingPosts.toMutableMap()
                                            mergedPosts.putAll(newPosts)
                                            currentUserFriendsRef.setValue(mergedPosts)
                                                .addOnSuccessListener {
                                                    Toast.makeText(this@DetailActivity, "Friend Added", Toast.LENGTH_SHORT).show()
                                                }
                                                .addOnFailureListener { e ->
                                                    Toast.makeText(this@DetailActivity, "Failed to add friend: ${e.message}", Toast.LENGTH_SHORT).show()
                                                }
                                        } else if (newPosts != null) {
                                            currentUserFriendsRef.setValue(newPosts)
                                                .addOnSuccessListener {
                                                    Toast.makeText(this@DetailActivity, "Friend Added", Toast.LENGTH_SHORT).show()
                                                }
                                                .addOnFailureListener { e ->
                                                    Toast.makeText(this@DetailActivity, "Failed to add friend: ${e.message}", Toast.LENGTH_SHORT).show()
                                                }
                                        }
                                    }

                                    override fun onCancelled(databaseError: DatabaseError) {
                                        Toast.makeText(this@DetailActivity, "Failed to fetch existing posts: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                                    }
                                })

                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                Toast.makeText(this@DetailActivity, "Failed to fetch friend's posts: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@DetailActivity, "Failed to fetch user details: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }




}
