package com.example.flowproject.ui.gameresults

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flowproject.R
import com.example.flowproject.databinding.FragmentGameResultsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private const val TAG = "GameResultsFragment"

@AndroidEntryPoint
class GameResultsFragment : Fragment(R.layout.fragment_game_results) {
    val viewModel: GameResultsViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentGameResultsBinding.bind(view)
        val gameResultsAdapter = GameResultsAdapter()
        binding.apply {
            recyclerViewResults.apply {
                adapter = gameResultsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
            gameResultsAdapter.submitList(viewModel.results)
            btnBackToMenu.setOnClickListener {
                val action =
                    GameResultsFragmentDirections.actionGameResultsFragmentToMainMenuFragment()
                findNavController().navigate(action)
            }
            yourScore.text = yourScore.text.toString() + viewModel.scores
        }
        viewLifecycleOwner.lifecycleScope.launch {
            if (viewModel.scores > viewModel.preferencesFlow.first().highScore) {
                viewModel.onHighScoreChanged(viewModel.scores)

            }
        }


    }
}