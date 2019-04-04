package joshuahalvorson.com.joshh.spaceimageviewer.Kotlin.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import joshuahalvorson.com.joshh.spaceimageviewer.Java.adapter.ViewPagerAdapter
import joshuahalvorson.com.joshh.spaceimageviewer.Kotlin.model.Model
import joshuahalvorson.com.joshh.spaceimageviewer.Kotlin.view.fragment.ImagePreviewListFragment
import joshuahalvorson.com.joshh.spaceimageviewer.R

class MainActivity : AppCompatActivity(), ImagePreviewListFragment.OnListFragmentInteractionListener{
    override fun onListFragmentInteraction(item: Model.ImagePreview?) {
        val intent = Intent(this, DetailImageActivity::class.java)
        intent.putExtra("imagepreview", item)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview_list)

        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(ImagePreviewListFragment(), "Hubble Pictures")

        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

}
