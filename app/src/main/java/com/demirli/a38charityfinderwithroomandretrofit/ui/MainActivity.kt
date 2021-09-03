package com.demirli.a38charityfinderwithroomandretrofit.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.demirli.a38charityfinderwithroomandretrofit.R
import com.demirli.a38charityfinderwithroomandretrofit.model.Country
import com.demirli.a38charityfinderwithroomandretrofit.model.ResponseFromApi
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), SpinnerAdapter.OnUserSpinnerItemClickListener, ProjectsAdapter.OnProjectClickListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var countryList: ArrayList<Country>

    private lateinit var adapterSpinner: SpinnerAdapter

    private var organizationServeListForUserSelection = arrayListOf<Country>()
    private var organizationServeStringForUserSelection = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = MainViewModel()
        countryList = viewModel.generateCountries()

        setUi()

    }

    private fun setUi(){
        setAutoComplateTextAdapter()
        setSpinnerAdapter()

        search_btn.setOnClickListener {
            var countryIsoSelection = ""
            countryList.forEach {
                if(it.name == country_acTextView.text.toString()){
                    countryIsoSelection += "country:" + it.isoCode
                }
            }

            organizationServeListForUserSelection.forEach {
                countryIsoSelection += ",country:" + it.isoCode
            }


            var queryText = name_editText.text.toString()
            if(queryText == ""){
                queryText = "*"
            }

            viewModel.getProjects(queryText,countryIsoSelection)?.observe(this, Observer{
                setRecyclerAdapter(it)
            })

           closeKeyBoard()
        }

        clear_btn.setOnClickListener {
            organizationServeListForUserSelection.clear()
            organizationServeStringForUserSelection = ""
            countryList = viewModel.generateCountries()
            setSpinnerAdapter()
        }
    }

    private fun setAutoComplateTextAdapter(){
        val countries = arrayListOf<String>()

        var firstItemBlockFlag = 0
        countryList.forEach {
            if(firstItemBlockFlag > 0){
                countries.add(it.name)
            }
            firstItemBlockFlag ++
        }

        val adapterAutoComplateText = ArrayAdapter(this,
            R.layout.support_simple_spinner_dropdown_item, countries)
        country_acTextView.setAdapter(adapterAutoComplateText)
    }

    private fun setSpinnerAdapter(){
        adapterSpinner = SpinnerAdapter(this,countryList,this)
        organization_serves_spinner.adapter = adapterSpinner
    }

    override fun onUserSpinnerItemClick(country: Country) {
        organizationServeListForUserSelection.add(country)
        organizationServeStringForUserSelection += country.name + ", "
        countryList.set(0,
            Country(organizationServeStringForUserSelection, country.isoCode, true)
        )
        adapterSpinner.notifyDataSetChanged()
    }

    private fun setRecyclerAdapter(projectList: List<ResponseFromApi.ProjectResults>){
        projects_recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ProjectsAdapter(projectList,this)
        projects_recyclerView.adapter = adapter

    }

    override fun onProjectClick(projectResults: ResponseFromApi.ProjectResults) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(projectResults.projectLink)
        startActivity(intent)
    }

    private fun closeKeyBoard(){
        val view = currentFocus
        if(view != null){
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
