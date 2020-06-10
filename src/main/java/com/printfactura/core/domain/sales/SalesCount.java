package com.printfactura.core.domain.sales;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SalesCount {

    int Number;
    String Year;
    String Month;

    public String getFormatBillNumber(){
        return Year +"/"+ Month +"/"+String.valueOf(Number);
    }
}