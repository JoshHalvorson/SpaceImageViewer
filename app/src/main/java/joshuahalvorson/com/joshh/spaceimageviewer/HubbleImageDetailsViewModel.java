package joshuahalvorson.com.joshh.spaceimageviewer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HubbleImageDetailsViewModel extends ViewModel {

    private MutableLiveData<List<HubbleImage>> imageList;

    public LiveData<List<HubbleImage>> getImageList(){
        if(imageList == null){
            imageList = new MutableLiveData<>();
            imageList = loadHubbleImages();
        }
        return imageList;
    }

    public MutableLiveData<List<HubbleImage>> loadHubbleImages() {
        return HubbleImageDetailsRepository.loadHubbleImages();
    }
}
