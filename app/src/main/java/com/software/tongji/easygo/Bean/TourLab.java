package com.software.tongji.easygo.Bean;

import android.content.Context;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class TourLab {
    public static TourLab sTourLab;

    public static TourLab get(Context context){
        if(sTourLab == null){
            sTourLab = new TourLab(context);
        }
        return sTourLab;
    }

    private TourLab(Context context){
        Context context1 = context;
        List<Tour> tourList = new ArrayList<>();
    }

    public List<Tour> getTourList() {
        return LitePal.findAll(Tour.class);
    }

    public String getTourTitle(String tourId){
        List<Tour> tours = LitePal.where("mId = ?", tourId).find(Tour.class);
        return tours.get(0).getTitle();
    }
    public void addNewTour(Tour tour){
        tour.save();
    }

    public int size(){
        return LitePal.count(Tour.class);
    }

    public String latestTourId(){
        Tour tour = LitePal.findLast(Tour.class);
        return tour.getId();
    }
}
