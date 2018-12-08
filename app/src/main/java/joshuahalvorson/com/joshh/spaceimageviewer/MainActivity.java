package joshuahalvorson.com.joshh.spaceimageviewer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
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
                        Toast.makeText(MainActivity.this, "clicked on " + image.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
                recyclerView.setAdapter(adapter);
            }
        });
    }
}
