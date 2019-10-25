package com.example.favoritesongs_kt_mf;

import java.util.Locale;

public class Song {
    private int id;
    private String name;
    private String artist;
    private int rating;

    public Song(int newID, String input_name, String input_artist, int input_rating){
        id = newID;
        name = input_name;
        artist = input_artist;
        rating = input_rating;
    }

    public int getId(){return id;};

    public String getName(){return name;}

    public void setName(String name){
        this.name = name;
    }

    public String getArtist(){return artist;}

    public void setArtist(String artist){
        this.artist = artist;
    }

    public int getRating(){return rating;}

    public void setRating(int rating){
        this.rating = rating;
    }

    public String toString(){
        return String.format(Locale.US, "%2d. %s by %s: %d",
                id, name, artist, rating);
    }
}
