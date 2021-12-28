package com.example.newyorkburritos

import com.google.gson.annotations.SerializedName


data class YelpSearchResult(
    @SerializedName("total") val total: Int,
    @SerializedName("businesses") val restaurants: List<YelpRestaurant>
    )

data class YelpRestaurant (
    val name: String,
    val rating: Double,
    val price: String,
    @SerializedName("review_count") val numReview: Int,
    @SerializedName("distance")  val distanceInMeters: Double,
    @SerializedName("image_url") val imageUrl: String,
    val categories: List<YelpCategory>,
    val location: YelpLocation
) {
    fun displayDistance(): String {
        val milesPerMiter = 0.00062137
        val distanceInMiles = "%.2f".format(distanceInMeters * milesPerMiter)
        return "$distanceInMiles mi"
    }

    fun displayCategories(): String {
        if (categories.isNotEmpty()) {
            val builder = StringBuilder()
            for (category in categories) {
                builder.append(category.title).append(", ")
            }
            return builder.toString().dropLast(2)
        }
        return ""
    }

    data class YelpCategory(
        val title: String
    )

    data class YelpLocation(
        @SerializedName("address1") val address: String
    )
}

