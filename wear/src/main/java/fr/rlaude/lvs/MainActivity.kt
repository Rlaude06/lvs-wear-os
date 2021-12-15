package fr.rlaude.lvs

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import fr.rlaude.lvs.activities.ListGrades
import fr.rlaude.lvs.activities.ListHomeworks
import fr.rlaude.lvs.activities.Timetable
import fr.rlaude.lvs.activities.UserProfile
import fr.rlaude.lvs.databinding.ActivityErrorBinding
import fr.rlaude.lvs.databinding.ActivityMainBinding
import fr.rlaude.lvs.databinding.ActivityWaitingBinding

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingSpinner: ActivityWaitingBinding
    private lateinit var bindingError: ActivityErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingSpinner = ActivityWaitingBinding.inflate(layoutInflater)
        setContentView(bindingSpinner.root)
        val spinner = findViewById<ImageView>(R.id.spinner)
        Glide.with(this).load(R.drawable.spinner_gif).into(spinner)
        val spinnerText = findViewById<TextView>(R.id.spinnerText)
        spinnerText.text = "Connection au serveur..."

        val queue = Volley.newRequestQueue(this)
        val url = getString(R.string.urlServer)
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, "$url/getUsersInfos", null,
            { response ->
                binding = ActivityMainBinding.inflate(layoutInflater)
                setContentView(binding.root)
                val textView = findViewById<TextView>(R.id.welcome)
                // Display the first 500 characters of the response string.
                val userPrenom = response["userPrenom"].toString()
                textView.text = "Bienvenue %s!".format(userPrenom)
                val sharedPref = this?.getSharedPreferences("HomeworksDone",Context.MODE_PRIVATE)
                var previous = 0
                for (i in 0..10){
                    previous += sharedPref.getInt(i.toString(), 0)
                }
                textView.text = previous.toString()
                val buttonHomeworks = findViewById<CardView>(R.id.buttonHomeworks)
                val buttonGrades = findViewById<CardView>(R.id.buttonGrades)
                val buttonProfile = findViewById<CardView>(R.id.buttonProfile)
                val buttonTimetable = findViewById<CardView>(R.id.buttonTimetable)
                buttonHomeworks.setOnClickListener {

                    val i = Intent(this, ListHomeworks::class.java)
                    startActivity(i)
                }
                buttonGrades.setOnClickListener {
                    val i = Intent(this, ListGrades::class.java)
                    startActivity(i)
                }
                buttonProfile.setOnClickListener {
                    val i = Intent(this, UserProfile::class.java)
                    startActivity(i)
                }
                buttonTimetable.setOnClickListener {
                    val i = Intent(this, Timetable::class.java)
                    startActivity(i)
                }
            },
            { error ->
                bindingError = ActivityErrorBinding.inflate(layoutInflater)
                setContentView(bindingError.root)
                val textView = findViewById<TextView>(R.id.error)

                textView.text = "Erreur de connexion!\n"
            })

        queue.add(jsonRequest)


    }
}