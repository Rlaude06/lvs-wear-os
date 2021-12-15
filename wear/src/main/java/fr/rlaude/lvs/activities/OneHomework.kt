package fr.rlaude.lvs.activities

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.TextView
import fr.rlaude.lvs.R
import fr.rlaude.lvs.databinding.ActivityOneHomeworkBinding


class OneHomework : Activity() {

    private lateinit var binding: ActivityOneHomeworkBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOneHomeworkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val vibrator = this?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE))

        val id = intent.getIntExtra("id", 0).toString()
        val dateDue = intent.getStringExtra("dateDue")
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val subject = intent.getStringExtra("subject")
        val close = intent.getBooleanExtra("close", false)

        val idTextView = findViewById<TextView>(R.id.homework_id)
        val dateDueView = findViewById<TextView>(R.id.homework_date_due)
        val titleView= findViewById<TextView>(R.id.homework_title)
        val descriptionView = findViewById<TextView>(R.id.homework_description)
        val subjectView = findViewById<TextView>(R.id.homework_subject)
        when (subject){
            getString(R.string.math_code) -> { subjectView.text = getString(R.string.math_text); subjectView.setTextColor(getColor(R.color.math_color));}
            getString(R.string.hi_ge_code) -> { subjectView.text = getString(R.string.hi_ge_text); subjectView.setTextColor(getColor(R.color.hi_ge_color));}
            getString(R.string.ph_ch_code) -> { subjectView.text = getString(R.string.ph_ch_text); subjectView.setTextColor(getColor(R.color.ph_ch_color));}
            getString(R.string.ses_code) -> { subjectView.text = getString(R.string.ses_text); subjectView.setTextColor(getColor(R.color.ses_color));}
            getString(R.string.svt_code) -> { subjectView.text = getString(R.string.svt_text); subjectView.setTextColor(getColor(R.color.svt_color));}
            getString(R.string.snt_code) -> { subjectView.text = getString(R.string.snt_text); subjectView.setTextColor(getColor(R.color.snt_color));}
            getString(R.string.fra_code) -> { subjectView.text = getString(R.string.fra_text); subjectView.setTextColor(getColor(R.color.fra_color));}
            getString(R.string.lva_code) -> { subjectView.text = getString(R.string.lva_text); subjectView.setTextColor(getColor(R.color.lva_color));}
            getString(R.string.lvb_code) -> { subjectView.text = getString(R.string.lvb_text); subjectView.setTextColor(getColor(R.color.lvb_color));}
            getString(R.string.eps_code) -> { subjectView.text = getString(R.string.eps_text); subjectView.setTextColor(getColor(R.color.eps_color));}
        }

        idTextView.text = id
        dateDueView.text = dateDue
        titleView.text = title
        descriptionView.text = description
        if (close){
            dateDueView.setTextColor(getColor(R.color.close_date_due))
        }



    }
}