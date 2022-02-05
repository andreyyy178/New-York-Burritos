package com.example.newyorkburritos.restaurants

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newyorkburritos.models.YelpRestaurant
import com.example.newyorkburritos.databinding.FragmentRestaurantsBinding


class RestaurantsFragment : Fragment() {

    private val viewModel: RestaurantsViewModel by viewModels()
    private lateinit var binding: FragmentRestaurantsBinding
    private lateinit var restaurantsAdapter : RestaurantsAdapter
    private val myRestaurants : MutableList<YelpRestaurant> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRestaurantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restaurantsAdapter = RestaurantsAdapter(requireContext(), myRestaurants)
        binding.rvRestaurants.adapter = restaurantsAdapter
        binding.rvRestaurants.layoutManager = LinearLayoutManager(requireContext())



        viewModel.restaurants.observe(viewLifecycleOwner,
            { restaurants->
                myRestaurants.addAll(restaurants)
                restaurantsAdapter.notifyDataSetChanged()
            })

        viewModel.currentStatus.observe(viewLifecycleOwner,
            { status->
                when (status) {
                    YelpAPIstatus.ERROR -> {
                        val toast = Toast.makeText(requireContext(), "error loading", Toast.LENGTH_SHORT)
                        toast.show()
                    }

                    else -> {
                        throw IllegalStateException("Unexpected result state found")
                    }
                }
            })

    }


}