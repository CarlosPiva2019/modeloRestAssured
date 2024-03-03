package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProductDTO {
    private String title;
    private String description;
    private Integer price;
    private double discountPercentage;
    private double rating;
    private Integer stock;
    private String brand;
    private String category;
    private String thumbnail;
}