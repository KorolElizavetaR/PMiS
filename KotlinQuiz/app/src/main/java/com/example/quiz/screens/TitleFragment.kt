package com.example.quiz.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.quiz.R
import com.example.quiz.databinding.FragmentTitleBinding
import com.example.quiz.screens.TitleFragmentDirections


class TitleFragment : Fragment() {

    lateinit var binding: FragmentTitleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)
        binding.startBtn.setOnClickListener {
            val action = TitleFragmentDirections.actionTitleFragmentToGameFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }
}