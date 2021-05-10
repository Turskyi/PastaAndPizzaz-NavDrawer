package io.github.turskyi.pastaandpizzaz.ui.pizza

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.github.turskyi.pastaandpizzaz.R

internal class CaptionedImagesAdapter(
    private val captions: Array<String?>,
    private val imageIds: IntArray
) : RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder?>() {
    private var listener: Listener? = null

    internal interface Listener {
        fun onClick(position: Int)
    }

   override fun getItemCount()= captions.size

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val cardView: CardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_captioned_image, parent, false) as CardView
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView: CardView = holder.cardView
        val imageView = cardView.findViewById(R.id.info_image) as ImageView
        val drawable: Drawable? = ContextCompat.getDrawable(
            cardView.context,
            imageIds[position]
        )
        imageView.setImageDrawable(drawable)
        imageView.contentDescription = captions[position]
        val textView = cardView.findViewById(R.id.info_text) as TextView
        textView.text = captions[position]
        cardView.setOnClickListener {
            listener?.onClick(position)
        }
    }

    class ViewHolder(view: CardView) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view
    }
}
