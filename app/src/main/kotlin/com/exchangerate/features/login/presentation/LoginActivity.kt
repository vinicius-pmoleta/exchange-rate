package com.exchangerate.features.login.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.exchangerate.R
import com.exchangerate.core.structure.BaseActivity
import com.exchangerate.features.home.presentation.HomeActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : BaseActivity() {

    companion object {
        const val AUTHENTICATION_REQUEST_CODE = 0
    }

    override fun initializeDependencyInjector() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val providers = listOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build()
        )

        FirebaseAuth.getInstance().currentUser?.let {
            redirectToHomeScreen()
        } ?: startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                AUTHENTICATION_REQUEST_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AUTHENTICATION_REQUEST_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                Log.d("Login", "Success $user")
                redirectToHomeScreen()
            } else {
                Log.e("Login", "Error ${response?.error?.errorCode}", response?.error)
                Toast.makeText(this, R.string.default_error_remote_message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun redirectToHomeScreen() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}