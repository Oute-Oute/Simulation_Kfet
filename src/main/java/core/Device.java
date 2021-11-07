package core;

import com.kfet.core.CoreController;

public class Device extends CoreController {
    private int id;
    private String type;
    private Boolean isFree;

    public Device(int id, String type) {
        this.id = id;
        this.type = type;
        isFree = true;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getFree() {
        return isFree;
    }

    public void setFree(Boolean free) {
        isFree = free;
        if(free){
            free(this.id);
        } else {
            notFree(this.id);
        }
    }
}
