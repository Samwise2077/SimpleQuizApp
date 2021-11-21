package com.example.flowproject.ui.gameresults

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flowproject.data.Question
import com.example.flowproject.databinding.ItemQuestionBinding

class GameResultsAdapter :
    ListAdapter<Result, GameResultsAdapter.GameResultsViewHolder>(DiffCallback()) {
    class GameResultsViewHolder(private val binding: ItemQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition


                }
            }
        }

        fun bind(result: Result) {
            binding.apply {
                questionText.text = result.questionText
                correctAnswerTextView.text = "Correct answer: " + result.correctAnswer
                correctAnswerTextView.setTextColor(Color.GREEN)
                yourAnswerTextView.text = "Your answer: " + result.yourAnswer
                if (result.correctAnswer == result.yourAnswer) {
                    correctAnswerTextView.setTextColor(Color.BLACK)
                    yourAnswerTextView.setTextColor(Color.GREEN)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameResultsViewHolder {
        val binding =
            ItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameResultsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameResultsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class DiffCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            TODO("Not yet implemented")
        }

    }
}


