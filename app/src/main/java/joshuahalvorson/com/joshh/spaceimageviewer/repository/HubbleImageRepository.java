package joshuahalvorson.com.joshh.spaceimageviewer.repository;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import joshuahalvorson.com.joshh.spaceimageviewer.image.HubbleImage;
import joshuahalvorson.com.joshh.spaceimageviewer.image.ImagePreview;
import joshuahalvorson.com.joshh.spaceimageviewer.network.HubbleImageClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HubbleImageRepository {
    private static final String TAG = "HubbleImageRepository";

    static List<ImagePreview> imagePreviews;
    static HubbleImage hubbleImage;

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HubbleImageClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static HubbleImageClient client = retrofit.create(HubbleImageClient.class);


    public static List<ImagePreview> getImagePreviews(int page){
        imagePreviews = new ArrayList<>();
        Call<List<ImagePreview>> call = client.getAllImages(page);
        call.enqueue(new Callback<List<ImagePreview>>() {
            @Override
            public void onResponse(Call<List<ImagePreview>> call, Response<List<ImagePreview>> response) {
                imagePreviews = response.body();
                Log.i(TAG, Integer.toString(response.body().size()));
                Log.i(TAG, Integer.toString(imagePreviews.size()));
            }

            @Override
            public void onFailure(Call<List<ImagePreview>> call, Throwable t) {
                Log.i(TAG, t.getLocalizedMessage());
            }
        });
        Log.i(TAG, Integer.toString(imagePreviews.size()));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return imagePreviews;
    }

    public static HubbleImage getImageData(int id){
        Call<HubbleImage> call = client.getImageData(id);
        call.enqueue(new Callback<HubbleImage>() {
            @Override
            public void onResponse(Call<HubbleImage> call, Response<HubbleImage> response) {
                hubbleImage = response.body();
            }

            @Override
            public void onFailure(Call<HubbleImage> call, Throwable t) {
                Log.i(TAG, t.getLocalizedMessage());
            }
        });
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return hubbleImage;
    }
}
