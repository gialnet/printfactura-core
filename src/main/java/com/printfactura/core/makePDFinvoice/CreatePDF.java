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
    private static final Font FUENTE_MYSELF = FontFactory.getFont(FontFactory.COURIER_BOLDOBLIQUE, 10, Font.NORMAL, Color.BLACK);
    private static final Font FUENTE_GRIS_OSCURO = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new Color(98, 98, 98));
    private static final Font FUENTE_TITULO = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, Color.BLACK);
    private static final Font FUENTE_TITULO_TABLA = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new Color(98, 98, 98));
    private static final Font FUENTE_PIE_TABLA = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, Color.WHITE);
    private static final Font FUENTE_CUERPO = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, Color.BLACK);
    private static final Font FUENTE_DIRECCION_POSTAL = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, Color.DARK_GRAY);

    private static Color BLUE_DARK = new Color(39, 83, 146);
    private static Color GRAY = new Color(237,237,237);

    private PdfWriter writer;
    private Document document;
    private ByteArrayOutputStream PDFenMemoria = new ByteArrayOutputStream();
    private PdfPTable table;
    private PdfPCell cell;
    private Paragraph p;

    private final DataOneSellBill dataOneSellBill;

    public CreatePDF(DataOneSellBill dataOneSellBill) {
        this.dataOneSellBill = dataOneSellBill;
    }


    public byte[] doit() throws DocumentException, SQLException, IOException, NamingException
    {
        // Crear el PDF en memoria
        CreatePDFInMemory();

        // Config Table
        ConfigTable();

        // Crear la tabla
        MakeTable();

        // Lineas de detalle
        int lineas = printDetailBill();

        lineas = 21 - lineas;

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
        /*if (!documento.equals("PRO-FORMA"))
            Save();*/

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

    private void CreatePDFInMemory() throws DocumentException
    {

        this.document = new Document(PageSize.A4);

        writer = PdfWriter.getInstance(this.document, PDFenMemoria);

        this.document.open();

    }

    private PdfPTable ConfigTable(){

        float[] widths1 = { 3f, 1f, 1f, 1f};

        this.table = new PdfPTable(widths1);

        table.setWidthPercentage(100);
        table.setHeaderRows(10);

        return table;
    }

    private void DocHead(){

        PdfPCell h1 = new PdfPCell(new Paragraph(dataOneSellBill.getMySelf().getCompanyName(), FUENTE_ENCABEZADO));
        //h1.setGrayFill(0.7f);
        h1.setColspan(4);
        h1.setBorder(Rectangle.NO_BORDER);
        h1.setHorizontalAlignment(Element.ALIGN_LEFT);
        h1.setBackgroundColor(BLUE_DARK);
        h1.setMinimumHeight(40);
        h1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        h1.setPaddingLeft(10);

        table.addCell(h1);

        // línea en blanco
        BlankLine();
        /*h1 = new PdfPCell(new Paragraph(" ",FUENTE_ENCABEZADO));
        h1.setColspan(4);
        h1.setBorder(Rectangle.NO_BORDER);
        h1.setFixedHeight(5);

        table.addCell(h1);*/
    }

    private void PhysicalAddress(){

        // Address street
        PdfPCell address1 = new PdfPCell(new Paragraph(dataOneSellBill.getMySelf().getAddress(), FUENTE_MYSELF));
        address1.setColspan(4);
        address1.setBorder(Rectangle.NO_BORDER);
        address1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(address1);

        // Address city and post code
        address1 = new PdfPCell(new Paragraph(dataOneSellBill.getMySelf().getCity()+
                ", "+dataOneSellBill.getMySelf().getPostCode(), FUENTE_MYSELF));
        address1.setColspan(4);
        address1.setBorder(Rectangle.NO_BORDER);
        address1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(address1);

        // Address country
        address1 = new PdfPCell(new Paragraph(dataOneSellBill.getMySelf().getCountry(), FUENTE_MYSELF));
        address1.setColspan(4);
        address1.setBorder(Rectangle.NO_BORDER);
        address1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(address1);

        // Company identification
        address1 = new PdfPCell(new Paragraph(dataOneSellBill.getMySelf().getIdentification(), FUENTE_MYSELF));
        address1.setColspan(4);
        address1.setBorder(Rectangle.NO_BORDER);
        address1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(address1);
    }

    private void BlankLine(){
        PdfPCell h1 = new PdfPCell(new Paragraph(" ",FUENTE_ENCABEZADO));
        h1.setColspan(4);
        h1.setBorder(Rectangle.NO_BORDER);
        h1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(h1);
    }
    /**
     *
     * @throws SQLException
     */
    private void MakeTable() throws SQLException
    {

        DocHead();

        //PdfPCell h1;

        PhysicalAddress();

        // línea en blanco
        BlankLine();
        /*h1 = new PdfPCell(new Paragraph(" ",FUENTE_ENCABEZADO));
        //h1.setGrayFill(0.7f);
        h1.setColspan(4);
        h1.setBorder(Rectangle.NO_BORDER);
        h1.setHorizontalAlignment(Element.ALIGN_LEFT);
        h1.setPaddingLeft(10);

        table.addCell(h1);*/

        // Invoice number
        PdfPCell h2 = new PdfPCell(new Paragraph("Invoice nº: "+dataOneSellBill.getTupleHeadBill().getNumero().trim()
                +"     Date: "
                +dataOneSellBill.getTupleHeadBill().getFecha(),FUENTE_GRIS_OSCURO));
        //h2.setGrayFill(0.7f);
        h2.setColspan(1);
        h2.setBorder(Rectangle.NO_BORDER);
        h2.setBackgroundColor(GRAY);
        h2.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(h2);

        // Linea en blanco *************************************
        BlankLine();

        // línea en blanco
        BlankLine();


       /* h2 = new PdfPCell(new Paragraph("Caja ",FUENTE_GRIS_OSCURO));
        //h2.setGrayFill(0.7f);
        h2.setColspan(2);
        h2.setBorder(Rectangle.BOX);
        h2.setBackgroundColor(GRAY);
        h2.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(h2);*/

        // Datos del cliente
        PdfPCell h4 = new PdfPCell(new Paragraph("-Client details-\n"+
                dataOneSellBill.getCustomerDetail().getCompanyName()+
                "\n"+dataOneSellBill.getCustomerDetail().getAddress()+
                "\n"+dataOneSellBill.getCustomerDetail().getPostCode()+" "+
                dataOneSellBill.getCustomerDetail().getCity()+
                "\n"+dataOneSellBill.getCustomerDetail().getCountry()+
                "\n"+dataOneSellBill.getCustomerDetail().getIdentification()
                , FUENTE_MYSELF));
        //h4.setGrayFill(0.7f);
        h4.setColspan(4);
        h4.setFixedHeight(72f);
        h4.setBorder(Rectangle.BOX);
        h2.setBackgroundColor(GRAY);
        h4.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(h4);


        // línea en blanco
        BlankLine();
       /* h1 = new PdfPCell(new Paragraph(" ",FUENTE_ENCABEZADO));
        //h1.setGrayFill(0.7f);
        h1.setColspan(4);
        h1.setBorder(Rectangle.NO_BORDER);
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(h1);*/

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
        p = new Paragraph("Concept",FUENTE_TITULO_TABLA);
        p.setAlignment(Element.ALIGN_LEFT);

        cell = new PdfPCell();
        cell.setBackgroundColor(gris);
        cell.addElement(p);
        cell.setBorderColor(borde);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);


        // Linea cantidad
        p = new Paragraph("Unit",FUENTE_TITULO_TABLA);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell = new PdfPCell();
        cell.setBackgroundColor(gris);
        cell.setBorderColor(borde);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);


        // Linea precio 1
        p = new Paragraph("Price",FUENTE_TITULO_TABLA);
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

        // añadir los totales
        PdfPCell pie = new PdfPCell(new Paragraph("Base amount "+
                NumberFormat.getCurrencyInstance(Locale.GERMANY).format(dataOneSellBill.getTotalsBill().getBaseEuros())+
                " VAT 20% "+ NumberFormat.getCurrencyInstance(Locale.GERMANY).format(dataOneSellBill.getTotalsBill().getVATEuros())+
                " Total "+NumberFormat.getCurrencyInstance(Locale.GERMANY).format(dataOneSellBill.getTotalsBill().getTotalEuros())
                ,FUENTE_CUERPO));
        pie.setColspan(4);
        //pie.setGrayFill(0.7f);
        pie.setHorizontalAlignment(Element.ALIGN_RIGHT);
        pie.setBorder(Rectangle.NO_BORDER);
        table.addCell(pie);

        pie = new PdfPCell(new Paragraph("Oficial HRMC exchange rates "+
                dataOneSellBill.getTotalsBill().getHMRC_ExchangeRates()+
                " Base amount "+
                NumberFormat.getCurrencyInstance(Locale.UK).format(dataOneSellBill.getTotalsBill().getBasePound())+
                " VAT 20% "+ NumberFormat.getCurrencyInstance(Locale.UK).format(dataOneSellBill.getTotalsBill().getVATPound())+
                " Total "+NumberFormat.getCurrencyInstance(Locale.UK).format(dataOneSellBill.getTotalsBill().getTotalPound())
                ,FUENTE_CUERPO));
        pie.setColspan(4);

        //pie.setGrayFill(0.7f);
        pie.setHorizontalAlignment(Element.ALIGN_RIGHT);
        pie.setBorder(Rectangle.NO_BORDER);
        table.addCell(pie);
            // base imponible
           /* p = new Paragraph(dataOneSellBill.getTupleTotalBill().getStBase(),FUENTE_CUERPO);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.addElement(p);
            table.addCell(cell);


            // VAT
            p = new Paragraph(dataOneSellBill.getTupleTotalBill().getStIVA(), FUENTE_CUERPO);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.addElement(p);
            table.addCell(cell);


            // Total
            p = new Paragraph(dataOneSellBill.getTupleTotalBill().getStTotal(), FUENTE_CUERPO);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.addElement(p);
            table.addCell(cell);*/





        TotalAPagar = TotalAPagar.add(dataOneSellBill.getTupleTotalBill().getTotal());


        // Total general
        PdfPCell pie2 = new PdfPCell(new Paragraph("Total bill ", FUENTE_PIE_TABLA));
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

        PdfPCell h6 = new PdfPCell(new Paragraph(
                dataOneSellBill.getMySelf().getBankName()+"\n"+
                dataOneSellBill.getMySelf().getBankAccount(),FUENTE_DIRECCION_POSTAL));
        //h6.setGrayFill(0.7f);
        h6.setColspan(4);
        h6.setBorder(Rectangle.NO_BORDER);
        h6.setHorizontalAlignment(Element.ALIGN_LEFT);

        table.addCell(h6);

        // Imprimir la dirección de la empresa
        h6 = new PdfPCell(new Paragraph(dataOneSellBill.getMySelf().getIBAN(),FUENTE_DIRECCION_POSTAL));
        //h6.setGrayFill(0.7f);
        h6.setColspan(4);
        h6.setBorder(Rectangle.NO_BORDER);
        h6.setHorizontalAlignment(Element.ALIGN_LEFT);

        table.addCell(h6);

    }
}
