package com.example.newyorkburritos

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


class RestaurantsAdapter(val context: Context, val restaurants: List<YelpRestaurant>) :
    RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvDistance : TextView = itemView.findViewById(R.id.tvDistance)
        private val tvNumReviews : TextView = itemView.findViewById(R.id.tvNumReviews)
        private val tvPrice : TextView = itemView.findViewById(R.id.tvPrice)
        private val tvAddress : TextView = itemView.findViewById(R.id.tvAddress)
        private val tvCategories : TextView = itemView.findViewById(R.id.tvCategories)
        private val ratingBar : RatingBar = itemView.findViewById(R.id.ratingBar)
        private val imImage : ImageView = itemView.findViewById(R.id.ivImage)

        fun bind(restaurant: YelpRestaurant) {
            tvName.text = restaurant.name
            tvDistance.text = restaurant.displayDistance()
            tvNumReviews.text = "${restaurant.numReview} Reviews"
            tvPrice.text = restaurant.price
            tvAddress.text = restaurant.location.address
            tvCategories.text = restaurant.displayCategories()
            ratingBar.rating = restaurant.rating.toFloat()

            val radius = 15 // corner radius, higher value = more rounded
            val cropOptions = RequestOptions(). transform(MultiTransformation(CenterCrop(), RoundedCorners(radius)))
            Glide.with(context)
                .load(restaurant.imageUrl).apply(cropOptions)
                .into(imImage)
        }

    }

    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false))
    }

    //Involves populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val restaurant: YelpRestaurant = restaurants[position]
        holder.bind(restaurant)
    }

    override fun getItemCount() = restaurants.size

}
