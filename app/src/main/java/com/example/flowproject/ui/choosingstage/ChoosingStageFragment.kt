package com.example.flowproject.ui.choosingstage

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.flowproject.R
import com.example.flowproject.data.Difficulty
import com.example.flowproject.data.Question
import com.example.flowproject.data.Subject
import com.example.flowproject.databinding.FragmentChoosingModeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

private const val TAG = "ChoosingStage"


@AndroidEntryPoint
class ChoosingStageFragment : DialogFragment() {

    private val viewModel: ChoosingStageViewModel by viewModels()
    private lateinit var selectedSubject: String
    lateinit var selectedDifficulty: String
    private lateinit var action: NavDirections

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "onViewCreated: okay?")
        val builder = AlertDialog.Builder(requireContext())
        var questionList: List<Question> = listOf()
        val binding = FragmentChoosingModeBinding.inflate(LayoutInflater.from(context))
        binding.apply {

            subjectSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedSubject = parent.selectedItem.toString()
                    Log.d(TAG, "selectedObject = $selectedSubject")
                }

            }
            difficultySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    selectedDifficulty = parent.selectedItem.toString()
                    Log.d(TAG, "selectedObject = $selectedDifficulty")
                }

            }

            startBtn.setOnClickListener {
                Log.d(TAG, "onCreateDialog: alright")

                viewModel.getQuestions(
                    Subject.valueOf(selectedSubject.toUpperCase()),
                    Difficulty.valueOf(selectedDifficulty.toUpperCase())
                ).observe(this@ChoosingStageFragment, Observer {
                    questionList = it

                    viewModel.onLastSubjectSelected(Subject.valueOf(selectedSubject.toUpperCase()))
                    viewModel.onLastDifficultySelected(Difficulty.valueOf(selectedDifficulty.toUpperCase()))
                    Log.d(TAG, "onCreateDialogxddd: $questionList")

                    if (!questionList.isNullOrEmpty()) {
                        action =
                            ChoosingStageFragmentDirections.actionChoosingStageFragmentToGameFragment(
                                questionList.toTypedArray(),
                                Difficulty.valueOf(selectedDifficulty.toUpperCase())
                            )
                        findNavController().navigate(action)
                    }
                })
                action =
                    ChoosingStageFragmentDirections.actionChoosingStageFragmentToGameFragment(
                        questionList.toTypedArray(),
                        Difficulty.valueOf(selectedDifficulty.toUpperCase())
                    )

            }

        }
        builder
            .setView(binding.root)


        viewModel.onListFilled()
        this.lifecycleScope.launch {
            binding.apply {
                Log.d(TAG, "onCreateDialog: ${viewModel.lastDifficulty}")
                Log.d(TAG, "onCreateDialog: ${viewModel.lastSubject}")
                subjectSpinner.setSelection(getIndex(subjectSpinner, viewModel.lastSubject.toString()))
                difficultySpinner.setSelection(
                    getIndex(
                        difficultySpinner,
                        viewModel.lastDifficulty.toString()
                    )
                )
            }
        }


        return builder.create()

    }

    private fun getIndex(spinner: Spinner, string: String): Int {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(string, true)) {
                return i
            }
        }
        return -1
    }
}
