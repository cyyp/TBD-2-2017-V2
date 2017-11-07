package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class userModel {

    private long u_id;
    private String u_name;
    private int u_mentions;
    private int u_user_favourites_count;
    private int u_followers_count;
    private int u_friend_count;
    private int u_listed_count;
    private int u_statuses_count;
    private String u_url;
    private String u_profile_image_url;
    private int retweet;
    private double spn;
    private int rank;


    public int getRetweet() {
        return retweet;
    }

    public void setRetweet(int retweet) {
        this.retweet = retweet;
    }

    public long getU_id() {
        return u_id;

    }

    public void setU_id(long u_id) {
        this.u_id = u_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public int getU_mentions() {
        return u_mentions;
    }

    public void setU_mentions(int u_mentions) {
        this.u_mentions = u_mentions;
    }

    public int getU_user_favourites_count() {
        return u_user_favourites_count;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setU_user_favourites_count(int u_user_favourites_count) {
        this.u_user_favourites_count = u_user_favourites_count;

    }

    public int getU_followers_count() {
        return u_followers_count;
    }

    public void setU_followers_count(int u_followers_count) {
        this.u_followers_count = u_followers_count;
    }

    public int getU_friend_count() {
        return u_friend_count;
    }

    public void setU_friend_count(int u_friend_count) {
        this.u_friend_count = u_friend_count;
    }

    public int getU_listed_count() {
        return u_listed_count;
    }

    public void setU_listed_count(int u_listed_count) {
        this.u_listed_count = u_listed_count;
    }

    public int getU_statuses_count() {
        return u_statuses_count;
    }

    public void setU_statuses_count(int u_statuses_count) {
        this.u_statuses_count = u_statuses_count;
    }

    public String getU_url() {
        return u_url;
    }

    public void setU_url(String u_url) {
        this.u_url = u_url;
    }

    public String getU_profile_image_url() {
        return u_profile_image_url;
    }

    public void setU_profile_image_url(String u_profile_image_url) {
        this.u_profile_image_url = u_profile_image_url;
    }

    public double getSpn() {
        return spn;
    }

    public void setSpn(double spn) {
        this.spn = spn;
    }




    public void setRanking(List<userModel> u){

        int cant = u.size();
        int[] d_rank = new int[cant];
        int cont = 0;
        for(userModel um : u){
            d_rank[cont] = um.getU_followers_count();
            cont++;
        }
        Arrays.sort(d_rank);

        int rank = cant;
        for(int i = 0; i < cant; i++){
            for(userModel um: u){
                if(d_rank[i] == um.getU_followers_count() && um.getRank() == 0){
                    um.setRank(rank);
                    rank--;
                    break;
                }
            }
        }
    }
}
