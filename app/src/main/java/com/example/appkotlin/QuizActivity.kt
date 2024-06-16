package com.example.appkotlin

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class QuizActivity : AppCompatActivity() {

    private lateinit var quiz: Quiz
    private lateinit var quizViewModel: QuizViewModel
    private var currentQuestionIndex = 0
    private var correctAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Récupérer le Quiz généré aléatoirement
        val quizExtra = intent.getSerializableExtra("quiz")
        if (quizExtra != null) {
            quiz = quizExtra as Quiz
        } else {
            // Gérer le cas où la valeur est null
            Toast.makeText(this, "Erreur : impossible de récupérer le quiz", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Initialiser le ViewModel pour gérer la logique du Quiz
        quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
        quizViewModel.setQuiz(quiz)

        // Afficher la première question du Quiz
        updateQuestion()

        // Démarrer le compteur de temps pour la question courante
        startQuestionTimer()

        // Gérer les clics sur les boutons de réponse
        val answerButtons = listOf(
            findViewById<Button>(R.id.answer_button1),
            findViewById<Button>(R.id.answer_button2),
            findViewById<Button>(R.id.answer_button3),
            findViewById<Button>(R.id.answer_button4)
        )
        for (button in answerButtons) {
            button.setOnClickListener { view ->
                val selectedAnswer = (view as Button).text.toString()
                if (quizViewModel.isCorrectAnswer(selectedAnswer)) {
                    correctAnswers++
                }
                currentQuestionIndex++
                if (currentQuestionIndex < quizViewModel.getQuiz()!!.questions.size) {
                    updateQuestion()
                    startQuestionTimer()
                } else {
                    // Passer à la page des scores
                    val intent = Intent(this, ScoreActivity::class.java)
                    intent.putExtra("correct_answers", correctAnswers)
                    startActivity(intent)
                }
            }
        }
    }

    private fun updateQuestion() {
        val question = quizViewModel.getCurrentQuestion()
        findViewById<TextView>(R.id.question_text_view).text = question.text
        val answerButtons = listOf(
            findViewById<Button>(R.id.answer_button1),
            findViewById<Button>(R.id.answer_button2),
            findViewById<Button>(R.id.answer_button3),
            findViewById<Button>(R.id.answer_button4)
        )
        val answers = question.answers.shuffled()
        for ((i, answer) in answers.withIndex()) {
            answerButtons[i].text = answer
        }
    }

    private fun startQuestionTimer() {
        val countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                findViewById<TextView>(R.id.timer_text_view).text = "Temps restant : ${millisUntilFinished / 1000} secondes"
            }

            override fun onFinish() {
                // Temps écoulé, passer à la question suivante ou à la page des scores
                currentQuestionIndex++
                if (currentQuestionIndex < quizViewModel.getQuiz()!!.questions.size) {
                    updateQuestion()
                    startQuestionTimer()
                } else {
                    // Passer à la page des scores
                    val intent = Intent(this@QuizActivity, ScoreActivity::class.java)
                    intent.putExtra("correct_answers", correctAnswers)
                    startActivity(intent)
                }
            }
        }
        countDownTimer.start()
    }
}
