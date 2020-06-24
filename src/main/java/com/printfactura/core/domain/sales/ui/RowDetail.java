package com.printfactura.core.domain.sales.ui;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RowDetail {

    private String Concept;
    private BigDecimal Unit;
    private BigDecimal Price;
    private BigDecimal Total;
}
