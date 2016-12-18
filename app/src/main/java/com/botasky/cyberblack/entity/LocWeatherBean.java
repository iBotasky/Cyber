package com.botasky.cyberblack.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Botasky on 18/12/2016.
 */

public class LocWeatherBean implements Parcelable{
    private String city_name;
    private int temperature;
    private String info;
    private int img;

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city_name);
        dest.writeInt(this.temperature);
        dest.writeString(this.info);
        dest.writeInt(this.img);
    }

    public LocWeatherBean() {
    }

    protected LocWeatherBean(Parcel in) {
        this.city_name = in.readString();
        this.temperature = in.readInt();
        this.info = in.readString();
        this.img = in.readInt();
    }

    public static final Creator<LocWeatherBean> CREATOR = new Creator<LocWeatherBean>() {
        public LocWeatherBean createFromParcel(Parcel source) {
            return new LocWeatherBean(source);
        }

        public LocWeatherBean[] newArray(int size) {
            return new LocWeatherBean[size];
        }
    };
}
