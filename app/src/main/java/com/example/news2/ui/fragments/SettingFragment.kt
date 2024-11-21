package com.example.news2.ui.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.news2.MainActivity
import com.example.news2.MainActivity.Companion.SELECTED_LANGUAGE
import com.example.news2.MainActivity.Companion.appLanguage
import com.example.news2.R
import com.example.news2.data.viewModels.SourcesViewModel
import com.example.news2.databinding.FragmentSettingBinding
import java.util.Locale

class SettingFragment : Fragment() {

    private lateinit var languages: List<String>
    private lateinit var navController: NavController
    private val sourcesViewModel: SourcesViewModel by activityViewModels()
    private val binding: FragmentSettingBinding by lazy {
        FragmentSettingBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {}

        languages = listOf("English", "عربي")
        createDropdownMenu(
            binding = binding,
            context = requireContext()
        )

    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.binding?.toolbarTitle1?.setText(getString(R.string.settings))
        (activity as? MainActivity)?.binding?.toolbarTitle2?.setText("")

    }

    private fun createDropdownMenu(
        binding: FragmentSettingBinding,
        context: Context,
    ) {


        binding.autoCompleteTextView.onFocusChangeListener =
            View.OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val arrayAdapter = ArrayAdapter(context, R.layout.dropdown_item, languages)
                    binding.autoCompleteTextView.setAdapter(arrayAdapter)
                }
            }

        if (appLanguage.getString(SELECTED_LANGUAGE, null) == "ar")
            binding.autoCompleteTextView.setText("عربي", false)
        else
            binding.autoCompleteTextView.setText("English", false)

        binding.autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->

            val selectedLanguage = parent.getItemAtPosition(position) as String
            when (selectedLanguage) {
                "English" -> setLang("en")
                "عربي" -> setLang("ar")
            }
            binding.autoCompleteTextView.clearFocus()

        }

    }

    private fun setLang(languageCode: String) {

        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        appLanguage.edit().putString(SELECTED_LANGUAGE, languageCode).apply()

        (activity as? MainActivity)?.recreate()

    }


}

