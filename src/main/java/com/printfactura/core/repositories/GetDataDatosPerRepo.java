package com.printfactura.core.repositories;

import com.printfactura.core.domain.MySelf;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface GetDataDatosPerRepo {

    List<MySelf> getMe();

}