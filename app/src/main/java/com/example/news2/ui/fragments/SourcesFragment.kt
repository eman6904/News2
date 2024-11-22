package com.example.news2.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.example.news2.R
import com.example.news2.data.viewModels.SourcesViewModel
import com.example.news2.databinding.FragmentSourcesBinding
import com.example.news2.ui.adapters.SourcesAdapter
import com.google.android.material.tabs.TabLayout


class SourcesFragment : Fragment() {

    private val sourcesViewModel: SourcesViewModel by activityViewModels()

    private val binding: FragmentSourcesBinding by lazy {
        FragmentSourcesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.isSaveEnabled = false
        setTabs(binding, this, sourcesViewModel, viewLifecycleOwner)
        ///////////////////////////////////////////////////////////////////////
        binding.toolbarTitle.setText(sourcesViewModel.category.value)
        binding.searchIcon.setOnClickListener {
            binding.title.visibility = View.GONE
            binding.searchField.visibility = View.VISIBLE
        }
        binding.searchField.setStartIconOnClickListener {
            binding.title.visibility = View.VISIBLE
            binding.searchField.visibility = View.GONE
            binding.QueryField.text!!.clear()
        }
        ///////////////////////////////////////////////////////////////////////
        binding.QueryField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                sourcesViewModel.setSearchQuery(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })


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

private fun setTabs(
    binding: FragmentSourcesBinding,
    fragment: SourcesFragment,
    sourcesViewModel: SourcesViewModel,
    lifecycleOwner: LifecycleOwner
) {

    sourcesViewModel.sourcesList.observe(lifecycleOwner) { sources ->

        sourcesViewModel.setSource(sourcesViewModel.sourcesList.value?.get(0)!!.id!!)

        binding.viewPager.adapter =
            SourcesAdapter(sourcesViewModel.sourcesList.value!!.size, fragment)
        for (source in sources) {
            if(binding.tabLayout.tabCount < sources.size)
             binding.tabLayout.addTab(binding.tabLayout.newTab().setText(source.name))
        }
        for (i in 0 until binding.tabLayout.tabCount) {
            val tab = (binding.tabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val layoutParams = tab.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(8, 0, 8, 0)  // Add left and right margin
            tab.layoutParams = layoutParams
        }
    }
    binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            if (tab != null) {
                binding.viewPager.currentItem = tab.position
                tab.view.setBackground(
                    ContextCompat.getDrawable(
                        fragment.requireContext(),
                        R.drawable.shape_tab_indicator
                    )
                )
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {

            if (tab != null) {
                tab.view.setBackground(
                    ContextCompat.getDrawable(fragment.requireContext(), R.drawable.tab_border)
                )
            }
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {

        }

    })
    binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (position == 0) {
                binding.tabLayout.getTabAt(position)!!.view.setBackground(
                    ContextCompat.getDrawable(
                        fragment.requireContext(),
                        R.drawable.shape_tab_indicator
                    )
                )
            }
            sourcesViewModel.setSource((sourcesViewModel.sourcesList.value?.get(position)?.id)!!)

            binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
        }
    })

}
