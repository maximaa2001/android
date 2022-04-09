package by.bsuir.laba_2.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ProductDto implements Parcelable {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category")
    private String category;

    @JsonProperty("image")
    private String image;

    @JsonProperty("rating")
    private RatingDto rating;

    public ProductDto(Integer productId, String title, Double price, String description, String category, String imagePath, RatingDto ratingDto) {
        this.id = productId;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = imagePath;
        this.rating = ratingDto;
    }

    public ProductDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public RatingDto getRating() {
        return rating;
    }

    public void setRating(RatingDto rating) {
        this.rating = rating;
    }

    public static final Creator<ProductDto> CREATOR = new Creator<ProductDto>() {
        @Override
        public ProductDto createFromParcel(Parcel parcel) {
            Integer id = parcel.readInt();
            String title = parcel.readString();
            Double price = parcel.readDouble();
            String description =  parcel.readString();
            String category = parcel.readString();
            String image = parcel.readString();
            RatingDto ratingDto = parcel.readTypedObject(RatingDto.CREATOR);
            return new ProductDto(id, title, price, description, category, image, ratingDto);
        }

        @Override
        public ProductDto[] newArray(int i) {
            return new ProductDto[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeDouble(price);
        parcel.writeString(description);
        parcel.writeString(category);
        parcel.writeString(image);
        parcel.writeTypedObject(rating, 0);
    }
}
