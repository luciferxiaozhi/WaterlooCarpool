package com.example.jiangzizheng.waterloocarpool.frontend.activities

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.jiangzizheng.waterloocarpool.R
import com.example.jiangzizheng.waterloocarpool.backend.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap

class AccountInfoActivity : AppCompatActivity() {

    // information related variables
    private var currUser = FirebaseAuth.getInstance().currentUser
    var db = FirebaseFirestore.getInstance()

    private val uid = currUser?.uid.toString()
    private val docRef = db.collection("users").document(uid)
    private val userEmail = currUser?.email

    // Icon related variables
    var btnUpload: Button? = null
    var btnChoose: Button? = null
    var imageView: ImageView? = null
    var filePath: Uri? = null

    private val pickImgReq: Int = 71
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val storageReference: StorageReference = storage.reference
    private var imageUID: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)

        btnChoose = findViewById(R.id.account_info_layout_button_btnChoose)
        btnUpload = findViewById(R.id.account_info_layout_button_btnUpload)
        imageView = findViewById(R.id.account_info_imgView)

        // icon related
        btnChoose?.setOnClickListener {
            chooseImage()
        }

        btnUpload?.setOnClickListener {
            uploadImage()
        }

        // information related
        readDataAndSetText()
    }

    private fun readDataAndSetText() {
        docRef.get().addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val doc = task.result
                val textFName = findViewById<TextView>(R.id.account_info_first_name)
                val textLName = findViewById<TextView>(R.id.account_info_last_name)
                val textEmail = findViewById<TextView>(R.id.account_info_email)

                textFName.text = doc?.get("firstName").toString()
                textLName.text = doc?.get("lastName").toString()
                textEmail.text = userEmail
            }
        }
    }

    private fun chooseImage() {
        Intent().type = "image/*"
        Intent().action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(Intent(), "Select Picture"), pickImgReq)
    }

    private fun uploadImage() {
        if (filePath != null) {
            val progressDialog: ProgressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()
            imageUID = UUID.randomUUID().toString()

            val ref: StorageReference = storageReference.child("image/$imageUID")
            ref.putFile(filePath as Uri)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(baseContext, "Uploaded", Toast.LENGTH_SHORT).show()
                    saveUIDToFireStore(imageUID)
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(baseContext, "Failed " + e.message, Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress: Double = (100.0 * taskSnapshot!!.bytesTransferred / taskSnapshot
                        .totalByteCount)
                    progressDialog.setMessage("Uploaded ${progress.toInt()}%")
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImgReq && resultCode == RESULT_OK
            && data != null && data.data != null
        ) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imageView?.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun saveUIDToFireStore(UID: String?) {
        val user = Auth.instance.currentUser
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("users").document(user!!.uid)

        val uidMap: HashMap<String, String?> = hashMapOf("iconUID" to UID)
        docRef.set(uidMap as Map<String, String?>, SetOptions.merge())
    }


}