package com.example.joshh.spaceimageviewer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final int MAX_INDEX = 4277;
    public static final String HTTP_HUBBLESITE_ORG = "http://hubblesite.org/";
    public static final int MAX_BYTE_SIZE = 112560000;
    private ImageView imageView;
    private TextView imageDesc, imageCredits, imageName;
    private ProgressBar progressBar;
    private Bitmap bitmap;
    private String[] urls;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        imageView = findViewById(R.id.image_view);
        imageDesc = findViewById(R.id.image_desc);
        imageName = findViewById(R.id.image_name);
        progressBar = findViewById(R.id.progress_bar);
        imageCredits = findViewById(R.id.image_credits);

        findViewById(R.id.new_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap(null);
                progressBar.setVisibility(View.VISIBLE);
                int id = (int)(Math.random() * ((MAX_INDEX - 1) + 1)) + 1;
                getNewImage(id);
                Log.i("imageid", Integer.toString(id));
            }
        });
        progressBar.setVisibility(View.VISIBLE);
        getNewImage((int)(Math.random() * ((MAX_INDEX - 1) + 1)) + 1);
    }

    private void getNewImage(int id){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(HTTP_HUBBLESITE_ORG)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        SpaceTelescopeClient spaceTelescopeClient = retrofit.create(SpaceTelescopeClient.class);
        Call<SpaceTelescopeImage> call = spaceTelescopeClient.getImageData(id);
        call.enqueue(new Callback<SpaceTelescopeImage>() {
            @Override
            public void onResponse(Call<SpaceTelescopeImage> call, Response<SpaceTelescopeImage> response) {
                final SpaceTelescopeImage image = response.body();
                if (image != null) {
                    List<ImageFile> imageUrls = image.getImageFiles();
                    urls = new String[imageUrls.size()];
                    for(int i = 0; i < imageUrls.size(); i++){
                        String url = imageUrls.get(i).getFileUrl();
                        if(url.substring(url.length() - 4, url.length()).equals(".jpg")){
                            urls[i] = url;
                        }
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            bitmap = NetworkAdapter.httpImageRequest(urls[urls.length - 1]);
                            if(bitmap == null){
                                bitmap = NetworkAdapter.httpImageRequest(urls[urls.length - 2]);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(bitmap != null){
                                        if(bitmap.getByteCount() < MAX_BYTE_SIZE){
                                            progressBar.setVisibility(View.GONE);
                                            imageView.setImageBitmap(bitmap);
                                        }
                                    }
                                }
                            });
                        }
                    }).start();

                    imageDesc.setText(image.getDescription());
                    if(imageDesc.getText().toString().equals("")){
                        imageDesc.setText(getString(R.string.image_desc_default_text));
                    }
                    imageName.setText(image.getName());
                    imageCredits.setText(image.getCredits());
                }
            }

            @Override
            public void onFailure(Call<SpaceTelescopeImage> call, Throwable t) {
                Toast.makeText(context, "Error getting image", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
