package joshuahalvorson.com.joshh.spaceimageviewer;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String IMAGE_DATA_KEY = "image_data";
    private RecyclerView recyclerView;
    private HubbleImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        HubbleImageDetailsViewModel viewModel = ViewModelProviders.of(this).get(HubbleImageDetailsViewModel.class);
        viewModel.getImageList().observe(this, new Observer<List<HubbleImage>>() {
            @Override
            public void onChanged(@Nullable List<HubbleImage> images) {
                adapter = new HubbleImageAdapter(MainActivity.this, images, new HubbleImageAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(HubbleImage image) {
                        //Toast.makeText(MainActivity.this, "clicked on " + image.getDescription(), Toast.LENGTH_LONG).show();
                        Intent detailedImageIntent = new Intent(MainActivity.this, DetailedImageActvity.class);
                        detailedImageIntent.putExtra(IMAGE_DATA_KEY, image);
                        startActivity(detailedImageIntent);
                    }
                });
                recyclerView.setAdapter(adapter);
            }
        });
    }
}
