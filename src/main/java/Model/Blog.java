/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Model;

import java.sql.Timestamp;

/**
 *
 * @author HP
 */
public class Blog {
    private int ID;
    private String title;
    private String shortDesc;
    private String description;
    private String image;
    private Timestamp datePost;
    private Timestamp dateUpdate;
    private int status;
    private int view;
    private int categoryID;

    public Blog() {
    }

    public Blog(int ID, String title, String shortDesc, String description, String image, Timestamp datePost, Timestamp dateUpdate, int status, int view, int categoryID) {
        this.ID = ID;
        this.title = title;
        this.shortDesc = shortDesc;
        this.description = description;
        this.image = image;
        this.datePost = datePost;
        this.dateUpdate = dateUpdate;
        this.status = status;
        this.view = view;
        this.categoryID = categoryID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    
    
}
