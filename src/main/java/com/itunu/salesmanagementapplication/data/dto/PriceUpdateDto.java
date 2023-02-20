package com.itunu.salesmanagementapplication.data.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PriceUpdateDto {
    Long productId;
    double newPrice;
}
