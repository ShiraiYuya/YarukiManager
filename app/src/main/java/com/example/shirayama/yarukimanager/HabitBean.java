package com.example.shirayama.yarukimanager;

/**
 * Created by shirayama on 2018/04/18.
 */

public class HabitBean {
    private Integer id = -1;
    private String title = "";
    private String declaration = "";
 /*   private boolean mon = false;
    private boolean tue = false;
    private boolean wed = false;
    private boolean thu = false;
    private boolean fri = false;
    private boolean sat = false;
    private boolean sun = false;*/
    private  boolean[] days = {false,false,false,false,false,false,false};
    private int norma = 0;
    private int stamp = 1;
    private int done_stamp = 0;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setNorma(Integer norma) {
        this.norma = norma;
    }

    public Integer getNorma() {
        return norma;
    }

    public void setStamp(Integer stamp) {
        this.stamp = stamp;
    }

    public Integer getStamp() {
        return stamp;
    }

    public void setDone_Stamp(Integer stamp) {
        this.done_stamp = stamp;
    }

    public Integer getDone_Stamp() {
        return done_stamp;
    }

    public void setDays(boolean[] days) {
        this.days = days;
    }

    public boolean[] getDays() {return days;}
}

