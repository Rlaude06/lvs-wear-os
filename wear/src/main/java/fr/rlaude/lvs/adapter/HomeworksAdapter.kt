package fr.rlaude.lvs.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import fr.rlaude.lvs.R
import fr.rlaude.lvs.activities.OneHomework
import fr.rlaude.lvs.data.Grades
import fr.rlaude.lvs.data.Homeworks


class HomeworksAdapter (private val context: Activity, private val arrayList: ArrayList<Homeworks>) : ArrayAdapter<Homeworks>(context, R.layout.list_homeworks, arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_homeworks, null)
        val titleView : TextView = view.findViewById(R.id.title_homework)
        val dateDueView : TextView = view.findViewById(R.id.date_due_homework)
        val subject : TextView = view.findViewById(R.id.subject)
        titleView.text = arrayList[position].title
        dateDueView.text = arrayList[position].dateDue
        subject.text = arrayList[position].subject
        val myContext = this.getContext()

        when (arrayList[position].subject){
            myContext.getString(R.string.math_code) -> { subject.text = myContext.getString(R.string.math_text); subject.setTextColor(context.getColor(R.color.math_color));}
            myContext.getString(R.string.hi_ge_code) -> { subject.text = myContext.getString(R.string.hi_ge_text); subject.setTextColor(context.getColor(R.color.hi_ge_color));}
            myContext.getString(R.string.ph_ch_code) -> { subject.text = myContext.getString(R.string.ph_ch_text); subject.setTextColor(context.getColor(R.color.ph_ch_color));}
            myContext.getString(R.string.ses_code) -> { subject.text = myContext.getString(R.string.ses_text); subject.setTextColor(context.getColor(R.color.ses_color));}
            myContext.getString(R.string.svt_code) -> { subject.text = myContext.getString(R.string.svt_text); subject.setTextColor(context.getColor(R.color.svt_color));}
            myContext.getString(R.string.snt_code) -> { subject.text = myContext.getString(R.string.snt_text); subject.setTextColor(context.getColor(R.color.snt_color));}
            myContext.getString(R.string.fra_code) -> { subject.text = myContext.getString(R.string.fra_text); subject.setTextColor(context.getColor(R.color.fra_color));}
            myContext.getString(R.string.lva_code) -> { subject.text = myContext.getString(R.string.lva_text); subject.setTextColor(context.getColor(R.color.lva_color));}
            myContext.getString(R.string.lvb_code) -> { subject.text = myContext.getString(R.string.lvb_text); subject.setTextColor(context.getColor(R.color.lvb_color));}
            myContext.getString(R.string.eps_code) -> { subject.text = myContext.getString(R.string.eps_text); subject.setTextColor(context.getColor(R.color.eps_color));}
        }
        if (arrayList[position].close){
            dateDueView.setTextColor(context.getColor(R.color.close_date_due))
        }


        return view
    }
}