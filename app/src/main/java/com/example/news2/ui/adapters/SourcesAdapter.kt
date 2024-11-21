package com.example.news2.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.news2.data.models.Source
import com.example.news2.ui.fragments.PostsFragment
import com.example.news2.ui.fragments.SourcesFragment

class SourcesAdapter(private val sourcesCount:Int,fragmentActivity: SourcesFragment) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = sourcesCount

    override fun createFragment(position: Int): Fragment {
        return PostsFragment()
 }

}