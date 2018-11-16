package com.example.jiangzizheng.waterloocarpool.frontend.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.jiangzizheng.waterloocarpool.R
import com.example.jiangzizheng.waterloocarpool.backend.api.Auth
import com.example.jiangzizheng.waterloocarpool.global.Constants.RC_GOOGLE_SIGN_IN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_google_login.*


/**
 * A login screen that offers login via email/password.
 */
class GoogleLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_login)
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
                        startActivity(Intent(this, MainActivity::class.java))
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
