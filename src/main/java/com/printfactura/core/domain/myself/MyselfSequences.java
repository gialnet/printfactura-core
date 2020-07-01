package com.printfactura.core.domain.myself;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MyselfSequences {

    private int SeqCustomer;
    private int SeqInvoice;
}
