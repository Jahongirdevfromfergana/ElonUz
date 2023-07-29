package uz.fergana.elonuz.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import uz.fergana.elonuz.adapters.AdsAdapter
import uz.fergana.elonuz.adapters.HorizontalCategoryAdapter
import uz.fergana.elonuz.auth.LoginActivity
import uz.fergana.elonuz.data.models.request.AdsFilter
import uz.fergana.elonuz.main.add_ad.AddAdsActivity
import uz.devapp.elonuz.utils.PrefUtils
import uz.fergana.elonuz.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = ViewModelProvider(this@MainFragment)[MainViewModel::class.java]

            viewModel.errorLiveData.observe(requireActivity()) {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }

            viewModel.progressLiveData.observe(requireActivity()) {
                swipe.isRefreshing = it
            }

            swipe.setOnRefreshListener {
                loadData()
            }

            imgAddAds.setOnClickListener {
                startActivity(
                    Intent(
                        requireActivity(),
                        if (PrefUtils.getToken()
                                .isEmpty()
                        ) LoginActivity::class.java else AddAdsActivity::class.java
                    )
                )
            }

            viewModel.categoriesListLiveData.observe(requireActivity()) {
                rvCategory.adapter = HorizontalCategoryAdapter(it.filter { it.parentId == 0 }, it)
            }

            viewModel.adsListLiveData.observe(requireActivity()) {
                rvAds.adapter = AdsAdapter(it)
            }
            loadData()
        }
        return binding.root
    }

    fun loadData() {
        viewModel.getCategories()
        viewModel.getAds(AdsFilter())
        viewModel.getRegions()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}