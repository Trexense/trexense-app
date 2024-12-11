package com.example.trexense.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.trexense.data.pref.UserModel
import com.example.trexense.databinding.ActivityLoginBinding
import com.example.trexense.view.main.HomeActivity
import com.example.trexense.view.PageForgotPassword
import com.example.trexense.view.PageWelcome
import com.example.trexense.view.ViewModelFactory
import com.example.trexense.view.register.RegisterActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var name: String

    private lateinit var idUser: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ivArrowBack.setOnClickListener {
            startActivity(Intent(this@LoginActivity, PageWelcome::class.java))

        }
        binding.clickForgotPassword.setOnClickListener {
            startActivity(Intent(this@LoginActivity, PageForgotPassword::class.java))
        }

        binding.tvClickSignup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }


        signIn()

    }

    private fun signIn() {
        binding.btnSignin.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            lifecycleScope.launch {
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()

                viewModel.loginUser(email, password)

                viewModel.isLoading.observe(this@LoginActivity) {
                    showLoading(it)
                }

                viewModel.nameUser.observe(this@LoginActivity) { value ->
                    name = value.toString()
                }

                viewModel.userID.observe(this@LoginActivity) { value ->
                    idUser = value.toString()
                }


                viewModel.isToken.observe(this@LoginActivity) { token ->
                    Log.d(TAG, "name User : ${name} ")
                    Log.d("USERID", ": ${idUser ?: "null"} ")
                    viewModel.saveSession(UserModel(idUser, name, email, token = token.toString()))
                }

                viewModel.isLoginSuccess.observe(this@LoginActivity) { success ->
                    if (success == true) {
                        AlertDialog.Builder(this@LoginActivity).apply {
                            setTitle("Yeah!")
                            setMessage("You have successfully logged in. Let's enjoy your trip in Trexense !")
                            setPositiveButton("Continue") { _, _ ->
                                val intent = Intent(context, HomeActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    } else {
                        viewModel.snackBarText.observe(this@LoginActivity, {
                            it.getContentIfNotHandled()?.let { snackBarText ->
                                Snackbar.make(
                                    window.decorView.rootView,
                                    snackBarText,
                                    Snackbar.LENGTH_SHORT
                                ).show()

                            }
                        })
                    }
                }

            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}