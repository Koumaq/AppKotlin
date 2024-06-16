package com.example.appkotlin

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    private lateinit var quiz: Quiz

    fun setQuiz(quiz: Quiz) {
        this.quiz = quiz
    }

    fun getQuiz(): Quiz? {
        return quiz
    }

    fun getCurrentQuestion(): Question {
        return quiz.questions[currentQuestionIndex]
    }

    fun isCorrectAnswer(answer: String): Boolean {
        return answer == getCurrentQuestion().correctAnswer
    }

    fun moveToNextQuestion() {
        currentQuestionIndex++
    }

    fun resetQuiz() {
        currentQuestionIndex = 0
        correctAnswers = 0
    }

    private var currentQuestionIndex = 0
    private var correctAnswers = 0
}
