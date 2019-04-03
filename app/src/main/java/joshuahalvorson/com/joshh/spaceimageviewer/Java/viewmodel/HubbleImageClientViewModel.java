package joshuahalvorson.com.joshh.spaceimageviewer.Java.viewmodel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import joshuahalvorson.com.joshh.spaceimageviewer.Java.image.HubbleImage;
import joshuahalvorson.com.joshh.spaceimageviewer.Java.image.ImagePreview;
import joshuahalvorson.com.joshh.spaceimageviewer.Java.repository.HubbleImageRepository;

public class HubbleImageClientViewModel extends ViewModel {

    private static MutableLiveData<List<ImagePreview>> liveData;
    private static MutableLiveData<HubbleImage> hubbleImage;

    public static LiveData<List<ImagePreview>> getImagePreviews(int page){
        liveData = HubbleImageRepository.getImagePreviews(page);
        return liveData;
    }

    public static LiveData<HubbleImage> getImageData(int id){
        hubbleImage = HubbleImageRepository.getImageData(id);
        return hubbleImage;
    }
}
