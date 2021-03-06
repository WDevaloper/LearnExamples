package com.github.binderlearn;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class User implements Parcelable {
    public String name;

    public User() {
        Log.e("tag", "<init>");
    }

    protected User(Parcel in) {
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
