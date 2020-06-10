package com.printfactura.core;

import com.google.gson.Gson;
import com.lowagie.text.DocumentException;
import com.printfactura.core.domain.*;
import com.printfactura.core.domain.customer.CustomerDetail;
import com.printfactura.core.domain.sales.SalesBill;
import com.printfactura.core.domain.sales.TotalSalesBill;
import com.printfactura.core.makePDFinvoice.CreatePDF;
import org.junit.jupiter.api.Test;

import javax.naming.NamingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.printfactura.core.domain.SalesBillTest.SalesBillBuilder;

public class createPDFTest {
    
    @Test
    public void makePDFTestOne() throws SQLException, NamingException, DocumentException, IOException {

        CreatePDF createPDF = new CreatePDF(SalesBillBuilder());
        byte[] myPDF = createPDF.doit();
        SaveToFile(myPDF,"myFact.pdf");


    }

    public void SaveToFile(byte[] bFile, String fileDest){
        try {
            Path path = Paths.get(fileDest);
            Files.write(path, bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
