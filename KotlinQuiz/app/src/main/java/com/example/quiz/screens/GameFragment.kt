package com.example.quiz.screens

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quiz.Constants
import com.example.quiz.Question
import com.example.quiz.R
import com.example.quiz.databinding.FragmentGameBinding
import com.example.quiz.screens.GameFragmentArgs
import com.example.quiz.screens.GameFragmentDirections

class GameViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        private const val KEY_QUESTIONS_LIST = "questions_list"
        private const val KEY_CURRENT_POSITION = "current_position"
        private const val KEY_SELECTED_POSITION = "selected_position"
        private const val KEY_CORRECT_ANSWER = "correct_answer"
    }

    var questionsList: List<Question>
        get() = savedStateHandle.get(KEY_QUESTIONS_LIST) ?: Constants.getQuestion().also {
            savedStateHandle[KEY_QUESTIONS_LIST] = it
        }
        set(value) {
            savedStateHandle[KEY_QUESTIONS_LIST] = value
        }

    var currentPosition: Int
        get() = savedStateHandle.get(KEY_CURRENT_POSITION) ?: 1
        set(value) {
            savedStateHandle[KEY_CURRENT_POSITION] = value
        }

    var selectedPosition: Int
        get() = savedStateHandle.get(KEY_SELECTED_POSITION) ?: 0
        set(value) {
            savedStateHandle[KEY_SELECTED_POSITION] = value
        }

    var correctAnswer: Int
        get() = savedStateHandle.get(KEY_CORRECT_ANSWER) ?: 0
        set(value) {
            savedStateHandle[KEY_CORRECT_ANSWER] = value
        }
}


class GameFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentGameBinding
    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)

        setQuestion()
        return binding.root
    }

    private fun setQuestion() {
        val question = viewModel.questionsList[viewModel.currentPosition - 1]

        binding.tvQuestion.text = question.question
        binding.tvOptionOne.text = question.optionOne
        binding.tvOptionTwo.text = question.optionTwo
        binding.tvOptionThree.text = question.optionThree
        binding.tvOptionFour.text = question.optionFour
        binding.pb.progress = viewModel.currentPosition
        binding.tvProgress.text = "${viewModel.currentPosition}/${binding.pb.max}"

        defaultAppearance()

        if (viewModel.selectedPosition != 0) {
            when (viewModel.selectedPosition) {
                1 -> selectedOptionView(binding.tvOptionOne, 1)
                2 -> selectedOptionView(binding.tvOptionTwo, 2)
                3 -> selectedOptionView(binding.tvOptionThree, 3)
                4 -> selectedOptionView(binding.tvOptionFour, 4)
            }
        }

        setOptionsEnabled(true)
        binding.btnSubmit.isEnabled = viewModel.selectedPosition != 0
        binding.btnSubmit.text = if (viewModel.currentPosition == viewModel.questionsList.size) {
            "Завершить тест"
        } else {
            "Далее"
        }
    }


    private fun defaultAppearance() {
        val options = listOf(binding.tvOptionOne, binding.tvOptionTwo, binding.tvOptionThree, binding.tvOptionFour)
        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.setBackgroundResource(R.drawable.default_option_border_bg) // Updated
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_optionOne -> selectedOptionView(binding.tvOptionOne, 1)
            R.id.tv_optionTwo -> selectedOptionView(binding.tvOptionTwo, 2)
            R.id.tv_optionThree -> selectedOptionView(binding.tvOptionThree, 3)
            R.id.tv_optionFour -> selectedOptionView(binding.tvOptionFour, 4)
            R.id.btnSubmit -> onSubmitClick()
        }
    }

    private fun selectedOptionView(tv: TextView, selectedPosition: Int) {
        defaultAppearance()
        viewModel.selectedPosition = selectedPosition // Save selected option
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.setBackgroundResource(R.drawable.selected_option_border_bg) // Updated

        // Enable the "Далее" button once an option is selected
        binding.btnSubmit.isEnabled = true
    }

    private fun onSubmitClick() {
        // Save if the answer is correct without showing feedback
        val question = viewModel.questionsList[viewModel.currentPosition - 1]
        if (question.correctAnswer == viewModel.selectedPosition) {
            viewModel.correctAnswer++
        }

        // Move to the next question or end the quiz
        viewModel.currentPosition++
        if (viewModel.currentPosition <= viewModel.questionsList.size) {
            viewModel.selectedPosition = 0
            setQuestion()
        } else {
            // Navigate to score screen
            val action = GameFragmentDirections.actionGameFragmentToScoreFragment()
            val nameOfPlayer by navArgs<GameFragmentArgs>()
            action.score = viewModel.correctAnswer
            action.name = nameOfPlayer.name
            findNavController().navigate(action)
        }
    }

    private fun setOptionsEnabled(enabled: Boolean) {
        binding.tvOptionOne.isEnabled = enabled
        binding.tvOptionTwo.isEnabled = enabled
        binding.tvOptionThree.isEnabled = enabled
        binding.tvOptionFour.isEnabled = enabled
    }
}


