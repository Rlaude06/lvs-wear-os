package fr.rlaude.lvs.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import fr.rlaude.lvs.R
import fr.rlaude.lvs.adapter.GradesAdapter
import fr.rlaude.lvs.adapter.HomeworksAdapter
import fr.rlaude.lvs.data.Grades
import fr.rlaude.lvs.data.Homeworks
import fr.rlaude.lvs.databinding.ActivityErrorBinding
import fr.rlaude.lvs.databinding.ActivityListGradesBinding
import fr.rlaude.lvs.databinding.ActivityListHomeworksBinding
import fr.rlaude.lvs.databinding.ActivityWaitingBinding


class ListHomeworks : Activity() {

    private lateinit var binding: ActivityListHomeworksBinding
    private lateinit var bindingSpinner: ActivityWaitingBinding
    private lateinit var bindingError: ActivityErrorBinding
    private lateinit var homeworkArrayList: ArrayList<Homeworks>
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
            Request.Method.GET, "$url/getHomeworks", null,
            { response ->
                binding = ActivityListHomeworksBinding.inflate(layoutInflater)
                setContentView(binding.root)
                homeworkArrayList = ArrayList()
                for(i in 0 until response.length()){
                    val homework = Homeworks(
                        response.getJSONObject(i)["id"].toString().toInt(),
                        response.getJSONObject(i)["start_date"].toString(),
                        response.getJSONObject(i)["titre"].toString(),
                        response.getJSONObject(i)["description"].toString(),
                        response.getJSONObject(i)["subject"].toString(),
                        response.getJSONObject(i)["close"].toString().toBoolean(),
                    )
                    homeworkArrayList.add(homework)
                }
                binding.listHomeworksView.isClickable = true
                binding.listHomeworksView.focusable = View.FOCUSABLE
                binding.listHomeworksView.adapter = HomeworksAdapter(this, homeworkArrayList)
                binding.listHomeworksView.requestFocus()
                binding.listHomeworksView.setOnItemClickListener { parent, view, position, id ->

                    val i = Intent(this, OneHomework::class.java)
                    i.putExtra("id", homeworkArrayList[position].id)
                    i.putExtra("dateDue", homeworkArrayList[position].dateDue)
                    i.putExtra("title", homeworkArrayList[position].title)
                    i.putExtra("description", homeworkArrayList[position].description)
                    i.putExtra("subject", homeworkArrayList[position].subject)
                    i.putExtra("close", homeworkArrayList[position].close)
                    startActivity(i)



                }
            },
            {   error ->
                bindingError = ActivityErrorBinding.inflate(layoutInflater)
                setContentView(bindingError.root)
                val textView = findViewById<TextView>(R.id.error)

                textView.text = "Erreur de connexion!\n"
            })

        queue.add(jsonRequest)


    }
}