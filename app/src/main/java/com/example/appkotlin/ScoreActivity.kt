package com.example.appkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ScoreActivity : AppCompatActivity() {

    private lateinit var quiz: Quiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        // Récupérer le nombre de réponses correctes
        val correctAnswers = intent.getIntExtra("correct_answers", 0)

        // Afficher le score de l'utilisateur
        findViewById<TextView>(R.id.score_text_view).text = "Votre score : $correctAnswers/10"

        // Récupérer les scores des autres utilisateurs pour la même catégorie de Quiz
        val sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val scores = sharedPreferences.getStringSet("scores_${quiz.category}", mutableSetOf())

        // Ajouter le score de l'utilisateur aux scores existants
        scores?.add("$correctAnswers/10")
        val editor = sharedPreferences.edit()
        editor.putStringSet("scores_${quiz.category}", scores)
        editor.apply()

        // Afficher les 10 meilleurs scores
        val topScores = scores?.toList()?.sortedByDescending { it.split("/")[0].toInt() }?.take(10)
        val scoreList = findViewById<ListView>(R.id.score_list)
        scoreList.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, topScores ?: emptyList())

        // Ajouter un bouton pour recommencer un nouveau Quiz
        findViewById<Button>(R.id.restart_button).setOnClickListener {
            val intent = Intent(this, QuizCategoryActivity::class.java)
            startActivity(intent)
        }
    }
}
