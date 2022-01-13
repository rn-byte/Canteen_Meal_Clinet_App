package com.inim.canteenmeal;


public class noticeModelForRead {
    String nId,dateTime,nMsg;
    noticeModelForRead(){}

    noticeModelForRead(String id,String dateTime, String nMsg)
    {    this.nId=nId;
        this.dateTime=dateTime;
       // this.nTime=nTime;
        this.nMsg=nMsg;

    }

    public void setnId(String nId) {
        this.nId = nId;
    }

    public String getnId() {
        return nId;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setnMsg(String nMsg) {
        this.nMsg = nMsg;
    }

    public String getnMsg() {
        return nMsg;
    }
}

