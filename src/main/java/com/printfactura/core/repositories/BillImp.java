package com.printfactura.core.repositories;

import com.printfactura.core.domain.TuplasFactura;
import com.printfactura.core.domain.TuplasLineasFactura;
import com.printfactura.core.domain.TuplasTotalFactura;

import java.util.List;

public class BillImp implements Bill {

    @Override
    public TuplasFactura getHeadFact(int id) {
        return null;
    }

    @Override
    public List<TuplasLineasFactura> getLineasFactLocale(int id) {
        return null;
    }

    @Override
    public List<TuplasTotalFactura> getPieFact(int id) {
        return null;
    }
}
