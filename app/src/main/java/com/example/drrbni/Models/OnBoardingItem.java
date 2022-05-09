package com.example.drrbni.Models;

public class OnBoardingItem {

    int Title,Description,ScreenImg;

    public OnBoardingItem(int title, int description, int screenImg) {
        Title = title;
        Description = description;
        ScreenImg = screenImg;
    }


    public int getTitle() {
        return Title;
    }

    public void setTitle(int title) {
        Title = title;
    }

    public int getDescription() {
        return Description;
    }

    public void setDescription(int description) {
        Description = description;
    }

    public int getScreenImg() {
        return ScreenImg;
    }

    public void setScreenImg(int screenImg) {
        ScreenImg = screenImg;
    }
}
