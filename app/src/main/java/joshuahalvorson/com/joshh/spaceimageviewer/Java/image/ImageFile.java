package joshuahalvorson.com.joshh.spaceimageviewer.Java.image;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageFile {

    @SerializedName("file_url")
    @Expose
    private String fileUrl;
    @SerializedName("file_size")
    @Expose
    private int fileSize;
    @SerializedName("width")
    @Expose
    private int width;
    @SerializedName("height")
    @Expose
    private int height;

    public String getFileUrl() {
        return fileUrl;
    }

    public int getFileSize() {
        return fileSize;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
