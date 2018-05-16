package com.example.aliceanglesjo.marvel;

/**
 * Created by aliceanglesjo on 2018-04-20.
 */

public class Mountain {
    private String ID;
    private String name;
    private String status;
    private String year;
    private String imdb;
    private String runtime;
    private String director;
    private String storyline;
    private String imgUrl;



    // Constructor
    public Mountain(String inName , String inStatus, String inYear, String inImdb, String inRuntime, String inDirector, String inStoryline, String inImg){
        name = inName;
        status = inStatus;
        year = inYear;
        imdb = inImdb;
        runtime = inRuntime;
        director = inDirector;
        storyline = inStoryline;
        imgUrl = inImg;

    }

    public Mountain(String inName, String inDirector, String inYear, String inStory, String inImage, String inImdb){
        this.name = inName;
        director = inDirector;
        year = inYear;
        storyline = inStory;
        imgUrl = inImage;
        imdb = inImdb;
    }



    public String imgInfo(){
        return imgUrl;
    }

    public String director(){
        return director;
    }
    public String year(){
        return year;
    }

   public String story(){

       return storyline;
   }

    public String heading(){
        String str = name;
        str+= " (" + year +")";

        return str;
    }

    public String subtile(){
        String subtitle = "Directed by " + director;

        return subtitle;
    }

    public String subtile2(){
        String subtitle2 = "IMDB rating: " + imdb;

        return subtitle2;
    }

    @Override
    public String toString() {
        return name;
    }

}
