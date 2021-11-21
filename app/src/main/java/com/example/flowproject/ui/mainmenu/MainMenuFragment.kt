package com.example.flowproject.ui.mainmenu

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.flowproject.R
import com.example.flowproject.databinding.FragmentMainMenuBinding
import com.example.flowproject.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "MainMenuFragment"

@AndroidEntryPoint
class MainMenuFragment : Fragment(R.layout.fragment_main_menu) {
    val viewModel: MainMenuViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainMenuBinding.bind(view)
        binding.apply {
            launchConditions.setOnClickListener {
                viewModel.onChoosingStageClick(viewModel.lastSubject, viewModel.lastDifficulty)
                Log.d(TAG, "onViewCreated: end")

            }
            settingsButton.setOnClickListener {
                TODO("")
            }

        }
        viewLifecycleOwner.lifecycleScope.launch {
            Log.d(TAG, "onViewCreated: ${viewModel.highScore}")
            binding.apply {
                viewModel.preferencesFlow.collect {
                    highScoreTextView.text = highScoreTextView.text.toString() + it.highScore

                }
            }

        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.mainMenuEvent.collect { event->
                when(event){
                    is MainMenuViewModel.MainMenuEvent.NavigateToChoosingStageScreen -> {
                        val action = MainMenuFragmentDirections.actionGlobalChoosingStageFragment(event.subject,event.difficulty)
                        findNavController().navigate(action)
                    }
                }.exhaustive
            }
        }
    }
}