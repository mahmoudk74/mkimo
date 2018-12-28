package ashraf.example.com.communication;

/**
 * Created by adeenapk on 4/12/2018.
 */

public class DashboarViewModel {
    private String image;
    private String uri;
    private long time;
    private String title;
    private String post_id;
    private String job;

    public DashboarViewModel(String image, String uri, long time, String title, String post_id, String job) {
        this.image = image;
        this.uri = uri;
        this.time = time;
        this.title = title;
        this.post_id = post_id;
        this.job = job;
    }

    public
    String getJob() {
        return job;
    }

    public
    void setJob(String job) {
        this.job = job;
    }

    public DashboarViewModel(){}

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}