package by.bsuir.laba_2.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RatingDto implements Parcelable {

    @JsonProperty("rate")
    private Double rate;

    @JsonProperty("count")
    private Integer count;

    public RatingDto() {
    }

    public RatingDto(Double rate, Integer count) {
        this.rate = rate;
        this.count = count;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public static final Creator<RatingDto> CREATOR = new Creator<RatingDto>(){

        @Override
        public RatingDto createFromParcel(Parcel parcel) {
            Double rate = parcel.readDouble();
            Integer count = parcel.readInt();
            return new RatingDto(rate, count);
        }

        @Override
        public RatingDto[] newArray(int i) {
            return new RatingDto[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(rate);
        parcel.writeInt(count);
    }
}
