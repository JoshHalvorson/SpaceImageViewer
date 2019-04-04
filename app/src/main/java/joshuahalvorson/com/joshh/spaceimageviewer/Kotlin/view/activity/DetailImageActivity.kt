package joshuahalvorson.com.joshh.spaceimageviewer.Kotlin.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Contacts
import android.util.Log
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import joshuahalvorson.com.joshh.spaceimageviewer.Kotlin.model.Model
import joshuahalvorson.com.joshh.spaceimageviewer.Kotlin.network.HubbleImageService
import joshuahalvorson.com.joshh.spaceimageviewer.R

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
                            Toast.makeText(applicationContext, result.name, Toast.LENGTH_SHORT).show()
                        },
                        { error -> Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show() }
                )
    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
    }
}
