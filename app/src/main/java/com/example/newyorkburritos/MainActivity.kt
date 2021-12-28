package com.example.newyorkburritos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {



    //The API can only return up to 1,000 results at this time. The API doesn't allow more than 50 per request.
    //You need to use the offset parameter to get the next page of results. (For using offset, give it any number.
    //If you have limit=50, that means you're getting results 1-50, so give it offset=51 and you'll get 51-100.)

    companion object {
        private const val TAG = "MainActivity"
        private const val BASE_URL = "https://api.yelp.com/v3/"
        private const val API_KEY = "pdQYzkH8JHHYanoSn8k1UlmXojCJjTKYUkC6NXxNVNLzgpOZ6BSNsxdb9ZDjytRju0y4EQWIg8B6a6rxoTwDiusy0CNtj1SPm-bdAY8pR1Kw8qkiKLyD0Cl_yUXJYXYx"
    }

    private lateinit var rvRestaurants : RecyclerView
    private lateinit var restaurants : MutableList<YelpRestaurant>
    private lateinit var restaurantsAdapter : RestaurantsAdapter

    private var limit = 10
    private var offset = 0
    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        restaurants = mutableListOf<YelpRestaurant>()
        restaurantsAdapter = RestaurantsAdapter(this, restaurants)
        rvRestaurants = findViewById(R.id.rvRestaurants)
        rvRestaurants.adapter = restaurantsAdapter
        val layoutManager = LinearLayoutManager(this)
        rvRestaurants.layoutManager = layoutManager
        getRestaurants()

        // RecyclerView Pagination********************************
        rvRestaurants.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                val total = restaurantsAdapter.itemCount

                if (!isLoading) {

                    if ((visibleItemCount + pastVisibleItem) >= total) {
                        offset+=(limit+1)
                        Log.i("offset", "$offset")
                        getRestaurants()
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun getRestaurants() {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val yelpService = retrofit.create(YelpService::class.java)
        yelpService.searchRestaurants("Bearer $API_KEY","Mexican", limit, offset, "New York").enqueue(object : Callback<YelpSearchResult>{
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                Log.i(TAG, "onResponse $response")
                val body = response.body()
                if (body == null){
                    Log.w(TAG, "Did not receive valid response body from Yelp API... exiting")
                    return
                }
                restaurants.addAll(body.restaurants)
                restaurantsAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }

        })
    }
}