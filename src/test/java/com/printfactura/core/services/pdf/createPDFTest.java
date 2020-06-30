package com.printfactura.core.services.pdf;

import com.lowagie.text.DocumentException;
import com.printfactura.core.makePDFinvoice.CreatePDF;
import org.junit.jupiter.api.Test;

import javax.naming.NamingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.printfactura.core.domain.SalesBillTest.SalesBillBuilder;

public class createPDFTest {

    @Test
    public void makePDFTestOne() throws NamingException, DocumentException, IOException {

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
