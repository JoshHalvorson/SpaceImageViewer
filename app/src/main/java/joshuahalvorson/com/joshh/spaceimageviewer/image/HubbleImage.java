package joshuahalvorson.com.joshh.spaceimageviewer.image;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HubbleImage implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("credits")
    @Expose
    private String credits;
    @SerializedName("news_name")
    @Expose
    private String newsName;
    @SerializedName("mission")
    @Expose
    private String mission;
    @SerializedName("collection")
    @Expose
    private String collection;
    @SerializedName("image_files")
    @Expose
    private List<ImageFile> imageFiles = null;

    private String loadingImage;

    private String thumbnailImage;

    private String fullResImage;

    protected HubbleImage(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        credits = in.readString();
        newsName = in.readString();
        mission = in.readString();
        collection = in.readString();
        loadingImage = in.readString();
        thumbnailImage = in.readString();
        fullResImage = in.readString();
    }

    public static final Creator<HubbleImage> CREATOR = new Creator<HubbleImage>() {
        @Override
        public HubbleImage createFromParcel(Parcel in) {
            return new HubbleImage(in);
        }

        @Override
        public HubbleImage[] newArray(int size) {
            return new HubbleImage[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public List<ImageFile> getImageFiles() {
        return imageFiles;
    }

    public void setImageFiles(List<ImageFile> imageFiles) {
        this.imageFiles = imageFiles;
    }

    public String getLoadingImage() {
        return loadingImage;
    }

    public void setLoadingImage(String loadingImage) {
        this.loadingImage = loadingImage;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getFullResImage() {
        return fullResImage;
    }

    public void setFullResImage(String fullResImage) {
        this.fullResImage = fullResImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(credits);
        dest.writeString(newsName);
        dest.writeString(mission);
        dest.writeString(collection);
        dest.writeString(loadingImage);
        dest.writeString(thumbnailImage);
        dest.writeString(fullResImage);
    }
}
