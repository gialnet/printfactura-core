package com.printfactura.core.domain.customer;

import lombok.*;

import java.util.PrimitiveIterator;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerElectronicAddress {

    // Reference James dto Sales Type= mobile Value= +447593690244
    // Reference Pamela dto. Accounting Type=email  Value= yourAddress@provider.com
    private String Reference;
    private String Type;
    private String Value;
}

