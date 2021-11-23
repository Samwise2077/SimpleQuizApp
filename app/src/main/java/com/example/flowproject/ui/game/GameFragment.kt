package com.example.flowproject.ui.game

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.flowproject.R
import com.example.flowproject.databinding.FragmentGameBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.flowproject.ui.gameresults.Result
import com.example.flowproject.util.exhaustive
import kotlinx.coroutines.flow.collect

private const val TAG = "GameFragment"


@AndroidEntryPoint
class GameFragment : Fragment(R.layout.fragment_game) {
    private val viewModel: GameViewModel by viewModels()
    private val questionCounter = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onNewQuestionShow()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.gameEvent.collect { event ->
                    when (event) {
                        is GameViewModel.GameEvent.NavigateToGameResults -> {
                            val action = GameFragmentDirections.actionGameFragmentToGameResultsFragment(event.resultList.toTypedArray(), event.scores)
                            findNavController().navigate(action)
                        }
                        is GameViewModel.GameEvent.ShowNewQuestion -> {
                            val binding = FragmentGameBinding.bind(requireView())
                            binding.apply {
                                questionTextView.text = viewModel.questionText
                                val randomOptionsList = listOf(0, 1, 2, 3).shuffled()
                                btnTopRight.text = viewModel.options[randomOptionsList[0]]
                                btnTopLeft.text = viewModel.options[randomOptionsList[1]]
                                btnBottomLeft.text = viewModel.options[randomOptionsList[2]]
                                btnBottomRight.text = viewModel.options[randomOptionsList[3]]
                                val onClickListener: View.OnClickListener = View.OnClickListener { it ->
                                    val indexOfCorrectAnswer = randomOptionsList.indexOf(0)

                                    it as Button
                                    Log.d(TAG, "questionText = ${viewModel.questionText}, answer = ${it.text.toString()}")
                                    viewModel.resultsList.add(Result(viewModel.questionText, it.text.toString(), viewModel.options[0]))
                                    if ((indexOfCorrectAnswer == 0 && btnTopRight == it) ||
                                        (indexOfCorrectAnswer == 1 && btnTopLeft == it) ||
                                        (indexOfCorrectAnswer == 2 && btnBottomLeft == it) ||
                                        (indexOfCorrectAnswer == 3 && btnBottomRight == it)
                                    ) {
                                        Log.d(TAG, "showNewQuestion: correct button")
                                        viewModel.onCorrectButtonClicked(true)
                                    } else {
                                        viewModel.onCorrectButtonClicked(false)
                                    }
                                    if (viewModel.questionCounter >= viewModel.questionList?.size ?: 5) {
                                        Log.d(TAG, "showNewQuestion: navigate")
                                        viewModel.onQuestionsUsedUp(viewModel.scores, viewModel.resultsList)
                                    }
                                    viewModel.onNewQuestionShow()

                                }
                                btnTopRight.setOnClickListener(onClickListener)
                                btnTopLeft.setOnClickListener(onClickListener)
                                btnBottomRight.setOnClickListener(onClickListener)
                                btnBottomLeft.setOnClickListener(onClickListener)
                                Log.d("", "onViewCreated: ")


                            }
                            null
                        }
                    }.exhaustive
            }
        }
    }
}