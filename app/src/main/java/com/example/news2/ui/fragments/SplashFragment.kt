package com.example.news2.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.example.news2.R
import com.example.news2.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private lateinit var navController: NavController
    private val binding: FragmentSplashBinding by lazy {
        FragmentSplashBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)


        Handler(Looper.getMainLooper()).postDelayed({
            navController.navigate(
                R.id.action_splashFragment_to_categoriesFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.splashFragment, true) // إزالة SplashFragment من back stack
                    // لضمان عدم العودة اليها عن الضغط علي زر الرجوع
                    .build()
            )

        }, 2000)
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }

}