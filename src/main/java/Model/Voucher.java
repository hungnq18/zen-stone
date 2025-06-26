package Model;

import java.sql.Date;

/**
 *
 * @author Le Tan Kim
 */
public class Voucher {

    private int id;
    private String name;
    private String code;
    private int type;
    private Float value;
    private Date start;
    private Date end;
    private int status;
    private float limit;
    private String used;

    public Voucher() {
    }
    public Voucher(int id, String name, String code,int type, Float value, Date start, Date end, int status, float limit, String used) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
        this.start = start;
        this.end = end;
        this.status = status;
        this.code = code;
        this.limit = limit;
        this.used = used;
    }
    public Voucher(int id, String name, String code,int type, Float value, Date start, Date end, int status, float limit) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
        this.start = start;
        this.end = end;
        this.status = status;
        this.code = code;
        this.limit = limit;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getLimit() {
        return limit;
    }

    public void setLimit(float limit) {
        this.limit = limit;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }
    

}
