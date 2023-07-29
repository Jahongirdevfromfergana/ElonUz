package uz.fergana.elonuz.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import uz.fergana.elonuz.data.models.request.LoginRequest
import uz.fergana.elonuz.main.MainActivity
import uz.fergana.elonuz.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            viewModel = ViewModelProvider(this@LoginActivity)[AuthViewModel::class.java]

            viewModel.errorLiveData.observe(this@LoginActivity) {
                Toast.makeText(this@LoginActivity, it, Toast.LENGTH_SHORT).show()
            }

            viewModel.progressLiveData.observe(this@LoginActivity) {
                flProgress.visibility = if (it) View.VISIBLE else View.GONE
            }

            viewModel.authListLiveData.observe(this@LoginActivity) {
                val i = Intent(this@LoginActivity, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }

            btnRegistration.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
                finish()
            }

            btnSend.setOnClickListener {
                viewModel.login(LoginRequest(edPassword.text.toString(), edPhone.text.toString()))
            }
        }
    }
}