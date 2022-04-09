package by.bsuir.laba_2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RatingDto {

    @JsonProperty("rate")
    private Double rate;

    @JsonProperty("count")
    private Integer count;

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
}
