package com.example.jiangzizheng.waterloocarpool.frontend.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView

import java.util.ArrayList
import android.Manifest.permission.READ_CONTACTS
import android.content.Intent
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import com.example.jiangzizheng.waterloocarpool.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

import com.example.jiangzizheng.waterloocarpool.backend.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

import kotlinx.android.synthetic.main.activity_login.*

import com.example.jiangzizheng.waterloocarpool.global.Constants.RC_GOOGLE_SIGN_IN


/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onStart() {
        super.onStart()

        val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        if (status != ConnectionResult.SUCCESS) {
            login_lodding.visibility = View.GONE
            login_invalid_message.visibility = View.VISIBLE
        } else {
            Auth.retrieveOAuthToken(this, Auth.OAuthType.Google)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_GOOGLE_SIGN_IN) {
            try {
                val account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                Auth.instance.signInWithCredential(credential)
                    .addOnSuccessListener {
                        finish()
                    }
                    .addOnFailureListener {
                        report(it)
                    }
            } catch (e: ApiException) {
                when (e.statusCode) {
                    12501, 12502 -> { /* Ignore */ }
                    else -> report(e)
                }
            }
        }
    }

    private fun report(e: Exception) {
        Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
        Log.w(javaClass.simpleName, e)
    }

}
