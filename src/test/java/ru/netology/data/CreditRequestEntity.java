package ru.netology.data;

import lombok.*;

@Data
@NoArgsConstructor
public class CreditRequestEntity {
    String id;
    String bank_id;
    String created;
    String status;
}
