package com.printfactura.core.repositories;

import com.printfactura.core.domain.SalesCount;
import com.printfactura.core.domain.TupleHeadBill;
import com.printfactura.core.domain.TupleDetailBill;
import com.printfactura.core.domain.TupleTotalBill;

import java.util.List;

public interface GetDataSellBill {


    SalesCount getSaleCount();
    TupleHeadBill getHeadFact(int id);
    List<TupleDetailBill> getLineasFactLocale(int id);
    TupleTotalBill getPieFact(int id);
}
