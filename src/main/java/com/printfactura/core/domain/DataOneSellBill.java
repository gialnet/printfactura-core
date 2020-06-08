package com.printfactura.core.domain;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DataOneSellBill {

    MySelf mySelf;
    CustomerDetail customerDetail;
    TupleCustomer tupleCustomer;
    SalesCount salesCount;
    TupleHeadBill tupleHeadBill;
    List<TupleDetailBill> tupleDetailBill;
    TupleTotalBill tupleTotalBill;
    TotalsBill totalsBill;
}
