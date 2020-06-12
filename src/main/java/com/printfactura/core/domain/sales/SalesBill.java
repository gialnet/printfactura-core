package com.printfactura.core.domain.sales;

import com.printfactura.core.domain.*;
import com.printfactura.core.domain.customer.Customer;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SalesBill {

    MySelf mySelf;
    Customer customer;
    HeadSalesBill headSalesBill;
    List<DetailSalesBill> detailSalesBill;
    TotalSalesBill totalSalesBill;
}
