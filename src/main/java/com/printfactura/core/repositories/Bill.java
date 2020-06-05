package com.printfactura.core.repositories;

import com.printfactura.core.domain.TuplasFactura;
import com.printfactura.core.domain.TuplasLineasFactura;
import com.printfactura.core.domain.TuplasTotalFactura;

import java.util.List;

public interface Bill {


    TuplasFactura getHeadFact(int id);
    List<TuplasLineasFactura> getLineasFactLocale(int id);
    List<TuplasTotalFactura> getPieFact(int id);
}
