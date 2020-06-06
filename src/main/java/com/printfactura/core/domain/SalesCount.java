package com.printfactura.core.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SalesCount {

    int number;
    String year;
    String month;
}
