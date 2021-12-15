package fr.rlaude.lvs.activities

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import fr.rlaude.lvs.databinding.ActivityTimetableBinding


class Timetable : Activity() {

    private lateinit var binding: ActivityTimetableBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTimetableBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val vibrator = this?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE))



    }
}