package nohi.demo.dto.json;

import java.util.List;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>MediaContent</p>
 * @date 2023/07/08 15:08
 **/
public class MediaContent implements java.io.Serializable {
    public Media media;
    public List<Image> images;

    public MediaContent() {
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Media getMedia() {
        return media;
    }

    public List<Image> getImages() {
        return images;
    }
}
