package joshuahalvorson.com.joshh.spaceimageviewer.viewmodel;

import android.media.Image;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import joshuahalvorson.com.joshh.spaceimageviewer.image.HubbleImage;
import joshuahalvorson.com.joshh.spaceimageviewer.image.ImagePreview;
import joshuahalvorson.com.joshh.spaceimageviewer.repository.HubbleImageRepository;

public class HubbleImageClientViewModel extends ViewModel {

    private static MutableLiveData<List<ImagePreview>> liveData;

    public static LiveData<List<ImagePreview>> getImagePreviews(int page){
        liveData = HubbleImageRepository.getImagePreviews(page);
        return liveData;
    }

    public static MutableLiveData<HubbleImage> getImageData(int id){
        return HubbleImageRepository.getImageData(id);
    }
}
