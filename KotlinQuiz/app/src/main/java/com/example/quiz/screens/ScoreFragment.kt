package com.example.quiz.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quiz.R
import com.example.quiz.databinding.FragmentScoreBinding
import com.example.quiz.screens.ScoreFragmentArgs

class ScoreFragment : Fragment() {

    lateinit var binding: FragmentScoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score, container, false)

        val scoreArgs by navArgs<ScoreFragmentArgs>()
        if (scoreArgs.score != 10) {
            binding.displayResult.text = "Вы набрали ${scoreArgs.score} очков из 10."
        } else {
            binding.displayResult.text = "${scoreArgs.score} из 10! Машина"
        }

        binding.playAgain.setOnClickListener { view: View ->

            view.findNavController().navigate(R.id.action_scoreFragment_to_titleFragment)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            view?.findNavController()?.navigate(R.id.action_scoreFragment_to_titleFragment)
        }
        return binding.root
    }
}