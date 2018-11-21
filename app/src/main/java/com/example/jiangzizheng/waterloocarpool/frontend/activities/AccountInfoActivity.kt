package com.example.jiangzizheng.waterloocarpool.frontend.activities

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.example.jiangzizheng.waterloocarpool.R
import com.example.jiangzizheng.waterloocarpool.backend.api.Auth
import com.example.jiangzizheng.waterloocarpool.backend.api.Store
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.*
import java.io.IOException
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap

class AccountInfoActivity : AppCompatActivity() {

    // information related variables
    var currUser = FirebaseAuth.getInstance().currentUser
    var db = FirebaseFirestore.getInstance()
    val uid = currUser?.uid.toString()
    val docRef = db.collection("users").document(uid)
    val userEmail = currUser?.email

    // Icon related variables
    var btnUpload: Button? = null
    var btnChoose: Button? = null
    var imageView: ImageView? = null
    var filePath: Uri? = null
    val PICK_IMAGE_REQUEST: Int = 71
    val storage: FirebaseStorage = FirebaseStorage.getInstance()
    val storageReference: StorageReference = storage.getReference()
    var imageUID: String? = null


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

    fun readDataAndSetText() {
        docRef.get().addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val doc = task.getResult()
                val textFName = findViewById<TextView>(R.id.account_info_first_name)
                val textLName = findViewById<TextView>(R.id.account_info_last_name)
                val textEmail = findViewById<TextView>(R.id.account_info_email)

                textFName.setText(doc?.get("firstName").toString())
                textLName.setText(doc?.get("lastName").toString())
                textEmail.setText(userEmail)
            }
        }
    }

    private fun chooseImage() {
        val intent: Intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    private fun uploadImage() {
        if (filePath != null)
        {44
            val progressDialog: ProgressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()
            imageUID = UUID.randomUUID().toString()

            val ref: StorageReference = storageReference.child("image/$imageUID")
            ref.putFile(filePath as Uri)
                .addOnSuccessListener(object: OnSuccessListener<UploadTask.TaskSnapshot>{
                    override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                        progressDialog.dismiss()
                        Toast.makeText(baseContext, "Uploaded", Toast.LENGTH_SHORT).show()
                        saveUIDToFireStore(imageUID)
                    }
                })
                .addOnFailureListener(object: OnFailureListener{
                    override fun onFailure(e: Exception) {
                        progressDialog.dismiss()
                        Toast.makeText(baseContext, "Failed "+ e.message, Toast.LENGTH_SHORT).show()
                    }
                })

                .addOnProgressListener(object: OnProgressListener<UploadTask.TaskSnapshot>{
                    override fun onProgress(taskSnapshot: UploadTask.TaskSnapshot?) {
                        val progress: Double = (100.0* taskSnapshot!!.bytesTransferred /taskSnapshot
                            .totalByteCount)
                        progressDialog.setMessage("Uploaded ${progress.toInt()}%")
                    }
                })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
            && data != null && data.getData() != null
        ) {
            filePath = data.getData()
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath)
                imageView?.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun saveUIDToFireStore(UID: String?)
    {
        val user = Auth.instance.currentUser
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("users").document(user!!.uid.toString())

        val UIDMap: HashMap<String, String?> = hashMapOf("iconUID" to UID)
        docRef.set(UIDMap as Map<String, String?>, SetOptions.merge())
    }


}