package uz.fergana.elonuz.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import uz.fergana.elonuz.auth.LoginActivity
import uz.fergana.elonuz.splash.SplashActivity
import uz.devapp.elonuz.utils.PrefUtils
import uz.fergana.elonuz.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.apply {

            if (PrefUtils.getToken().isEmpty()) {
                login.visibility = View.VISIBLE
                tvName.visibility = View.GONE
                tvPhone.visibility = View.GONE
                lyLogout.visibility = View.GONE
            } else {
                login.visibility = View.GONE
                tvName.visibility = View.VISIBLE
                tvPhone.visibility = View.VISIBLE
                lyLogout.visibility = View.VISIBLE
            }

            login.setOnClickListener {
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
            }

            viewModel = ViewModelProvider(requireActivity())[ProfileViewModel::class.java]

            viewModel.errorLiveData.observe(requireActivity()) {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }

            viewModel.progressLiveData.observe(requireActivity()) {
                flProgress.visibility = if (it) View.VISIBLE else View.GONE
            }

            viewModel.userListLiveData.observe(requireActivity()) {
                tvName.text = it.fullname
                tvPhone.text = it.phone
            }

            lyLogout.setOnClickListener {
                PrefUtils.clear()
                val i = Intent(requireActivity(), SplashActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }
            if (PrefUtils.getToken().isNotEmpty()) {
                viewModel.getUser()
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}