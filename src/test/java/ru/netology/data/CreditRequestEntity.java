package ru.netology.data;

import lombok.*;

@Data
@NoArgsConstructor
public class CreditRequestEntity {
    String id;
    String bankId;
    String created;
    String status;
}
