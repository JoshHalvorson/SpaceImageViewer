package joshuahalvorson.com.joshh.spaceimageviewer.Kotlin.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import joshuahalvorson.com.joshh.spaceimageviewer.Kotlin.adapter.ImagePreviewListAdapter
import joshuahalvorson.com.joshh.spaceimageviewer.Kotlin.model.Model
import joshuahalvorson.com.joshh.spaceimageviewer.Kotlin.network.HubbleImageService
import joshuahalvorson.com.joshh.spaceimageviewer.R
import kotlinx.android.synthetic.main.activity_image_preview_list.*

class ImagePreviewList : AppCompatActivity() {

    private var disposable: Disposable? = null

    val imageList: ArrayList<Model.ImagePreview> = ArrayList()

    private lateinit var adapter: ImagePreviewListAdapter

    private val hubbleImageService by lazy {
        HubbleImageService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview_list)

        image_preview_list.layoutManager = LinearLayoutManager(this)
        adapter = ImagePreviewListAdapter(imageList, applicationContext)
        image_preview_list.adapter = adapter

        getImages(2)

    }

    private fun getImages(page: Int) {
        disposable = hubbleImageService.getAllImages(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            imageList.addAll(result)
                            adapter.notifyDataSetChanged()
                            Toast.makeText(applicationContext, imageList[1].name, Toast.LENGTH_LONG).show()
                        },
                        { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
                )
     }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
