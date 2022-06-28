package com.example.drrbni;

import java.util.HashMap;

public class SpinnerPosition {

    private HashMap<String , Integer> major ;
    private HashMap<String , Integer> category ;
    private HashMap<String , Integer> governorate ;
    private HashMap<String , Integer> college ;
    private static SpinnerPosition instance;

    public static SpinnerPosition getInstance(){
        if (instance == null){
            instance = new SpinnerPosition();
        }
        return instance;
    }


    private SpinnerPosition() {
        this.major = new HashMap<>();
        this.category = new HashMap<>();
        this.governorate = new HashMap<>();
        this.college = new HashMap<>();

        setMajor();
        setGovernorate();
        setCategory();
        setCollege();
    }

    public void setMajor(){
        major.put("تكنولوجيا الوسائط المتعددة" , 1);
        major.put("البرمجيات وقواعد البيانات" , 2);
        major.put("تصميم وتطوير مواقع الويب" , 3);
        major.put("أمن المعلومات السيبراني" , 4);
        major.put("علم البيانات والذكاء الإصطناعي" , 5);
        major.put("شبكات الحاسوب والإنترنت" , 6);
        major.put("الهندسة المعمارية" , 7);
        major.put("الهندسة المدنية" , 8);
        major.put("هندسة التشييد وإدارة المشاريع" , 9);
        major.put("هندسة المساحة" , 10);
        major.put("نظم المعلومات الجغرافية" , 11);
        major.put("المحاسبة" , 12);
        major.put("إداراة أتمتة المكاتب" , 13);
        major.put("التسويق الإلكتروني" , 14);
        major.put("الإعلام الرقمي" , 15);
        major.put("العلاقات العامة والإعلان" , 16);
    }

    public int getMajorPosition(String major){

        return  this.major.get(major);
    }

    public void setGovernorate(){
        governorate.put("شمال غزة" , 1);
        governorate.put("غزة" , 2);
        governorate.put("الوسطى" , 3);
        governorate.put("خانيونس" , 4);
        governorate.put("رفح" , 5);
    }

    public int getGovernoratePosition(String governorate){

        return  this.governorate.get(governorate);
    }


    public void setCategory(){
        category.put("تكنولوجيا المعلومات" , 1);
        category.put("المحاسبة" , 2);
        category.put("الصحافة والاعلام" , 3);
        category.put("الهندسة" , 4);
        category.put("التصميم والديكور" , 5);
        category.put("التسويق" , 6);
        category.put("البلديات" , 7);
    }


    public int getCategoryPosition(String category){

        return  this.category.get(category);
    }

    public void setCollege(){
        college.put("الجامعة الإسلامية" , 1);
        college.put("جامعة الأقصى" , 2);
        college.put("جامعة فلسطين" , 3);
        college.put("جامعة القدس المفتوحة" , 4);
        college.put("بوليتكنك فلسطين" , 5);
        college.put("جامعة الإسراء" , 6);
        college.put("جامعة غزة" , 7);
        college.put("الكلية الجامعية للعلوم التطبيقية" , 8);
        college.put("الكلية الجامعية للعلوم والتكنولوجيا" , 9);
        college.put("كلية فلسطين التقنية - دير البلح" , 10);
        college.put("كلية العودة الجامعية" , 11);
    }

    public int getCollegePosition(String college){

        return  this.college.get(college);
    }
}
