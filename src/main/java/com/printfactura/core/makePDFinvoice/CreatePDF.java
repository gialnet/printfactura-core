package com.printfactura.core.makePDFinvoice;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.printfactura.core.domain.*;

import javax.naming.NamingException;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CreatePDF {

    private static final Font FUENTE_ENCABEZADO = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, Color.WHITE);
    private static final Font FUENTE_GRIS_OSCURO = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new Color(98, 98, 98));
    private static final Font FUENTE_TITULO = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, Color.BLACK);
    private static final Font FUENTE_TITULO_TABLA = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new Color(98, 98, 98));
    private static final Font FUENTE_PIE_TABLA = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, Color.WHITE);
    private static final Font FUENTE_CUERPO = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, Color.BLACK);
    private static final Font FUENTE_DIRECCION_POSTAL = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, Color.DARK_GRAY);


    private PdfWriter writer;
    private Document document;
    private ByteArrayOutputStream PDFenMemoria = new ByteArrayOutputStream();
    private PdfPTable table;
    private PdfPCell cell;
    private Paragraph p;

    private int id_fact;
    private final DataOneSellBill dataOneSellBill;

    public CreatePDF(DataOneSellBill dataOneSellBill) {
        this.dataOneSellBill = dataOneSellBill;
    }


    public byte[] doit(int id_factura, String documento) throws DocumentException, SQLException, IOException, NamingException
    {

        this.id_fact = id_factura;

        // Crear el PDF en memoria
        //CreatePDF();

        // Crear la tabla
        MakeTable(documento);

        // Lineas de detalle
        int lineas = printDetailBill();

        lineas = 23 - lineas;

        // Lineas en blanco de relleno
        for (int i = 0; i < lineas; i++) {
            printLineasBlancos();
        }

        // Totales
        printTotales();

        // Pie con los datos de la empresa
        pintPie();

        // Guardar el PDF en memoria
        Grabar();

        // Salvar en la BBDD en caso de factura pro forma
        // no la grabamos
        if (!documento.equals("PRO-FORMA"))
            Save();

        return PDFenMemoria.toByteArray();
    }

    private void Grabar() throws DocumentException
    {
        document.add(table);
        document.close();
        writer.close();
    }

    private void Save() throws DocumentException, IOException, SQLException
    {

        /*Connection conn = PGconectar();

        String registerQuery = "{call SaveFactPDF(?,?)}";
        CallableStatement ps = conn.prepareCall(registerQuery);
        ps.setBytes(1, PDFenMemoria.toByteArray() );
        ps.setInt(2, id_fact);
        ps.executeUpdate();
        ps.close();
*/


    }

    private void CreatePDF() throws DocumentException
    {

        this.document = new Document(PageSize.A4);

        writer = PdfWriter.getInstance(this.document, PDFenMemoria);

        this.document.open();

    }

    /**
     *
     * @param documento
     * @throws SQLException
     */
    private void MakeTable(String documento) throws SQLException
    {
        Color azul_oscuro = new Color(39, 83, 146);
        Color gris = new Color(237,237,237);

        float[] widths1 = { 3f, 1f, 1f, 1f};

        this.table = new PdfPTable(widths1);

        table.setWidthPercentage(100);
        table.setHeaderRows(10);


        PdfPCell h1 = new PdfPCell(new Paragraph(dataOneSellBill.getMySelf().getNombre(),FUENTE_ENCABEZADO));
        //h1.setGrayFill(0.7f);
        h1.setColspan(4);
        h1.setBorder(Rectangle.NO_BORDER);
        h1.setHorizontalAlignment(Element.ALIGN_LEFT);
        h1.setBackgroundColor(azul_oscuro);
        h1.setFixedHeight(50);
        h1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        h1.setPaddingLeft(10);

        table.addCell(h1);

        // línea en blanco
        h1 = new PdfPCell(new Paragraph(" ",FUENTE_ENCABEZADO));
        h1.setColspan(4);
        h1.setBorder(Rectangle.NO_BORDER);
        h1.setFixedHeight(5);

        table.addCell(h1);

        // línea en blanco
        h1 = new PdfPCell(new Paragraph(" ",FUENTE_ENCABEZADO));
        //h1.setGrayFill(0.7f);
        h1.setColspan(1);
        h1.setBorder(Rectangle.NO_BORDER);
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(h1);

        // número de factura

        documento=(documento.equals("cuota")) ? "Recibo" : "Factura";
        PdfPCell h2 = new PdfPCell(new Paragraph(documento+" núm.: ",FUENTE_GRIS_OSCURO));
        //h2.setGrayFill(0.7f);
        h2.setColspan(1);

        h2.setBorder(Rectangle.NO_BORDER);
        h2.setBackgroundColor(gris);
        h2.setHorizontalAlignment(Element.ALIGN_LEFT);
        h2.setVerticalAlignment(Element.ALIGN_MIDDLE);

        table.addCell(h2);


        //tFact.getNumero().trim() numero de factura
        h2 = new PdfPCell(new Paragraph(dataOneSellBill.getTupleHeadBill().getNumero().trim(),FUENTE_GRIS_OSCURO));
        //h2.setGrayFill(0.7f);
        h2.setColspan(2);

        h2.setBorder(Rectangle.NO_BORDER);
        h2.setBackgroundColor(gris);
        h2.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(h2);

        // línea en blanco
        h1 = new PdfPCell(new Paragraph(" ",FUENTE_ENCABEZADO));
        h1.setColspan(1);
        h1.setBorder(Rectangle.NO_BORDER);
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(h1);

        //Titulo fecha
        PdfPCell h3 = new PdfPCell(new Paragraph("Fecha: ",FUENTE_GRIS_OSCURO));
        //h3.setGrayFill(0.7f);
        h3.setColspan(1);

        h3.setBorder(Rectangle.NO_BORDER);
        h3.setBackgroundColor(gris);
        h3.setHorizontalAlignment(Element.ALIGN_LEFT);
        h3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(h3);


        h3 = new PdfPCell(new Paragraph(dataOneSellBill.getTupleHeadBill().getFecha(),FUENTE_GRIS_OSCURO));
        //h3.setGrayFill(0.7f);
        h3.setColspan(2);

        h3.setBorder(Rectangle.NO_BORDER);
        h3.setBackgroundColor(gris);
        h3.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(h3);

        // línea en blanco
        h1 = new PdfPCell(new Paragraph(" ",FUENTE_ENCABEZADO));
        //h1.setGrayFill(0.7f);
        h1.setColspan(1);
        h1.setBorder(Rectangle.NO_BORDER);
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(h1);

        // IBAN
        h3 = new PdfPCell(new Paragraph("IBAN: ",FUENTE_GRIS_OSCURO));
        //h3.setGrayFill(0.7f);
        h3.setColspan(1);

        h3.setBorder(Rectangle.NO_BORDER);
        h3.setBackgroundColor(gris);
        h3.setHorizontalAlignment(Element.ALIGN_LEFT);
        h3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        h3.setPaddingBottom(7);
        table.addCell(h3);

        //dp.getIBAN()
        h3 = new PdfPCell(new Paragraph(dataOneSellBill.getMySelf().getIBAN(),FUENTE_GRIS_OSCURO));
        //h3.setGrayFill(0.7f);
        h3.setColspan(2);

        h3.setBorder(Rectangle.NO_BORDER);
        h3.setBackgroundColor(gris);
        h3.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(h3);

        // Linea en blanco *************************************
        h1 = new PdfPCell(new Paragraph(" ",FUENTE_ENCABEZADO));
        h1.setColspan(1);
        h1.setBorder(Rectangle.NO_BORDER);
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(h1);

        // BIC-swift **********************************************
        h3 = new PdfPCell(new Paragraph("BIC:",FUENTE_GRIS_OSCURO));
        //h3.setGrayFill(0.7f);
        h3.setColspan(1);

        h3.setBorder(Rectangle.NO_BORDER);
        h3.setBackgroundColor(gris);
        h3.setHorizontalAlignment(Element.ALIGN_LEFT);
        h3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        h3.setPaddingBottom(7);
        table.addCell(h3);


        /*h3 = new PdfPCell(new Paragraph(dp.getBIC(),FUENTE_GRIS_OSCURO));

        h3.setColspan(2);

        h3.setBorder(Rectangle.NO_BORDER);
        h3.setBackgroundColor(gris);
        h3.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(h3);*/

        // línea en blanco
        h1 = new PdfPCell(new Paragraph(" ",FUENTE_ENCABEZADO));
        //h1.setGrayFill(0.7f);
        h1.setColspan(4);
        h1.setBorder(Rectangle.NO_BORDER);
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);


        table.addCell(h1);

        // Datos del cliente
        PdfPCell h4 = new PdfPCell(new Paragraph("Datos del cliente",FUENTE_TITULO));
        //h4.setGrayFill(0.7f);
        h4.setColspan(4);
        h4.setBorder(Rectangle.NO_BORDER);
        h4.setHorizontalAlignment(Element.ALIGN_LEFT);

        table.addCell(h4);

        // Nombre o razón social
        PdfPCell h5 = new PdfPCell(new Paragraph(dataOneSellBill.getTupleHeadBill().getNombre(),FUENTE_DIRECCION_POSTAL));
        //h5.setGrayFill(0.7f);
        h5.setColspan(4);
        h5.setBorder(Rectangle.NO_BORDER);
        h5.setHorizontalAlignment(Element.ALIGN_LEFT);

        table.addCell(h5);

        //
        // Dirección postal
        //

        // Calle y número
        PdfPCell h6 = new PdfPCell(new Paragraph(dataOneSellBill.getTupleHeadBill().getDireccion(),FUENTE_DIRECCION_POSTAL));
        //h6.setGrayFill(0.7f);
        h6.setColspan(4);
        h6.setBorder(Rectangle.NO_BORDER);
        h6.setHorizontalAlignment(Element.ALIGN_LEFT);

        table.addCell(h6);

        // Objeto
        PdfPCell h7 = new PdfPCell(new Paragraph(dataOneSellBill.getTupleHeadBill().getObjeto(),FUENTE_DIRECCION_POSTAL));
        //h7.setGrayFill(0.7f);
        h7.setColspan(4);
        h7.setBorder(Rectangle.NO_BORDER);
        h7.setHorizontalAlignment(Element.ALIGN_LEFT);

        table.addCell(h7);

        // Código postal poblacion
        PdfPCell h8 = new PdfPCell(new Paragraph(dataOneSellBill.getTupleHeadBill().getPoblacion(),FUENTE_DIRECCION_POSTAL));
        //h8.setGrayFill(0.7f);
        h8.setColspan(4);
        h8.setBorder(Rectangle.NO_BORDER);
        h8.setHorizontalAlignment(Element.ALIGN_LEFT);

        table.addCell(h8);

        // línea en blanco
        h1 = new PdfPCell(new Paragraph(" ",FUENTE_ENCABEZADO));
        //h1.setGrayFill(0.7f);
        h1.setColspan(4);
        h1.setBorder(Rectangle.NO_BORDER);
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);


        table.addCell(h1);
    }

    /**
     *
     * @return
     * @throws SQLException
     * @throws DocumentException
     */
    private int printDetailBill() throws SQLException, DocumentException
    {

        Color gris = new Color(237,237,237);
        Color borde = new Color(204,204,204);
        // Linea de Concepto
        p = new Paragraph("Concepto",FUENTE_TITULO_TABLA);
        p.setAlignment(Element.ALIGN_LEFT);

        cell = new PdfPCell();
        cell.setBackgroundColor(gris);
        cell.addElement(p);
        cell.setBorderColor(borde);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);


        // Linea cantidad
        p = new Paragraph("Unidades",FUENTE_TITULO_TABLA);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell = new PdfPCell();
        cell.setBackgroundColor(gris);
        cell.setBorderColor(borde);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);


        // Linea precio 1
        p = new Paragraph("Importe",FUENTE_TITULO_TABLA);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell = new PdfPCell();
        cell.setBackgroundColor(gris);
        cell.setBorderColor(borde);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        // Linea Importe 1
        p = new Paragraph("Total",FUENTE_TITULO_TABLA);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell = new PdfPCell();
        cell.setBackgroundColor(gris);
        cell.setBorderColor(borde);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        List<TupleDetailBill> LineasFactura = dataOneSellBill.getTupleDetailBill();

        int j=1;

        for (TupleDetailBill lineasFact : LineasFactura) {

            j++;
            // Linea de Concepto
            p = new Paragraph(lineasFact.getConcepto(),FUENTE_CUERPO);
            p.setAlignment(Element.ALIGN_LEFT);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.addElement(p);
            table.addCell(cell);


            // Linea cantidad
            p = new Paragraph(lineasFact.getUnidades(),FUENTE_CUERPO);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.addElement(p);
            table.addCell(cell);


            // Linea precio 1
            p = new Paragraph(lineasFact.getImporte(),FUENTE_CUERPO);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.addElement(p);
            table.addCell(cell);

            // Linea Importe 1
            p = new Paragraph(lineasFact.getTotal(),FUENTE_CUERPO);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.addElement(p);
            table.addCell(cell);

        }

        return j;
    }

    /**
     *
     * @throws SQLException
     * @throws DocumentException
     */
    private void printTotales() throws SQLException, DocumentException
    {

        //TupleTotalBill TotalesFactura = myGetDataSellBill.getPieFact(id_fact);

        BigDecimal TotalAPagar = BigDecimal.ZERO;
        Color azul_oscuro = new Color(39, 83, 146);

        //int cuantos = TotalesFactura.size();
        //System.out.println(cuantos);



            // añadir los totales
            PdfPCell pie = new PdfPCell(new Paragraph("Base al "+dataOneSellBill.getTupleTotalBill().getIva()+"% IVA",FUENTE_CUERPO));
            //pie.setColspan(2);
            //pie.setGrayFill(0.7f);
            pie.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pie.setBorder(Rectangle.NO_BORDER);
            table.addCell(pie);

            // base imponible
            p = new Paragraph(dataOneSellBill.getTupleTotalBill().getStBase(),FUENTE_CUERPO);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.addElement(p);
            table.addCell(cell);


            // IVA
            p = new Paragraph(dataOneSellBill.getTupleTotalBill().getStIVA(),FUENTE_CUERPO);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.addElement(p);
            table.addCell(cell);


            // Total Factura
            p = new Paragraph(dataOneSellBill.getTupleTotalBill().getStTotal(),FUENTE_CUERPO);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.addElement(p);
            table.addCell(cell);

            TotalAPagar=TotalAPagar.add(dataOneSellBill.getTupleTotalBill().getTotal());


        // añadir el total general
        PdfPCell pie2 = new PdfPCell(new Paragraph("Total a pagar ", FUENTE_PIE_TABLA));
        pie2.setColspan(3);
        pie2.setBackgroundColor(azul_oscuro);
        pie2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        pie2.setBorder(Rectangle.NO_BORDER);
        table.addCell(pie2);

        p = new Paragraph(NumberFormat.getCurrencyInstance(Locale.GERMANY).format(TotalAPagar),FUENTE_PIE_TABLA);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell = new PdfPCell();
        cell.setBackgroundColor(azul_oscuro);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(p);
        table.addCell(cell);

    }

    /**
     *
     * @throws SQLException
     * @throws DocumentException
     */

    private void printLineasBlancos() throws SQLException, DocumentException
    {

        // Linea de Concepto
        p = new Paragraph(" ",FUENTE_CUERPO);
        p.setAlignment(Element.ALIGN_LEFT);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(p);
        table.addCell(cell);


        // Linea cantidad
        p = new Paragraph(" ",FUENTE_CUERPO);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(p);
        table.addCell(cell);


        // Linea precio 1
        p = new Paragraph(" ",FUENTE_CUERPO);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(p);
        table.addCell(cell);

        // Linea Importe 1
        p = new Paragraph(" ",FUENTE_CUERPO);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(p);
        table.addCell(cell);

    }

    /**
     *
     * @throws SQLException
     * @throws NamingException
     */
    private void pintPie() throws SQLException, NamingException
    {

        // Imprimir el nombre de la empresa y el nif
        //PdfPCell h6 = new PdfPCell(new Paragraph(dp.getNombre()+" NIF/CIF "+dp.getNif()+" - "+dp.getUrl_web(),FUENTE_DIRECCION_POSTAL));
        PdfPCell h6 = new PdfPCell(new Paragraph(dataOneSellBill.getMySelf().getNombre()+" NIF/CIF "+dataOneSellBill.getMySelf().getNif(),FUENTE_DIRECCION_POSTAL));
        //h6.setGrayFill(0.7f);
        h6.setColspan(4);
        h6.setBorder(Rectangle.NO_BORDER);
        h6.setHorizontalAlignment(Element.ALIGN_LEFT);

        table.addCell(h6);

        // Imprimir la dirección de la empresa
        h6 = new PdfPCell(new Paragraph(dataOneSellBill.getMySelf().getDireccion()+" "+dataOneSellBill.getMySelf().getObjeto()+" "+dataOneSellBill.getMySelf().getPoblacion(),FUENTE_DIRECCION_POSTAL));
        //h6.setGrayFill(0.7f);
        h6.setColspan(4);
        h6.setBorder(Rectangle.NO_BORDER);
        h6.setHorizontalAlignment(Element.ALIGN_LEFT);

        table.addCell(h6);

       /* h6 = new PdfPCell(new Paragraph(dp.getMail()+" "+dp.getMovil()+" "+dp.getFax(),FUENTE_DIRECCION_POSTAL));
        //h6.setGrayFill(0.7f);
        h6.setColspan(4);
        h6.setBorder(Rectangle.NO_BORDER);
        h6.setHorizontalAlignment(Element.ALIGN_LEFT);

        table.addCell(h6);*/

    }
}
