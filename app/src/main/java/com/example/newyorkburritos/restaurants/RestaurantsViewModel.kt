package com.example.newyorkburritos.restaurants

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newyorkburritos.api.RetrofitInstance
import com.example.newyorkburritos.models.YelpRestaurant
import com.example.newyorkburritos.models.YelpSearchResult
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantsViewModel : ViewModel() {

    private val _restaurants: MutableLiveData<List<YelpRestaurant>> = MutableLiveData()
    val restaurants: LiveData<List<YelpRestaurant>> = _restaurants

    private val _currentStatus = MutableLiveData<YelpAPIstatus>()
    val currentStatus: LiveData<YelpAPIstatus> = _currentStatus

    init {
        getRestaurantsCoroutine()
    }

    private fun getRestaurants() {
        val api = RetrofitInstance.api
        api.searchRestaurants("Mexican", "New York").enqueue(object :
            Callback<YelpSearchResult> {
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                Log.i("Restaurants fragment", "onResponse $response")
                val body = response.body()
                if (body == null){
                    Log.w("Restaurants Fragment", "Did not receive valid response body from Yelp API... exiting")
                    return
                }
                _restaurants.value = body.restaurants
            }
            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i("Restaurants fragment", "onFailure $t")
            }

        })
    }

    private fun getRestaurantsCoroutine() {
        viewModelScope.launch{
            val api = RetrofitInstance.api
            try {
                val fetchedRestaurants = api.searchRestaurantsCoroutine("Mexican", "New York").restaurants
                _restaurants.value = fetchedRestaurants
            }
            catch (e: Exception) {
                _currentStatus.value = YelpAPIstatus.ERROR
                Log.i("RestaurantsViewModel", "$e")
            }
        }
    }
}