package com.example.news2.ui.fragments


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.news2.MainActivity
import com.example.news2.R
import com.example.news2.data.uiState.UIState
import com.example.news2.data.viewModels.SourcesViewModel
import com.example.news2.databinding.FragmentCategoriesBinding


class CategoriesFragment : Fragment() {

    private val sourcesViewModel: SourcesViewModel by activityViewModels()
    private lateinit var navController: NavController
    private val binding: FragmentCategoriesBinding by lazy {
        FragmentCategoriesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

            activity?.finish()
        }
        binding.sportsCategory.setOnClickListener {

            sourcesViewModel.setCategory(getString(R.string.sports))
            sourcesViewModel.getSources("sports", requireContext())

        }
        binding.scienceCategory.setOnClickListener {
            sourcesViewModel.setCategory(getString(R.string.science))
            sourcesViewModel.getSources("science", requireContext())
        }
        binding.businessCategory.setOnClickListener {
            sourcesViewModel.setCategory(getString(R.string.business))
            sourcesViewModel.getSources("business", requireContext())
        }
        binding.entertainmentCategory.setOnClickListener {
            sourcesViewModel.setCategory(getString(R.string.entertainment))
            sourcesViewModel.getSources("entertainment", requireContext())
        }
        binding.technologyCategory.setOnClickListener {
            sourcesViewModel.setCategory(getString(R.string.technology))
            sourcesViewModel.getSources("Technology", requireContext())
        }
        binding.healthCategory.setOnClickListener {
            sourcesViewModel.setCategory(getString(R.string.health))
            sourcesViewModel.getSources("health", requireContext())

        }
        sourcesViewModel.sourcesResponse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> {
                    binding.progressBar2.visibility = View.VISIBLE
                }

                is UIState.Success -> {

                    binding.progressBar2.visibility = View.GONE
                    sourcesViewModel.setSources(state.data.sources)
                    if (state.data.sources.isNotEmpty())
                        navController.navigate(R.id.action_categoriesFragment_to_sourcesFragment)
                    else {
                        showCustomDialog(requireContext(), getString(R.string.no_results))
                    }
                }

                is UIState.Error -> {

                    binding.progressBar2.visibility = View.GONE
                    showCustomDialog(requireContext(), state.message)

                }

                null -> {

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.binding?.toolbarTitle1?.setText(getString(R.string.pick_your_category_of))
        (activity as? MainActivity)?.binding?.toolbarTitle2?.setText(getString(R.string.interest))

    }

    override fun onStop() {
        super.onStop()
        sourcesViewModel.removeResponse()
    }

}

fun showCustomDialog(
    context: Context,
    errorMessage: String
) {

    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(true)
    dialog.setContentView(R.layout.custom_dialog)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    val tvMessage = dialog.findViewById<TextView>(R.id.error_message)
    tvMessage.setText(errorMessage)
    dialog.show()
}

