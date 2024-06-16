package com.example.appkotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appkotlin.Question

class QuizCategoryActivity : AppCompatActivity() {

    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category)

        // Créer une liste de catégories de Quiz
        val categories = listOf(
            "Jeux-vidéos",
            "Cinéma",
            "Art et littérature",
            "Musique",
            "Culture générale",
            "Sport"
        )

        // Créer une liste de questions pour chaque catégorie de Quiz
        val questionsByCategory: Map<String, List<Question>> = mapOf(
            "Jeux-vidéos" to listOf(
                Question(
                    "Quel est le personnage principal de la série de jeux vidéo Zelda ?",
                    listOf("Mario", "Link", "Pikachu", "Sonic"),
                    "Link"
                ),
                // Ajouter d'autres questions pour la catégorie "Jeux-vidéos"
            ),
            "Cinéma" to listOf(
                Question(
                    "Qui a réalisé le film Pulp Fiction ?",
                    listOf("Quentin Tarantino", "Steven Spielberg", "Martin Scorsese", "Stanley Kubrick"),
                    "Quentin Tarantino"
                ),
                // Ajouter d'autres questions pour la catégorie "Cinéma"
            ),
            // Ajouter d'autres catégories de Quiz et leurs questions correspondantes
        )
        // Initialiser le RecyclerView pour afficher les catégories
        val categoryRecyclerView = findViewById<RecyclerView>(R.id.category_recycler_view)
        categoryAdapter = CategoryAdapter(categories) { category ->
            // Générer un Quiz aléatoire pour la catégorie sélectionnée
            val questions = questionsByCategory[category]
            if (questions == null) {
                // La catégorie sélectionnée n'existe pas dans la map, afficher un message d'erreur
                Toast.makeText(this, "Pas de questions disponibles pour cette catégorie", Toast.LENGTH_SHORT).show()
                return@CategoryAdapter
            }
            val quiz = generateQuiz(category, questions)

            // Passer à l'écran de Quiz
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("quiz", quiz)
            startActivity(intent)
        }


        categoryRecyclerView.adapter = categoryAdapter
        categoryRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun generateQuiz(category: String, questions: List<Question>): Quiz? {
        // Générer un Quiz aléatoire à partir des questions de la catégorie sélectionnée
        val shuffledQuestions = questions.shuffled()
        return Quiz(category, shuffledQuestions.take(10))
    }


}
