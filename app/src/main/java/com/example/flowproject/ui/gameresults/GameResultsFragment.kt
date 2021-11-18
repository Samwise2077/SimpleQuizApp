package com.example.flowproject.ui.gameresults

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flowproject.R
import com.example.flowproject.databinding.FragmentGameResultsBinding
import dagger.hilt.android.AndroidEntryPoint

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

            }
            yourScore.text = yourScore.text.toString() + viewModel.highScore
        }
        if(viewModel.highScore > 0){
            viewModel.onHighScoreChanged(viewModel.highScore, requireContext())
        }
    }
}