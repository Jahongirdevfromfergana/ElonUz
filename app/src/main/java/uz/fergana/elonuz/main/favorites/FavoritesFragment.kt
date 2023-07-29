package uz.fergana.elonuz.main.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import uz.fergana.elonuz.adapters.AdsAdapter
import uz.fergana.elonuz.data.models.AdsModel
import uz.fergana.elonuz.data.models.request.AdsFilter
import uz.fergana.elonuz.main.ads.AdsViewModel
import uz.devapp.elonuz.utils.PrefUtils
import uz.fergana.elonuz.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {
    lateinit var binding: FragmentFavoritesBinding
    lateinit var viewModel: AdsViewModel
    lateinit var list:MutableList<AdsModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentFavoritesBinding.inflate(inflater,container,false)
        binding.apply {
            viewModel = ViewModelProvider(this@FavoritesFragment)[AdsViewModel::class.java]

            viewModel.errorLiveData.observe(requireActivity()) {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }

            viewModel.progressLiveData.observe(requireActivity()) {
                swipe.isRefreshing = it
            }

            viewModel.adsListLiveData.observe(requireActivity()) {  adsList->
                list=mutableListOf()
                val favoriteList = PrefUtils.getFavoriteList()
                adsList.forEach {adsModel->
                    favoriteList.forEach {
                        if (adsModel.id==it){
                            list.add(adsModel)
                        }
                    }
                }
                rv.adapter = AdsAdapter(list)
                lottie.visibility = if (list.isNotEmpty()) View.GONE else View.VISIBLE
            }

            swipe.setOnRefreshListener {
                loadData()
            }
            loadData()
        }
        return binding.root
    }

    private fun loadData() {
        viewModel.getAds(AdsFilter())
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoritesFragment()
    }
}