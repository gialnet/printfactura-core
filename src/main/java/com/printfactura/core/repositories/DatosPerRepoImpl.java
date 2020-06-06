package com.printfactura.core.repositories;

import com.printfactura.core.domain.MySelf;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatosPerRepoImpl implements DatosPerRepo {

    private final JdbcTemplate jdbc;

    public DatosPerRepoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<MySelf> getMe() {

        String sql = "Select Nif,Nombre,Direccion,Objeto,Poblacion,Pais_ISO3166,periodo,fiscal_year,iva,IBAN from DatosPer where id=1";
        return this.jdbc.query(sql, new BeanPropertyRowMapper(MySelf.class));
    }
}
