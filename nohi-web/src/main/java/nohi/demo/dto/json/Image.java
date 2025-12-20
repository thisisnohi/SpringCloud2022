package nohi.demo.dto.json;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2023/07/08 15:05
 **/

public class Image implements java.io.Serializable {
    private int height;
    private Size size;
    private String title;
    private String uri;
    private int width;

    public Image() {
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public String getUri() {
        return uri;
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Size getSize() {
        return size;
    }
}
