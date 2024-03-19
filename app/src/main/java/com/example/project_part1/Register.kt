package com.example.project_part1

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class Register : AppCompatActivity() {

    private lateinit var inputName: EditText
    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var inputNumber: EditText
    private lateinit var btnSignUp: Button
    private lateinit var btnUploadImage: Button
    private lateinit var logo2: ImageView
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        inputName = findViewById(R.id.input_name)
        inputEmail = findViewById(R.id.input_email)
        inputPassword = findViewById(R.id.input_password)
        inputNumber = findViewById(R.id.input_number)
        btnSignUp = findViewById(R.id.btnsignup)
        btnUploadImage = findViewById(R.id.btnUploadImage)
        logo2 = findViewById(R.id.logo2)

        btnSignUp.setOnClickListener {
            registerUser()
        }

        btnUploadImage.setOnClickListener {
            uploadImage()
        }
    }

    private fun registerUser() {
        val name = inputName.text.toString().trim()
        val email = inputEmail.text.toString().trim()
        val password = inputPassword.text.toString().trim()
        val number = inputNumber.text.toString().trim()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    val userData = Candidates(name, email, number)
                    Toast.makeText(this, "Registration Successfull", Toast.LENGTH_SHORT).show()
                    uploadImageToStorage(userId, userData)
                } else {
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun uploadImageToStorage(userId: String?, userData: Candidates) {
        selectedImageUri?.let { imageUri ->
            userId?.let { uid ->
                val storageReference = FirebaseStorage.getInstance().reference.child("images/${System.currentTimeMillis()}_${imageUri.lastPathSegment}")
                val uploadTask = storageReference.putFile(imageUri)

                uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    storageReference.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        val imageUrl = downloadUri.toString()
                        userData.image = imageUrl

                        saveUserDataToDatabase(userId, userData)
                    } else {
                        Toast.makeText(this, "Failed to upload image.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun saveUserDataToDatabase(userId: String?, userData: Candidates) {
        userId?.let {
            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("Candidates")

            // Store user data in the Realtime Database
            usersRef.child(it).setValue(userData)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, Login::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Failed to save user data.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun uploadImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
        }
    }

    companion object {
        private const val IMAGE_PICK_CODE = 1000
    }
}
