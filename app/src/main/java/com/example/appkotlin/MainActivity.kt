package com.example.appkotlin

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Récupérer le pseudo saisi par l'utilisateur
        val pseudoEditText = findViewById<EditText>(R.id.pseudo_edit_text)
        val connectButton = findViewById<Button>(R.id.connect_button)

        connectButton.setOnClickListener {
            val pseudo = pseudoEditText.text.toString()
            if (pseudo.isNotEmpty()) {
                // Enregistrer le pseudo dans les SharedPreferences
                val sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("pseudo", pseudo)
                editor.apply()

                // Passer à l'écran principal
                val intent = Intent(this, QuizCategoryActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Veuillez saisir un pseudo", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
