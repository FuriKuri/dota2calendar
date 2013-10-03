package com.github.furikuri.dota2calendar;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MatchList implements Parcelable {

    private List<Match> matches = new ArrayList<Match>();

    public MatchList(List<Match> matches) {
        this.matches = matches;
    }

    public MatchList(Parcel in) {
        in.readList(matches, null);
    }

    public List<Match> getMatches() {
        return matches;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(matches);
    }

    @Override
    public String toString() {
        return "MatchList{" +
                "matches=" + matches +
                '}';
    }
}
