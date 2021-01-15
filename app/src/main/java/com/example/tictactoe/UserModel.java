package com.example.tictactoe;

import java.io.Serializable;

public class UserModel implements Serializable {
    private int UserId;
    private String Username;
    private String EmailAddress;
    private int EasyWin;
    private int EasyDraw;
    private int EasyLose;
    private int HardWin;
    private int HardDraw;
    private int HardLose;
    private int FriendWin;
    private int FriendDraw;
    private int FriendLose;

    public UserModel(int userId, String username, String emailAddress) {
        this.UserId = userId;
        this.Username = username;
        this.EmailAddress = emailAddress;
        this.setEasyWin(0);
        this.setEasyDraw(0);
        this.setEasyLose(0);
        this.setHardWin(0);
        this.setHardDraw(0);
        this.setHardLose(0);
        this.setFriendWin(0);
        this.setFriendDraw(0);
        this.setHardLose(0);
    }

    public UserModel(){

    }

    public int getUserId() {
        return UserId;
    }

    public String getUsername() {
        return Username;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public int getEasyWin() {
        return EasyWin;
    }

    public int getEasyDraw() {
        return EasyDraw;
    }

    public int getEasyLose() {
        return EasyLose;
    }

    public int getHardWin() {
        return HardWin;
    }

    public int getHardDraw() {
        return HardDraw;
    }

    public int getHardLose() {
        return HardLose;
    }

    public int getFriendWin() {
        return FriendWin;
    }

    public int getFriendDraw() {
        return FriendDraw;
    }

    public int getFriendLose() {
        return FriendLose;
    }

    public void setUserId(int userId){
        UserId = userId;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setEmailAddress(String emailAddress){
        EmailAddress = emailAddress;
    }

    public void setEasyWin(int easyWin) {
        EasyWin = easyWin;
    }

    public void setEasyDraw(int easyDraw) {
        EasyDraw = easyDraw;
    }

    public void setEasyLose(int easyLose) {
        EasyLose = easyLose;
    }

    public void setHardWin(int hardWin) {
        HardWin = hardWin;
    }

    public void setHardDraw(int hardDraw) {
        HardDraw = hardDraw;
    }

    public void setHardLose(int hardLose) {
        HardLose = hardLose;
    }

    public void setFriendWin(int friendWin) {
        FriendWin = friendWin;
    }

    public void setFriendDraw(int friendDraw) {
        FriendDraw = friendDraw;
    }

    public void setFriendLose(int friendLose) {
        FriendLose = friendLose;
    }
}
