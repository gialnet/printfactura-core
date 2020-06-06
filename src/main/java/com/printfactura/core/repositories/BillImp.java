package com.printfactura.core.repositories;

import com.printfactura.core.domain.TuplasFactura;
import com.printfactura.core.domain.TuplasLineasFactura;
import com.printfactura.core.domain.TuplasTotalFactura;
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


    @Override
    public List<TuplasFactura> getHeadFact(int id) {

        String sql = "SELECT * from vwhead_bill where id=?";
        return this.jdbc.query(sql, new BeanPropertyRowMapper(TuplasFactura.class), new Object[]{id});

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
