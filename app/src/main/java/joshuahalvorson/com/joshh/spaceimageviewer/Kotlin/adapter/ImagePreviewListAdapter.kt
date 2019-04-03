package joshuahalvorson.com.joshh.spaceimageviewer.Kotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import joshuahalvorson.com.joshh.spaceimageviewer.Kotlin.model.Model
import joshuahalvorson.com.joshh.spaceimageviewer.R
import kotlinx.android.synthetic.main.images_element_layout.view.*

class ImagePreviewListAdapter(val items : ArrayList<Model.ImagePreview>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.images_element_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.imageNameText?.text = items[position].name
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val imageNameText = view.image_name_text
}