package fr.rlaude.lvs.activities

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import fr.rlaude.lvs.R
import fr.rlaude.lvs.databinding.ActivityErrorBinding
import fr.rlaude.lvs.databinding.ActivityUserProfileBinding
import fr.rlaude.lvs.databinding.ActivityWaitingBinding


class UserProfile : Activity() {

    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var bindingSpinner: ActivityWaitingBinding
    private lateinit var bindingError: ActivityErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE))
        bindingSpinner = ActivityWaitingBinding.inflate(layoutInflater)
        setContentView(bindingSpinner.root)
        val spinner = findViewById<ImageView>(R.id.spinner)
        Glide.with(this).load(R.drawable.spinner_gif).into(spinner)
        val spinnerText = findViewById<TextView>(R.id.spinnerText)
        spinnerText.text = "Connection au serveur..."

        val queue = Volley.newRequestQueue(this)
        val url = getString(R.string.urlServer)
        val getBasicInfos = JsonObjectRequest(
            Request.Method.GET, "$url/getUsersInfos", null,
            { Jsonresponse ->
                val getAverage = StringRequest(
                    Request.Method.GET, "$url/getMoyGen",
                    { Stringresponse ->

                        binding = ActivityUserProfileBinding.inflate(layoutInflater)
                        setContentView(binding.root)
                        val profilePictureView = findViewById<CircleImageView>(R.id.profilePicture)
                        Glide.with(this).load("$url/static/33111.jpg").into(profilePictureView)

                        val usernameView = findViewById<TextView>(R.id.username)
                        val etabNameView = findViewById<TextView>(R.id.etabName)
                        usernameView.text = Jsonresponse["userPrenom"].toString() +" "+ Jsonresponse["userNom"].toString()
                        etabNameView.text = Jsonresponse["etabName"].toString()


                        val averageView = findViewById<TextView>(R.id.average)
                        averageView.text = "Moyenne Générale: %s".format(Stringresponse.toString())
                        val average = Stringresponse.toFloat()
                        val imageComplimentView = findViewById<ImageView>(R.id.imageCompliment)
                        val complimentView = findViewById<TextView>(R.id.compliment)
                        if (average > 16) {
                            Glide.with(this).load(R.drawable.felicitations).into(imageComplimentView)
                            complimentView.text = "Félicitations!"
                            complimentView.setTextColor(getColor(R.color.felicitations))
                        } else if(average > 14) {
                            Glide.with(this).load(R.drawable.bravo).into(imageComplimentView)
                            complimentView.text = "Bravo!"
                            complimentView.setTextColor(getColor(R.color.bravo))
                        } else {
                            Glide.with(this).load(R.drawable.youcandoit).into(imageComplimentView)
                            complimentView.text = "Continue!"
                        }
                    },
                    { error ->
                        bindingError = ActivityErrorBinding.inflate(layoutInflater)
                        setContentView(bindingError.root)
                        val textView = findViewById<TextView>(R.id.error)

                        textView.text = error.networkResponse.statusCode.toString()
                    })
                queue.add(getAverage)

            },
            {
                //TODO Error Json UserProfile
            })




        queue.add(getBasicInfos)


    }
}