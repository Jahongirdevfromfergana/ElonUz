package uz.fergana.elonuz.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import uz.fergana.elonuz.data.models.request.RegistrationRequest
import uz.fergana.elonuz.main.MainActivity
import uz.fergana.elonuz.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding
    lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            viewModel = ViewModelProvider(this@RegistrationActivity)[AuthViewModel::class.java]

            viewModel.errorLiveData.observe(this@RegistrationActivity) {
                Toast.makeText(this@RegistrationActivity, it, Toast.LENGTH_SHORT).show()
            }

            viewModel.progressLiveData.observe(this@RegistrationActivity) {
                flProgress.visibility = if (it) View.VISIBLE else View.GONE
            }

            viewModel.authListLiveData.observe(this@RegistrationActivity) {
                val i = Intent(this@RegistrationActivity, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }

            btnLogin.setOnClickListener {
                startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
                finish()
            }

            btnSend.setOnClickListener {
                viewModel.registration(RegistrationRequest(edName.text.toString(),edPassword.text.toString(), edPhone.text.toString()))
            }
        }
    }
}