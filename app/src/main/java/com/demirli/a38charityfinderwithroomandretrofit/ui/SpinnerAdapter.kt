package com.demirli.a38charityfinderwithroomandretrofit.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import com.demirli.a38charityfinderwithroomandretrofit.R
import com.demirli.a38charityfinderwithroomandretrofit.model.Country

class SpinnerAdapter(var context: Context, var countries: List<Country>, var onUserItemClickListener: OnUserSpinnerItemClickListener): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder

        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
            viewHolder =
                ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var country: Country = getItem(position) as Country
        viewHolder.countryTv.text = country.name

        viewHolder.countryCheckBox.setOnCheckedChangeListener(null)
        viewHolder.countryCheckBox.isChecked = country.isSelected

        viewHolder.countryCheckBox.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                countries[position].isSelected = isChecked
                onUserItemClickListener.onUserSpinnerItemClick(countries[position])
            }
        })

        return view as View
    }

    override fun getItem(position: Int): Any = countries[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = countries.size

    private class ViewHolder(view: View){
        var countryTv: TextView
        var countryCheckBox: CheckBox

        init {
            countryTv = view.findViewById(R.id.country_textView)
            countryCheckBox = view.findViewById(R.id.country_checkBox)
        }

    }

    interface OnUserSpinnerItemClickListener {
        fun onUserSpinnerItemClick(country: Country)
    }
}