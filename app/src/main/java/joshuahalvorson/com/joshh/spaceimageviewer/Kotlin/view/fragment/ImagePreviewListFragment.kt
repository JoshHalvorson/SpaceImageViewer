package joshuahalvorson.com.joshh.spaceimageviewer.Kotlin.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import joshuahalvorson.com.joshh.spaceimageviewer.Kotlin.adapter.ImagePreviewRecyclerViewAdapter
import joshuahalvorson.com.joshh.spaceimageviewer.Kotlin.model.Model
import joshuahalvorson.com.joshh.spaceimageviewer.Kotlin.network.HubbleImageService
import joshuahalvorson.com.joshh.spaceimageviewer.R
import kotlinx.android.synthetic.main.fragment_image_preview_list.*

class ImagePreviewListFragment : Fragment() {

    private var disposable: Disposable? = null

    val imageList: ArrayList<Model.ImagePreview> = ArrayList()

    private lateinit var adapter: ImagePreviewRecyclerViewAdapter

    private val hubbleImageService by lazy {
        HubbleImageService.create()
    }

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image_preview_list, container, false)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val linearLayoutManager = LinearLayoutManager(context)
        adapter = ImagePreviewRecyclerViewAdapter(imageList, listener)
        image_preview_list.layoutManager = linearLayoutManager
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
                        },
                        { error -> Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show() }
                )
    }

    override fun onDetach() {
        super.onDetach()
        disposable?.dispose()
        listener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: Model.ImagePreview?)
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
                ImagePreviewListFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
