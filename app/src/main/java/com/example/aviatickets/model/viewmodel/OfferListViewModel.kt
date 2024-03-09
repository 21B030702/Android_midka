package com.example.aviatickets.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aviatickets.model.entity.Offer
import kotlinx.coroutines.launch
import com.example.aviatickets.model.network.ApiClient



class OfferListViewModel : ViewModel() {
    private val _offersLiveData = MutableLiveData<List<Offer>>()
    val offersLiveData: LiveData<List<Offer>> = _offersLiveData

    init {
        loadOffers()
    }

    fun loadOffers() {
        viewModelScope.launch {
            val response = ApiClient.offerApiService.getOffers()
            if (response.isSuccessful) {
                _offersLiveData.postValue(response.body() ?: emptyList())
            } else {
                // Обработка ошибок
            }
        }
    }

    fun sortOffersBy(sortType: SortType) {
        val currentOffers = _offersLiveData.value
        if (currentOffers != null) {
            val sortedList = when (sortType) {
                SortType.PRICE -> currentOffers.sortedBy { it.price }
                SortType.DURATION -> currentOffers.sortedBy { it.flight.duration }
            }
            _offersLiveData.postValue(sortedList)
        }
    }
}
