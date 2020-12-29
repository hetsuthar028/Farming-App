package com.project.farmingapp.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.project.farmingapp.R
import com.project.farmingapp.databinding.ActivitySignupBinding
import com.project.farmingapp.utilities.toast
import com.project.farmingapp.viewmodel.AuthListener
import com.project.farmingapp.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity(), AuthListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivitySignupBinding  = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        val viewModel =ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.authViewModel  = viewModel

        viewModel.authListener =this


        loginRedirectTextSignup.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
        }

    }

    override fun onStarted() {
        toast("Started")
    }

    override fun onSuccess() {
        toast("Success")
    }

    override fun onFailure(message: String) {
        toast("Failure")
    }
}