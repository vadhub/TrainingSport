package com.vad.calk;

public class TableResult {
    private long date;

    private String lvlRes;
    private String statRes;
    private String numbRes;

    private int type;

    public TableResult(int type, long date){
        this.type = type;
        this.date = date;
    }

    public TableResult(int type, String lvlRes, String statRes, String numbRes){
        this.type = type;
        this.lvlRes = lvlRes;
        this.statRes = statRes;
        this.numbRes = numbRes;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getLvlRes() {
        return lvlRes;
    }

    public void setLvlRes(String lvlRes) {
        this.lvlRes = lvlRes;
    }

    public String getStatRes() {
        return statRes;
    }

    public void setStatRes(String statRes) {
        this.statRes = statRes;
    }

    public String getNumbRes() {
        return numbRes;
    }

    public void setNumbRes(String numbRes) {
        this.numbRes = numbRes;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
