package dh.projetointegradorctd.backend.dto;

import dh.projetointegradorctd.backend.model.storage.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteDto {
    private Long clientId;
    private Product product;
}
