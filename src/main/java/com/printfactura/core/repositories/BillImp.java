package com.printfactura.core.repositories;

import com.printfactura.core.domain.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BillImp implements Bill {

    private final JdbcTemplate jdbc;

    public BillImp(JdbcTemplate jdbc) {
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
    public TuplasFactura getHeadFact(int id) {

        String sql = "SELECT * from vwhead_bill where id=?";
        List<TuplasFactura>  tuplasFacturas = this.jdbc.query(sql, new BeanPropertyRowMapper(TuplasFactura.class), new Object[]{id});
        return tuplasFacturas.get(0);

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
