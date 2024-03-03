package datafactory;

import dto.ProductDTO;
import dto.TokenDTO;

public class Datafactory {
    public TokenDTO createToken(Object username, Object password) {
        return TokenDTO.builder()
                .username(username)
                .password(password)
                .build();
    }

    public ProductDTO createProduct() {
        return ProductDTO.builder()
                .title("Notebook de teste")
                .description("test description")
                .price(500)
                .discountPercentage(1.6)
                .rating(8)
                .stock(300)
                .brand("Apple")
                .category("Notebook")
                .thumbnail("https://cdn.dummyjson.com/product-images/8/thumbnail.jpg")
                .build();
    }

    public ProductDTO createProduct_Empty() {
        return ProductDTO.builder()
                .title(null)
                .description(null)
                .price(null)
                .discountPercentage(0)
                .rating(0)
                .stock(null)
                .brand(null)
                .category(null)
                .thumbnail(null)
                .build();
    }
}