package com.example.flowproject.ui.mainmenu

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flowproject.R
import com.example.flowproject.databinding.FragmentMainMenuBinding

private const val TAG = "MainMenuFragment"

class MainMenuFragment : Fragment(R.layout.fragment_main_menu) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainMenuBinding.bind(view)
        binding.apply {
            launchConditions.setOnClickListener {
                val action = MainMenuFragmentDirections.actionGlobalChoosingStageFragment()
                findNavController().navigate(action)
           //     var alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
           //     val layoutInflater: LayoutInflater =
                Log.d(TAG, "onViewCreated: end")

            }
            settingsButton.setOnClickListener {
                TODO("")
            }
        }
    }
}