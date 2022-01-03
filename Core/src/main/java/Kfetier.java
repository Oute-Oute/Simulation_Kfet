package main.java;


import kfet.CoreController;

public class Kfetier {
    private int id;
    private String type;
    private Boolean isFree;

    public Kfetier(int id, String type) {
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
            CoreController.getInstance().free(this.id);
        } else {
            CoreController.getInstance().notFree(this.id);
        }
    }
}