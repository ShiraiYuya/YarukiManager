package com.example.shirayama.yarukimanager;

import java.util.Calendar;

/**
 * Created by shirayama on 2018/04/18.
 */

public class TaskBean {
    private Integer id = -1;
    private String title = "";
    private String declaration = "";
    private int step = 1;
    private String unit = "";
    private int stamp = 1;
    private Calendar duedate;
    private Calendar createddate;

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

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getStep() {
        return step;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setStamp(Integer stamp) {
        this.stamp = stamp;
    }

    public Integer getStamp() {
        return stamp;
    }

    public void setDueDate(Calendar duedate) {
        this.duedate = duedate;
    }

    public Calendar getDueDate() {return duedate;}

    public void setCreatedDate(Calendar createddate) {
        this.createddate = createddate;
    }

    public Calendar getCreatedDate() {return createddate;}


}

