package com.printfactura.core.makePDFinvoice;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class QRBarcodeTest {

    public static BufferedImage generateQRCodeImage(String barcodeText) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    @Test
    public void CreateQRTest() throws Exception {

        BufferedImage image = generateQRCodeImage("Hoal mundo");

        // Here you can rotate your image as you want (making your magic)
        File outputfile = new File("saved.png");
        ImageIO.write(image, "png", outputfile); // Write the Buffered Image into an output file
    }
}
