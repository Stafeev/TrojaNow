package com.example.artemstafeev.trojanow;

/**
 * Created by artemstafeev on 3/25/15.
 * Defines messaging functionality
 */
public class Message {
    boolean read; //true=read, false=unread
    String content;
    String sender;
    String receiver;
    public Message(boolean r, String c, String s, String rec)
    {
        read=r;
        content=c;
        sender=s;
        receiver=rec;
    }
    public void sendMessage()
    {
        //save record to database
    }
    public void deleteMessage()
    {
        //delete record from database
    }
    public void marAsRead()
    {
        read=true;
    }
    public void marAsUnread()
    {
        read=false;
    }
}
