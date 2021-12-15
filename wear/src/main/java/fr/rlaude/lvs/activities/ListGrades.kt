package fr.rlaude.lvs.activities

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import fr.rlaude.lvs.R
import fr.rlaude.lvs.adapter.GradesAdapter
import fr.rlaude.lvs.data.Grades
import fr.rlaude.lvs.databinding.ActivityErrorBinding
import fr.rlaude.lvs.databinding.ActivityListGradesBinding
import fr.rlaude.lvs.databinding.ActivityWaitingBinding


class ListGrades : Activity() {

    private lateinit var binding: ActivityListGradesBinding
    private lateinit var bindingSpinner: ActivityWaitingBinding
    private lateinit var bindingError: ActivityErrorBinding
    private lateinit var gradesArrayList: ArrayList<Grades>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vibrator = this?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE))
        bindingSpinner = ActivityWaitingBinding.inflate(layoutInflater)
        setContentView(bindingSpinner.root)
        val spinner = findViewById<ImageView>(R.id.spinner)
        Glide.with(this).load(R.drawable.spinner_gif).into(spinner)
        val spinnerText = findViewById<TextView>(R.id.spinnerText)
        spinnerText.text = "Connection au serveur..."



        val queue = Volley.newRequestQueue(this)
        val url = getString(R.string.urlServer)
        val jsonRequest = JsonArrayRequest(
            Request.Method.GET, "$url/getMoyDetailed", null,
            { response ->
                binding = ActivityListGradesBinding.inflate(layoutInflater)
                setContentView(binding.root)
                gradesArrayList = ArrayList()
                var starsCount = 0
                for(i in 0 until response.length()){
                    val grade = Grades(response.getJSONObject(i)["mat"].toString(),response.getJSONObject(i)["moy"].toString().toFloat())
                    gradesArrayList.add(grade)
                    if(response.getJSONObject(i)["moy"].toString().toFloat() > 16){
                        starsCount+=1
                    }
                }
                binding.listGradesView.adapter = GradesAdapter(this, gradesArrayList)
                binding.stars.text = "🥕".repeat(starsCount)
            },
            {error ->
                bindingError = ActivityErrorBinding.inflate(layoutInflater)
                setContentView(bindingError.root)
                val textView = findViewById<TextView>(R.id.error)

                textView.text = "Erreur de connexion!\n"+error.networkResponse.networkTimeMs.toString() +"ms statusCode: " + error.networkResponse.statusCode.toString()
            })

        queue.add(jsonRequest)


    }
}