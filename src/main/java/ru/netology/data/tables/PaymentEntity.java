package ru.netology.data.tables;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class PaymentEntity implements Serializable {
    private String id;
    private Long amount;
    private String created;
    private String status;
    private String transaction_id;
}
