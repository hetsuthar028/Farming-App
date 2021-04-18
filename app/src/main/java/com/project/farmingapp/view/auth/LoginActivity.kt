package com.project.farmingapp.view.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.project.farmingapp.R
import com.project.farmingapp.databinding.ActivityLoginBinding
import com.project.farmingapp.utilities.hide
import com.project.farmingapp.utilities.show
import com.project.farmingapp.utilities.toast
import com.project.farmingapp.view.dashboard.DashboardActivity
import com.project.farmingapp.viewmodel.AuthListener
import com.project.farmingapp.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {
    lateinit var googleSignInClient: GoogleSignInClient
    val firebaseAuth = FirebaseAuth.getInstance()
    lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.authViewModel = viewModel
        viewModel.authListener = this

        if (firebaseAuth.currentUser != null) {
            Intent(this, DashboardActivity::class.java).also {
                startActivity(it)
            }
        }

        createaccountText.setOnClickListener {
            Intent(this, SignupActivity::class.java).also {
                startActivity(it)
            }
        }
        signGoogleBtnLogin.setOnClickListener {
            signIn()
        }

        forgotPasswdTextLogin.setOnClickListener {
            val userEmail = emailEditLogin.text.toString()
            if (userEmail.isNullOrEmpty()) {
                Toast.makeText(this, "Please enter your Email", Toast.LENGTH_SHORT).show()
            } else {
//                Toast.makeText(this, "Please enter your Email", Toast.LENGTH_SHORT).show()
                firebaseAuth.sendPasswordResetEmail(userEmail)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Email Sent", Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
            }
        }

    }


    //googlesignIn
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.returnActivityResult(requestCode, resultCode, data)
    }

    fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }
    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    override fun onStarted() {
        progressLogin.show()
    }

    override fun onSuccess(authRepo: LiveData<String>) {
        authRepo.observe(this, Observer {
            progressLogin.hide()
            if (it.toString() == "Success") {
                toast("Logged In")
                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                Intent(this, DashboardActivity::class.java).also {
                    startActivity(it)
                }
            }
        })
    }

    override fun onFailure(message: String) {
        progressLogin.hide()
        toast("Failure")
    }
}