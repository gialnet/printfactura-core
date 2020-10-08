package com.printfactura.core.domain.sales;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DirectDebits {

    private int Id_Consignment;

    // remittance concept for direct debits
    private String ConceptConsignment;

    private String PaymentDueDate;
    private String FormalNoticeDue;

    private String DateConsignment;
    private String State;
    private BigDecimal Amount;

}
