package com.printfactura.core.repositories;

import com.printfactura.core.domain.*;
import com.printfactura.core.domain.sales.SalesCount;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GetDataSellBillImp implements GetDataSellBill {

    private final JdbcTemplate jdbc;

    public GetDataSellBillImp(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // SELECT nextval('cont_facturas') as numero,EXTRACT(year FROM now()) as year, EXTRACT(month FROM now()) as month

    @Override
    public SalesCount getSaleCount() {
        String sql = "SELECT nextval('cont_facturas') as numero,EXTRACT(year FROM now()) as year, EXTRACT(month FROM now()) as month";
        List<SalesCount> salesCounts = this.jdbc.query(sql, new BeanPropertyRowMapper(SalesCount.class));

        return salesCounts.get(0);
    }

    @Override
    public TupleHeadBill getHeadFact(int id) {

        String sql = "SELECT * from vwhead_bill where id=?";
        List<TupleHeadBill> tupleHeadBills = this.jdbc.query(sql, new BeanPropertyRowMapper(TupleHeadBill.class), new Object[]{id});
        return tupleHeadBills.get(0);

    }

    @Override
    public List<TupleDetailBill> getLineasFactLocale(int id) {

        String sql = "SELECT id,id_bill,concepto,unidades,por_vat,por_req,importe,(unidades*importe) as total from row_bill where id_bill=?";

        List<TupleDetailBill> tupleDetailBills = this.jdbc.query(sql, new BeanPropertyRowMapper(TupleDetailBill.class), new Object[]{id});
        return tupleDetailBills;
    }

    @Override
    public TupleTotalBill getPieFact(int id) {

        String sql = "SELECT * from total_bill where id_bill=?";

        List<TupleTotalBill> tupleTotalBills = this.jdbc.query(sql, new BeanPropertyRowMapper(TupleTotalBill.class), new Object[]{id});
        return tupleTotalBills.get(0);
    }
}
