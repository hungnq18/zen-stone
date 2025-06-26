package Model;

import java.sql.Timestamp;

/**
 *
 * @author Le Tan Kim
 */
public class BannerText {

    private int ID;
    private String img;
    private String title;
    private String description;
    private int status;
    private Timestamp datePost;
    private Timestamp dateUpdate;

    public BannerText() {
    }

    public BannerText(int ID, String img, String title, String description, int status, Timestamp datePost, Timestamp dateUpdate) {
        this.ID = ID;
        this.img = img;
        this.title = title;
        this.description = description;
        this.status = status;
        this.datePost = datePost;
        this.dateUpdate = dateUpdate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getDatePost() {
        return datePost;
    }

    public void setDatePost(Timestamp datePost) {
        this.datePost = datePost;
    }

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

}
