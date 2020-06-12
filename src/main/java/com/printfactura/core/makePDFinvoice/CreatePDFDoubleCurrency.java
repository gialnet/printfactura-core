package com.printfactura.core.makePDFinvoice;

import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.printfactura.core.domain.sales.DetailSalesBill;
import com.printfactura.core.domain.sales.SalesBill;

import javax.naming.NamingException;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CreatePDFDoubleCurrency {

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

    private final SalesBill salesBill;

    public CreatePDFDoubleCurrency(SalesBill salesBill) {
        this.salesBill = salesBill;
    }


    public byte[] doit() throws DocumentException, IOException, NamingException
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

    /**
     * Add PDF file to Bills database table
     * @throws DocumentException
     * @throws IOException
     * @throws SQLException
     */
    private void Save() throws DocumentException, IOException
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

        PdfPCell h1 = new PdfPCell(new Paragraph(salesBill.getMySelf().getCompanyName(), FUENTE_ENCABEZADO));
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
    }

    private void PhysicalAddress(){

        // Address street
        PdfPCell address1 = new PdfPCell(new Paragraph(salesBill.getMySelf().getAddress(), FUENTE_MYSELF));
        address1.setColspan(4);
        address1.setBorder(Rectangle.NO_BORDER);
        address1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(address1);

        // Address city and post code
        address1 = new PdfPCell(new Paragraph(salesBill.getMySelf().getCity()+
                ", "+ salesBill.getMySelf().getPostCode(), FUENTE_MYSELF));
        address1.setColspan(4);
        address1.setBorder(Rectangle.NO_BORDER);
        address1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(address1);

        // Address country
        address1 = new PdfPCell(new Paragraph(salesBill.getMySelf().getCountry(), FUENTE_MYSELF));
        address1.setColspan(4);
        address1.setBorder(Rectangle.NO_BORDER);
        address1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(address1);

        // Company identification
        address1 = new PdfPCell(new Paragraph(salesBill.getMySelf().getIdentification(), FUENTE_MYSELF));
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
    private void MakeTable()
    {

        DocHead();

        //PdfPCell h1;

        PhysicalAddress();

        // línea en blanco
        BlankLine();


        // Invoice number
        PdfPCell h2 = new PdfPCell(new Paragraph("Invoice nº: "+ salesBill.getHeadSalesBill().getBillNumber().trim()
                +"     Date: "
                + salesBill.getHeadSalesBill().getDate(),FUENTE_GRIS_OSCURO));
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

        // Datos del cliente
        PdfPCell h4 = new PdfPCell(new Paragraph("-Client details-\n"+
                salesBill.getCustomer().getCompanyName()+
                "\n"+ salesBill.getCustomer().getAddress()+
                "\n"+ salesBill.getCustomer().getPostCode()+" "+
                salesBill.getCustomer().getCity()+
                "\n"+ salesBill.getCustomer().getCountry()+
                "\n"+ salesBill.getCustomer().getIdentification()
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

    }

    /**
     *
     * @return
     * @throws SQLException
     * @throws DocumentException
     */
    private int printDetailBill() throws DocumentException
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

        List<DetailSalesBill> LineasFactura = salesBill.getDetailSalesBill();

        int j=1;

        for (DetailSalesBill lineasFact : LineasFactura) {

            j++;
            // Linea de Concepto
            p = new Paragraph(lineasFact.getConcept(),FUENTE_CUERPO);
            p.setAlignment(Element.ALIGN_LEFT);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.addElement(p);
            table.addCell(cell);


            // Linea cantidad
            p = new Paragraph(String.valueOf(lineasFact.getUnit()),FUENTE_CUERPO);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.addElement(p);
            table.addCell(cell);


            // Linea precio 1
            p = new Paragraph(String.valueOf(lineasFact.getPrice()),FUENTE_CUERPO);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.addElement(p);
            table.addCell(cell);

            // Linea Importe 1
            p = new Paragraph(String.valueOf(lineasFact.getAmount()),FUENTE_CUERPO);
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
    private void printTotales() throws
            DocumentException
    {

        //TupleTotalBill TotalesFactura = myGetDataSellBill.getPieFact(id_fact);

        BigDecimal TotalAPagar = BigDecimal.ZERO;
        Color azul_oscuro = new Color(39, 83, 146);

        // añadir los totales
        PdfPCell pie = new PdfPCell(new Paragraph("Base  "+
                NumberFormat.getCurrencyInstance(Locale.GERMANY).format(salesBill.getTotalSalesBill().getBaseEuros())+
                " VAT 20% "+ NumberFormat.getCurrencyInstance(Locale.GERMANY).format(salesBill.getTotalSalesBill().getVATEuros())+
                " Total "+NumberFormat.getCurrencyInstance(Locale.GERMANY).format(salesBill.getTotalSalesBill().getTotalEuros())
                ,FUENTE_TITULO));
        pie.setColspan(4);
        //pie.setGrayFill(0.7f);
        pie.setHorizontalAlignment(Element.ALIGN_RIGHT);
        pie.setBorder(Rectangle.NO_BORDER);
        pie.setBackgroundColor(GRAY);
        table.addCell(pie);

        pie = new PdfPCell(new Paragraph("HRMC exchange rates "+
                salesBill.getTotalSalesBill().getHMRC_ExchangeRates()+
                " Base "+
                NumberFormat.getCurrencyInstance(Locale.UK).format(salesBill.getTotalSalesBill().getBasePound())+
                " VAT 20% "+ NumberFormat.getCurrencyInstance(Locale.UK).format(salesBill.getTotalSalesBill().getVATPound())+
                " Total "+NumberFormat.getCurrencyInstance(Locale.UK).format(salesBill.getTotalSalesBill().getTotalPound())
                ,FUENTE_TITULO));
        pie.setColspan(4);

        //pie.setGrayFill(0.7f);
        pie.setHorizontalAlignment(Element.ALIGN_RIGHT);
        pie.setBorder(Rectangle.NO_BORDER);
        table.addCell(pie);

        TotalAPagar = TotalAPagar.add(salesBill.getTotalSalesBill().getTotalEuros());

        p = new Paragraph(
                "VAT "+NumberFormat.getCurrencyInstance(Locale.UK).format(salesBill.getTotalSalesBill().getVATPound())+
                "   Total bill " + NumberFormat.getCurrencyInstance(Locale.GERMANY).format(TotalAPagar), FUENTE_PIE_TABLA);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell = new PdfPCell();
        cell.setColspan(4);
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

    private void printLineasBlancos() throws DocumentException
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
    private void pintPie() throws NamingException
    {

        PdfPCell h6 = new PdfPCell(new Paragraph(
                salesBill.getMySelf().getBankName()+"\n"+
                salesBill.getMySelf().getBankAccount(),FUENTE_DIRECCION_POSTAL));
        //h6.setGrayFill(0.7f);
        h6.setColspan(4);
        h6.setBorder(Rectangle.NO_BORDER);
        h6.setHorizontalAlignment(Element.ALIGN_LEFT);

        table.addCell(h6);

        // Imprimir la dirección de la empresa
        h6 = new PdfPCell(new Paragraph("IBAN "+ salesBill.getMySelf().getIBAN(),FUENTE_TITULO));
        //h6.setGrayFill(0.7f);
        h6.setColspan(4);
        h6.setBorder(Rectangle.NO_BORDER);
        h6.setHorizontalAlignment(Element.ALIGN_LEFT);

        table.addCell(h6);

    }
}
