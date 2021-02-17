package com.project.farmingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.project.farmingapp.R
import com.project.farmingapp.model.data.WeatherList
import com.project.farmingapp.utilities.CellClickListener
import org.kodein.di.android.androidCoreContextTranslators
import kotlin.math.round

class ArticleListAdapter(val context: Context, val articleListData: List<DocumentSnapshot>, private val cellClickListener: CellClickListener): RecyclerView.Adapter<ArticleListAdapter.ArticleListViewholder>() {
    class ArticleListViewholder(itemView: View): RecyclerView.ViewHolder(itemView){
        var articleName = itemView.findViewById<TextView>(R.id.descTextxArticleListFrag)
        var articleImage = itemView.findViewById<ImageView>(R.id.imageArticleListFrag)
        var articleCard = itemView.findViewById<CardView>(R.id.articleListCardArtListFrag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListViewholder {
        val view = LayoutInflater.from(context).inflate(R.layout.article_list_single, parent, false)
        return ArticleListAdapter.ArticleListViewholder(view)
    }

    override fun getItemCount(): Int {
        return articleListData.size
    }

    override fun onBindViewHolder(holder: ArticleListViewholder, position: Int) {
        val singleArticle = articleListData[position]

        holder.articleName.text = singleArticle.data!!.get("title").toString()
        holder.articleCard.setOnClickListener {
            cellClickListener.onCellClickListener(singleArticle.data!!.get("title").toString())
        }
        var list : List<String> = singleArticle.data!!.get("images") as List<String>

        Glide.with(holder.itemView.context)
            .load(list[0])
//            .apply(RequestOptions.bitmapTransform(
//                RoundedCorners(20)
//            ))
            .into(holder.articleImage)

    }
}