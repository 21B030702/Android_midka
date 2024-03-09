package com.example.aviatickets.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.aviatickets.R
import com.example.aviatickets.adapter.OfferListAdapter
import com.example.aviatickets.databinding.FragmentOfferListBinding
import com.example.aviatickets.model.viewmodel.OfferListViewModel
import com.example.aviatickets.model.viewmodel.SortType


class OfferListFragment : Fragment() {

    companion object {
        fun newInstance() = OfferListFragment()
    }

    private var _binding: FragmentOfferListBinding? = null
    private val binding
        get() = _binding!!

    private val adapter: OfferListAdapter by lazy {
        OfferListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOfferListBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    private lateinit var viewModel: OfferListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(OfferListViewModel::class.java)
        setupUI()
        setupObservers()
        viewModel.loadOffers()
    }

    private fun setupUI() {
        with(binding) {
            offerList.adapter = adapter

            sortRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {

                    R.id.sort_by_price -> viewModel.sortOffersBy(SortType.PRICE)
                    R.id.sort_by_duration -> viewModel.sortOffersBy(SortType.DURATION)
                }
            }
        }
    }
    private fun setupObservers() {
        viewModel.offersLiveData.observe(viewLifecycleOwner) { offers ->
            adapter.submitList(offers) // Обновление списка в адаптере
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}