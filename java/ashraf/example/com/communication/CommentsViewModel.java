package ashraf.example.com.communication;

/**
 * Created by Mostafa on 4/14/2018.
 */

public class CommentsViewModel {
    private String uid;
    private String title;
    private long time;

    public CommentsViewModel(){}

    public CommentsViewModel(String uid, String title, long time) {
        this.uid = uid;
        this.title = title;
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
