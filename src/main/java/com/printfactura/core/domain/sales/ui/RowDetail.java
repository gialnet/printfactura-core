package com.printfactura.core.domain.sales.ui;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RowDetail {

    @Builder.Default
    private String Concept = "";
    @Builder.Default
    private BigDecimal Unit = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal Price = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal Total = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal VAT = BigDecimal.ZERO;
}
