package ru.netology.data;

import lombok.*;

@Data
@NoArgsConstructor
public class PaymentEntity {
    String id;
    String amount;
    String created;
    String status;
    String transactionId;
}
