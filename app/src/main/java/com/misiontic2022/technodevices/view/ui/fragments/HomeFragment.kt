package com.misiontic2022.technodevices.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.misiontic2022.technodevices.R
import com.misiontic2022.technodevices.databinding.FragmentHomeBinding
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding : FragmentHomeBinding
    private val list = mutableListOf<CarouselItem>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        list.add(CarouselItem("https://technodevices-fe.herokuapp.com/img/banner1.059f4c55.png"))
        list.add(CarouselItem("https://technodevices-fe.herokuapp.com/img/banner2.36a00eb9.png"))
        list.add(CarouselItem("https://technodevices-fe.herokuapp.com/img/banner3.d13afcf5.png"))

        binding.carouselProduct.addData(list)
    }

}