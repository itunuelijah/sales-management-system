package com.itunu.salesmanagementapplication.data.dto;
import com.itunu.salesmanagementapplication.data.models.OrderLine;
import lombok.Data;
import java.util.List;

@Data
public class OrderUpdateDto {
    private Long orderId;
    private Long productId;
    private int quantity;
    private String customerName;
    private String customerPhoneNumber;
    private List<OrderLine> orderLines;



    public OrderUpdateDto(Long orderId) {
        this.orderId = orderId;
    }
}
