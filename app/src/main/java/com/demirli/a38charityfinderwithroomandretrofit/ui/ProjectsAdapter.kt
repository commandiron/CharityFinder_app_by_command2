package com.demirli.a38charityfinderwithroomandretrofit.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.demirli.a38charityfinderwithroomandretrofit.R
import com.demirli.a38charityfinderwithroomandretrofit.model.ResponseFromApi
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class ProjectsAdapter(var projectList: List<ResponseFromApi.ProjectResults>, var onProjectClickListener: OnProjectClickListener): RecyclerView.Adapter<ProjectsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.project_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = projectList.size

    override fun onBindViewHolder(holder: ProjectsAdapter.ViewHolder, position: Int) {
        holder.projectId.text = projectList[position].id.toString()
        holder.projectTitle.text = projectList[position].title

        Picasso.get().load(projectList[position].imageLink).into(holder.projectImage)
        holder.projectImage.setOnClickListener {
            onProjectClickListener.onProjectClick(projectList[position])
        }

        holder.projectAddress.text = projectList[position].address
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val projectId = view.findViewById<TextView>(R.id.id_textView)
        val projectTitle = view.findViewById<TextView>(R.id.title_textView)
        val projectImage = view.findViewById<ImageView>(R.id.logo_imageView)
        val projectAddress = view.findViewById<TextView>(R.id.address_textView)
    }

    interface OnProjectClickListener{
        fun onProjectClick(projectResults :ResponseFromApi.ProjectResults)
    }


}