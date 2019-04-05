package joshuahalvorson.com.joshh.spaceimageviewer.kotlin.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import joshuahalvorson.com.joshh.spaceimageviewer.kotlin.model.Model
import joshuahalvorson.com.joshh.spaceimageviewer.R
import joshuahalvorson.com.joshh.spaceimageviewer.kotlin.view.fragment.ImagePreviewListFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.images_element_layout.view.*

class ImagePreviewRecyclerViewAdapter(
        private val mValues: ArrayList<Model.ImagePreview>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<ImagePreviewRecyclerViewAdapter.ViewHolder>() {

    lateinit var context: Context

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Model.ImagePreview
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.images_element_layout, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.imageNameText.text = item.name
        when(item.collection){
            "news" -> holder.imageCollectionImageView.setImageDrawable(context.getDrawable(R.drawable.ic_noun_news_1726837))
            "holiday_cards" -> holder.imageCollectionImageView.setImageDrawable(context.getDrawable(R.drawable.ic_noun_greeting_card_1080415))
        }


        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val imageNameText = mView.image_name_text
        val imageCollectionImageView = mView.image_collection_icon

        override fun toString(): String {
            return super.toString() + " '" + imageNameText + "'"
        }
    }
}
