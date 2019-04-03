package joshuahalvorson.com.joshh.spaceimageviewer.Kotlin.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import joshuahalvorson.com.joshh.spaceimageviewer.Kotlin.network.HubbleImageService
import joshuahalvorson.com.joshh.spaceimageviewer.R
import kotlinx.android.synthetic.main.activity_image_preview_list.*

class ImagePreviewList : AppCompatActivity() {

    private var disposable: Disposable? = null

    private val hubbleImageService by lazy {
        HubbleImageService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview_list)
        getImages(2)
    }

    private fun getImages(page: Int) {
        disposable = hubbleImageService.getAllImages(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> results_text.text = result[2].name },
                        { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
                )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
