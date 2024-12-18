package com.booknotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.booknotes.R
import kotlinx.android.synthetic.main.fragment_main_screen.view.addNoteFloatingButton


class MainScreen : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = return inflater.inflate(R.layout.fragment_main_screen, container, false)
        view.addNoteFloatingButton.setOnClickListener{
            findNavController().navigate(R.id.action_mainScreen_to_addScreen)
        }
        return view
    }
}