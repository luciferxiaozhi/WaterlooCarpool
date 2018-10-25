package com.example.jiangzizheng.waterloocarpool.backend.api

import android.app.Activity
import com.example.jiangzizheng.waterloocarpool.global.Constants.RC_GOOGLE_SIGN_IN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

@Suppress("MemberVisibilityCanBePrivate")
object Auth {
    val instance: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    enum class OAuthType {
        Facebook,
        Google,
        Email,
    }

    private fun retrieveGoogleOAuthToken(activity: Activity) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("828708864479-5o9in9hc2maotclfti7tidrtevbb2cic.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val client = GoogleSignIn.getClient(activity, gso)
        activity.startActivityForResult(client.signInIntent, RC_GOOGLE_SIGN_IN)
    }

    fun retrieveOAuthToken(activity: Activity, type: OAuthType) {
        when (type) {
            OAuthType.Facebook -> {

            }
            OAuthType.Google -> {
                retrieveGoogleOAuthToken(activity)
            }
            OAuthType.Email -> {

            }

        }
    }
}