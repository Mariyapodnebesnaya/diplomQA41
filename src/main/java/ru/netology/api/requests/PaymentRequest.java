package ru.netology.api.requests;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PaymentRequest {
    private String number;
    private String month;
    private String year;
    private String holder;
    private String cvv;
}
