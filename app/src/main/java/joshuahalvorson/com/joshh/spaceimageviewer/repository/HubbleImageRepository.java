package joshuahalvorson.com.joshh.spaceimageviewer.repository;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
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

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HubbleImageClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static HubbleImageClient client = retrofit.create(HubbleImageClient.class);


    public static MutableLiveData<List<ImagePreview>> getImagePreviews(int page){
        final MutableLiveData<List<ImagePreview>> imagePreviews = new MutableLiveData<>();
        Call<List<ImagePreview>> call = client.getAllImages(page);
        call.enqueue(new Callback<List<ImagePreview>>() {
            @Override
            public void onResponse(Call<List<ImagePreview>> call, Response<List<ImagePreview>> response) {
                imagePreviews.setValue(response.body());
                Log.i(TAG, Integer.toString(response.body().size()));
            }

            @Override
            public void onFailure(Call<List<ImagePreview>> call, Throwable t) {
                Log.i(TAG, t.getLocalizedMessage());
            }
        });
        return imagePreviews;
    }

    public static MutableLiveData<HubbleImage> getImageData(int id){
        final MutableLiveData<HubbleImage> hubbleImage = new MutableLiveData<>();
        Call<HubbleImage> call = client.getImageData(id);
        call.enqueue(new Callback<HubbleImage>() {
            @Override
            public void onResponse(Call<HubbleImage> call, Response<HubbleImage> response) {
                hubbleImage.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HubbleImage> call, Throwable t) {
                Log.i(TAG, t.getLocalizedMessage());
            }
        });
        return hubbleImage;
    }
}
