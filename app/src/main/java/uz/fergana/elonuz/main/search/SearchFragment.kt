package uz.fergana.elonuz.main.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import uz.fergana.elonuz.adapters.AdsAdapter
import uz.fergana.elonuz.data.models.request.AdsFilter
import uz.fergana.elonuz.databinding.FragmentSearchBinding
import uz.fergana.elonuz.main.ads.AdsViewModel

class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var viewModel: AdsViewModel
    var keyword = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = ViewModelProvider(this@SearchFragment)[AdsViewModel::class.java]

            viewModel.errorLiveData.observe(requireActivity()) {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }

            viewModel.progressLiveData.observe(requireActivity()) {
                swipe.isRefreshing = it
            }

            viewModel.adsListLiveData.observe(requireActivity()) {
                rv.adapter = AdsAdapter(it)
                lottie.visibility = if (it!!.isNotEmpty()) View.GONE else View.VISIBLE
            }

            swipe.setOnRefreshListener {
                loadData(keyword)
            }

            search.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    keyword = p0.toString()
                    if (keyword.isNotEmpty()) {
                        clearText.visibility = View.VISIBLE
                    } else {
                        clearText.visibility = View.GONE
                        loadData(keyword)
                    }
                    if (keyword.count() > 1)
                        loadData(keyword)
                }
            })
            clearText.setOnClickListener {
                binding.search.setText("")
                loadData("")
            }

            loadData(keyword)
        }
        return binding.root
    }

    private fun loadData(keyword: String) {
        viewModel.getAds(AdsFilter(keyword = keyword))
    }

    companion object {

        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}