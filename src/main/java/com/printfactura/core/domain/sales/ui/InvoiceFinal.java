package com.printfactura.core.domain.sales.ui;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InvoiceFinal {

    private String email;
    private String saveDoc;
}
