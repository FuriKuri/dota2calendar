package com.github.furikuri.dota2calendar;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Match implements Parcelable {
    private String team1;
    private String team2;
    private String mode;
    private String tournament;
    private Date match_time;

    public Match(String team1, String team2, String mode, String tournament, Date match_time) {
        this.team1 = team1;
        this.team2 = team2;
        this.mode = mode;
        this.tournament = tournament;
        this.match_time = match_time;
    }

    public Match(Parcel in){
        String[] data = new String[5];
        in.readStringArray(data);
        team1 = data[0];
        team2 = data[1];
        mode = data[2];
        tournament = data[3];
        match_time = new Date(Long.valueOf(data[4]));
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getTournament() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }

    public Date getMatch_time() {
        return match_time;
    }

    public void setMatch_time(Date match_time) {
        this.match_time = match_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeArray(new String[] {
                team1,
                team2,
                mode,
                tournament,
                String.valueOf(match_time.getTime())
        });
    }

    @Override
    public String toString() {
        return "Match{" +
                "team1='" + team1 + '\'' +
                ", team2='" + team2 + '\'' +
                ", mode='" + mode + '\'' +
                ", tournament='" + tournament + '\'' +
                ", match_time=" + match_time +
                '}';
    }
}
