package com.example.drrbni.OnboardingPackage;

public class OnBoardingItem {

    int Title, SubTitle,ScreenImg;

    public OnBoardingItem(int title, int subTitle, int screenImg) {
        Title = title;
        SubTitle = subTitle;
        ScreenImg = screenImg;
    }


    public int getTitle() {
        return Title;
    }

    public void setTitle(int title) {
        Title = title;
    }

    public int getSubTitle() {
        return SubTitle;
    }

    public void setSubTitle(int subTitle) {
        SubTitle = subTitle;
    }

    public int getScreenImg() {
        return ScreenImg;
    }

    public void setScreenImg(int screenImg) {
        ScreenImg = screenImg;
    }
}