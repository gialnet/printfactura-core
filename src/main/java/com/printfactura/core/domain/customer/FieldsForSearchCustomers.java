package com.printfactura.core.domain.customer;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FieldsForSearchCustomers {

    @Builder.Default
    private String searchName="";
    @Builder.Default
    private int id=0;

}
