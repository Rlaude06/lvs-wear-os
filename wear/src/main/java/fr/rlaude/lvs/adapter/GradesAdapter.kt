package fr.rlaude.lvs.adapter

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import fr.rlaude.lvs.R
import fr.rlaude.lvs.data.Grades


class GradesAdapter (private val context: Activity, private val arrayList: ArrayList<Grades>) : ArrayAdapter<Grades>(context, R.layout.list_grades, arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_grades, null)
        val moy : TextView = view.findViewById(R.id.moy)
        val mat : TextView = view.findViewById(R.id.mat)
        moy.text = arrayList[position].moy.toString()
        if (arrayList[position].moy > 16){
            moy.setTextColor(Color.parseColor("#D4AF37"))
            moy.text = arrayList[position].moy.toString()+"🥕"
        } else if (arrayList[position].moy > 14){
            moy.setTextColor(Color.parseColor("#9ACD32"))
        }

        mat.text = arrayList[position].mat

        return view
        }
}