package com.example.news2.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.news2.R
import com.example.news2.databinding.FragmentPostsBinding
import com.example.news2.databinding.FragmentWebViewBinding


class WebViewFragment : Fragment() {
    private lateinit var navController: NavController
    private val binding: FragmentWebViewBinding by lazy {
        FragmentWebViewBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        val url=arguments?.getString("url")
        binding.webView.loadUrl(url.toString())
// for debugging-->
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//            val popped = findNavController().popBackStack()
//            if (popped) {
//                Log.d("BackStackCheck", "${navController.currentDestination?.label}")
//            } else {
//                Log.d("BackStackCheck", "Nothing to pop from BackStack")
//            }
//        }

    }
    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

}