package joshuahalvorson.com.joshh.spaceimageviewer;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.media.Image;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HubbleImageDetailsViewModel extends ViewModel {

    private MutableLiveData<List<HubbleImage>> imageList;
    private List<HubbleImage> imagesList;
    private HubbleImage imageData;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HubbleImageClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    HubbleImageClient api = retrofit.create(HubbleImageClient.class);

    public LiveData<List<HubbleImage>> getImageList(){
        if(imageList == null){
            imageList = new MutableLiveData<>();
            loadHubbleImages();
        }
        return imageList;
    }

    public void loadSingleImageData(List<HubbleImage> list){
        for(int i = 0; i < list.size(); i++) {
            final HubbleImage singleImage = list.get(i);
            Log.i("imageid", Integer.toString(singleImage.getId()));
            Call<HubbleImage> imageDataCall = api.getImageData(singleImage.getId());
            imageDataCall.enqueue(new Callback<HubbleImage>() {
                @Override
                public void onResponse(Call<HubbleImage> call, Response<HubbleImage> response) {
                    imageData = response.body();
                    List<ImageFile> imgUrls = imageData.getImageFiles();
                    String urls = "";
                    StringBuilder sb = new StringBuilder();
                    if (imgUrls != null) {
                        for (int i = 0; i < imgUrls.size(); i++) {
                            String url = imgUrls.get(i).getFileUrl();
                            if (url.contains(".jpg") || url.contains(".png")) {
                                sb.append(url).append(",");
                            }
                        }
                        urls = sb.toString();
                        urls = urls.replaceAll(", $", "");
                        String[] validUrls = urls.split(",");
                        singleImage.setThumbnailImage(validUrls[0]);
                        singleImage.setFullResImage(validUrls[validUrls.length - 1]);
                    }
                }

                @Override
                public void onFailure(Call<HubbleImage> call, Throwable t) {

                }
            });
        }
    }

    public void loadHubbleImages() {
        Call<List<HubbleImage>> call = api.getAllImages();
        call.enqueue(new Callback<List<HubbleImage>>() {
            @Override
            public void onResponse(Call<List<HubbleImage>> call, Response<List<HubbleImage>> response) {
                imagesList = response.body();
                loadSingleImageData(imagesList);
                imageList.setValue(imagesList);
            }

            @Override
            public void onFailure(Call<List<HubbleImage>> call, Throwable t) {

            }
        });
    }
}
