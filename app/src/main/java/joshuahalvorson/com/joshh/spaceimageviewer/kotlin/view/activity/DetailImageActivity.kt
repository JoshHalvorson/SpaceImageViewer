package joshuahalvorson.com.joshh.spaceimageviewer.kotlin.view.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import joshuahalvorson.com.joshh.spaceimageviewer.kotlin.model.Model
import joshuahalvorson.com.joshh.spaceimageviewer.kotlin.network.HubbleImageService
import joshuahalvorson.com.joshh.spaceimageviewer.R
import kotlinx.android.synthetic.main.activity_detail_image.*

class DetailImageActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    private val hubbleImageService by lazy {
        HubbleImageService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_image)

        val imagePreview =
                intent.getSerializableExtra("imagepreview") as? Model.ImagePreview

        if (imagePreview != null) {
            getImageDetails(imagePreview.id)
        }
    }

    private fun getImageDetails(id: Int) {
        disposable = hubbleImageService.getImageData(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            updateUi(result)
                        },
                        { error -> Toast.makeText(applicationContext, error.message, Toast.LENGTH_LONG).show()
                        Log.i("details", error.message)}
                )
    }

    private fun updateUi(image: Model.HubbleImage) {
        image_name.text = image.name
        image_desc.text = image.description

        Glide
                .with(this)
                .load(image.image_files?.get(1)?.file_url)
                .apply(RequestOptions().centerCrop())
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        Toast.makeText(applicationContext, e?.localizedMessage, Toast.LENGTH_LONG).show()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                })
                .thumbnail(.1f)
                .into(image_image_view)

        if (image.description != null) {
            var fixedDesc: String = image_desc.text.replace("<.*?>".toRegex(), "").replace("\\[.*?]".toRegex(), "")
            image_desc.text = fixedDesc
        } else {
            image_desc.text = getString(R.string.image_desc_null_text)
        }

        if (image.credits != null) {
            image_credits.text = image.credits
        } else {
            image_credits.text = getString(R.string.image_credits_null_text)
        }
    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
    }
}
