package com.textapp3.reddittest.ui.epoxy

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.squareup.picasso.Picasso
import com.textapp3.reddittest.R

@EpoxyModelClass(layout = R.layout.item_post)
abstract class PostItemView : EpoxyModelWithHolder<PostItemView.Holder>() {

    @EpoxyAttribute
    lateinit var title: String
    @EpoxyAttribute
    lateinit var author: String
    @EpoxyAttribute
    lateinit var imageUrl: String

    override fun bind(holder: Holder) {
        holder.titleView.text = title
        holder.authorView.text = author
        Picasso.with(holder.imageView.context)
            .load(imageUrl)
            .error(R.drawable.ic_image_black_24dp)
            .into(holder.imageView)
    }

    inner class Holder : EpoxyHolder() {
        lateinit var titleView: TextView
        lateinit var authorView: TextView
        lateinit var imageView: ImageView

        override fun bindView(itemView: View) {
            titleView = itemView.findViewById<TextView>(R.id.post_title)
            authorView = itemView.findViewById<TextView>(R.id.post_author)
            imageView = itemView.findViewById<ImageView>(R.id.imageView)
        }
    }
}

