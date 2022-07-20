package com.example.todolist.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class TaskResponse implements Parcelable {

    @Expose
    @SerializedName("name")
    private String name;
    @SerializedName("level")
    private String level;
    @SerializedName("category")
    private String category;
    @SerializedName("date")
    private String date;
    @SerializedName("id")
    @PrimaryKey
    @NonNull private String id;

    public TaskResponse(String name, String level, String category, String date) {
        this.name = name;
        this.level = level;
        this.category = category;
        this.date = date;
    }

    protected TaskResponse(Parcel in) {
        name = in.readString();
        level = in.readString();
        category = in.readString();
        date = in.readString();
        id = in.readString();
    }

    public static final Creator<TaskResponse> CREATOR = new Creator<TaskResponse>() {
        @Override
        public TaskResponse createFromParcel(Parcel in) {
            return new TaskResponse(in);
        }

        @Override
        public TaskResponse[] newArray(int size) {
            return new TaskResponse[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TaskResponse{" +
                "name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", category='" + category + '\'' +
                ", date='" + date + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(level);
        parcel.writeString(category);
        parcel.writeString(date);
        parcel.writeString(id);
    }
}
