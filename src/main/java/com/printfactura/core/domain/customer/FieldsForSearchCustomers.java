package com.printfactura.core.domain.customer;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FieldsForSearchCustomers {

    private String searchName;
    private int id;

}
