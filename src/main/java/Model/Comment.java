package Model;

import Util.ClearHTML;
import java.sql.Timestamp;

/**
 *
 * @author Le Tan Kim
 */
public class Comment {

    private int id;
    private int userID;
    private int productID;
    private String comment;
    private int status;
    private Timestamp datePost;
    private Timestamp dateUpdate;

    public Comment() {
    }

    public Comment(int id, int userID, int productID, String comment, int status, Timestamp datePost, Timestamp dateUpdate) {
        ClearHTML clearHtml = new ClearHTML();
        this.id = id;
        this.userID = userID;
        this.productID = productID;
        this.comment = clearHtml.clearHtml(comment);
        this.status = status;
        this.datePost = datePost;
        this.dateUpdate = dateUpdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
