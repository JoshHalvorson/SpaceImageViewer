package joshuahalvorson.com.joshh.spaceimageviewer;

import android.util.Log;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HubbleImageDetailsRepository {

    private static MutableLiveData<List<HubbleImage>> imageList;
    private static List<HubbleImage> imagesList;
    private static HubbleImage imageData;

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HubbleImageClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static HubbleImageClient api = retrofit.create(HubbleImageClient.class);

    public static MutableLiveData<List<HubbleImage>> loadHubbleImages() {
        Call<List<HubbleImage>> call = api.getAllImages();
        imageList = new MutableLiveData<>();
        call.enqueue(new Callback<List<HubbleImage>>() {
            @Override
            public void onResponse(Call<List<HubbleImage>> call, Response<List<HubbleImage>> response) {
                imagesList = response.body();
                if (imagesList != null) {
                    loadSingleImageData(imagesList);
                }
                imageList.setValue(imagesList);
            }

            @Override
            public void onFailure(Call<List<HubbleImage>> call, Throwable t) {

            }
        });
        return imageList;
    }

    public static void loadSingleImageData(List<HubbleImage> list){
        for(int i = 0; i < list.size(); i++) {
            final HubbleImage singleImage = list.get(i);
            Log.i("imageid", Integer.toString(singleImage.getId()));
            Call<HubbleImage> imageDataCall = api.getImageData(singleImage.getId());
            imageDataCall.enqueue(new Callback<HubbleImage>() {
                @Override
                public void onResponse(Call<HubbleImage> call, Response<HubbleImage> response) {
                    imageData = response.body();
                    List<ImageFile> imgUrls = imageData.getImageFiles();
                    StringBuilder sb = new StringBuilder();
                    if (imgUrls != null) {
                        for (int i = 0; i < imgUrls.size(); i++) {
                            String url = imgUrls.get(i).getFileUrl();
                            if (url.contains(".jpg") || url.contains(".png")) {
                                //Log.i("imagesize", Integer.toString(imgUrls.get(i).getFileSize()) + " - url: " + imgUrls.get(i).getFileUrl());
                                sb.append(url).append(",");
                            }
                        }
                        String[] validUrls = sb.toString().replaceAll(", $", "").split(",");
                        singleImage.setThumbnailImage(validUrls[0]);
                        singleImage.setLoadingImage(validUrls[0]);
                        singleImage.setFullResImage(validUrls[validUrls.length - 1]);
                        singleImage.setDescription(imageData.getDescription());
                        singleImage.setCredits(imageData.getCredits());
                        singleImage.setCredits(imageData.getCredits());
                        singleImage.setMission(imageData.getMission());
                        //Log.i("imageData", "loadingImage: " + singleImage.getLoadingImage() + " thumbnailImage: " + singleImage.getThumbnailImage() + " fullresImage: " + singleImage.getFullResImage());
                    }
                }

                @Override
                public void onFailure(Call<HubbleImage> call, Throwable t) {

                }
            });
        }
    }
}
