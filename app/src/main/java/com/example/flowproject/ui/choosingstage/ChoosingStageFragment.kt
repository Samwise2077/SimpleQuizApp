package com.example.flowproject.ui.choosingstage

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.flowproject.data.Difficulty
import com.example.flowproject.data.Question
import com.example.flowproject.data.Subject
import com.example.flowproject.databinding.FragmentChoosingModeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.security.acl.Owner

@AndroidEntryPoint
class ChoosingStageFragment : DialogFragment() {

    private val viewModel: ChoosingStageViewModel by viewModels()
    private lateinit var selectedSubject: String
    lateinit var selectedDifficulty: String
    private lateinit var action: NavDirections

    private val TAG = "ChoosingStage"
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
       Log.d(TAG, "onViewCreated: okay?")
        val builder = AlertDialog.Builder(requireContext())
        var questionList: List<Question> = listOf()
      //  Log.d(TAG, "onCreateDialog: $difficulty")
        val binding = FragmentChoosingModeBinding.inflate(LayoutInflater.from(context))
        binding.apply {

            subjectSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    selectedSubject = parent.selectedItem.toString()
                    Log.d(TAG, "selectedObject = $selectedSubject")
                }

            }
            difficultySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    selectedDifficulty = parent.selectedItem.toString()
                    Log.d(TAG, "selectedObject = $selectedDifficulty")
                }

            }

            startBtn.setOnClickListener {
                Log.d(TAG, "onCreateDialog: alright")

                viewModel.getQuestions(Subject.valueOf(selectedSubject.toUpperCase()), Difficulty.valueOf(selectedDifficulty.toUpperCase())).observe(this@ChoosingStageFragment, Observer {
                    questionList = it

                    viewModel.onLastSubjectSelected(Subject.valueOf(selectedSubject.toUpperCase()), requireContext())
                    viewModel.onLastDifficultySelected(Difficulty.valueOf(selectedDifficulty.toUpperCase()), requireContext())
                    Log.d(TAG, "onCreateDialogxddd: $questionList")
                    action =
                        ChoosingStageFragmentDirections.actionChoosingStageFragmentToGameFragment(
                            questionList.toTypedArray(), Difficulty.valueOf(selectedDifficulty.toUpperCase())
                        )
                    findNavController().navigate(action)

                })
                Log.d(TAG, "$questionList")
                viewModel.fillList()

                action =
                    ChoosingStageFragmentDirections.actionChoosingStageFragmentToGameFragment(
                        questionList.toTypedArray(), Difficulty.valueOf(selectedDifficulty.toUpperCase())
                    )

            }

        }
        builder
            .setView(binding.root)


        viewModel.fillList()
        this.lifecycleScope.launch {
            Log.d(TAG, "onCreateDialog: " + viewModel.preferencesFlow.toString())
        }

      /*  viewModel.getQuestions(Subject.MATH, Difficulty.EASY).observe(this, Observer {
            questionList = it
        })*/
        return builder.create()

    }
}
