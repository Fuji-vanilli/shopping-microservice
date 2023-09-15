package com.shoppingms.billingservice.service;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.shoppingms.billingservice.dto.Client;
import com.shoppingms.billingservice.dto.Order;
import com.shoppingms.billingservice.dto.OrderLine;
import com.shoppingms.billingservice.model.Bill;
import com.shoppingms.billingservice.webClient.WebClientGetter;
import com.shoppingms.billingservice.webClient.WebClientOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.itextpdf.kernel.colors.DeviceGray.*;

@Service
@RequiredArgsConstructor
public class GenerateReporte {
    private final WebClientOrder webClientOrder;
    private final WebClientGetter webClientClient;
    private final String path= "E:\\shopping-ms\\shopping-microservice\\Bill.pdf";
    public void generate(Bill bill) throws FileNotFoundException {
        Client client= webClientClient.getClient(bill.getEmailClient());
        Order order= webClientOrder.getOrder(bill.getCodeOrder());

        PdfWriter pdfWriter= new PdfWriter(this.path);
        PdfDocument pdfDocument= new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document= new Document(pdfDocument);

        float threeCol= 190f;
        float twoColumn= 285f;
        float twoColumn150= twoColumn+150f;
        float fourColum= 150f;
        float[] twoColumnWidth= {twoColumn150, twoColumn};
        //float[] threeColumnWidth= {}
        float[] fullWidth= {threeCol, threeCol, threeCol};
        float[] fourColumnWidth= {fourColum, fourColum, fourColum, fourColum};

        Paragraph oneSpace= new Paragraph("\n");

        Table headerTable= new Table(twoColumnWidth);
        headerTable.addCell(new Cell().add(new Paragraph("Fuji-T")).setBold().setFontSize(15f).setBorder(Border.NO_BORDER));

        Table headerRight= new Table(new float[]{twoColumn, twoColumn});
        SimpleDateFormat formato= new SimpleDateFormat("dd/MM/yyyy");
        Date date= Date.from(order.getDate());
        String dateString= formato.format(date);
        headerRight.addCell(getCell10left("Invoice n°: ", true));
        headerRight.addCell(getCell10left("INV-"+bill.getCode(), false));
        headerRight.addCell(getCell10left("Date: ", true));
        headerRight.addCell(getCell10left(dateString, false));
        headerTable.addCell(new Cell().add(headerRight).setBorder(Border.NO_BORDER));
        document.add(headerTable);
        document.add(oneSpace);

        Table divider= new Table(fullWidth);
        Border bg= new SolidBorder(GRAY, 1f/2f);
        document.add(divider.setBorder(bg));
        document.add(oneSpace);

        Table billingShippingTab= new Table(twoColumnWidth);
        billingShippingTab.addCell(new Cell().add(new Paragraph("Billing Information")).setBold().setBorder(Border.NO_BORDER));
        billingShippingTab.addCell(new Cell().add(new Paragraph("Shipping Information")).setBold().setBorder(Border.NO_BORDER));

        document.add(billingShippingTab);
        document.add(oneSpace);

        Table infoGen= new Table(twoColumnWidth);
        infoGen.addCell(getCell10left("Company", true));
        infoGen.addCell(getCell10left("Name", true));
        infoGen.addCell(getCell10left("Tech-No-Logy", false));
        infoGen.addCell(getCell10left(client.getFirstname()+" "+client.getLastname(), false));
        infoGen.addCell(getCell10left("Name", true));
        infoGen.addCell(getCell10left("Address", true));
        infoGen.addCell(getCell10left("Fuji Vanilli", false));
        infoGen.addCell(getCell10left(client.getAdress().getAdress1()+"\n"+client.getAdress().getCity()+" - "+client.getAdress().getCountry(), false));
        document.add(infoGen);

        Table lastInfo= new Table(new float[]{twoColumn});
        lastInfo.addCell(getCell10left("Address", true));
        lastInfo.addCell(getCell10left("Lot XV25 Andriana \nTananarive \nMadagascar", false));
        lastInfo.addCell(getCell10left("Email", true));
        lastInfo.addCell(getCell10left(bill.getEmailClient(), false));
        document.add(lastInfo);
        document.add(oneSpace);

        Table divider2= new Table(fullWidth);
        Border bg2= new DashedBorder(0.5f);
        document.add(divider2.setBorder(bg2));
        document.add(oneSpace);

        Table headerDetailsOrder= new Table(fourColumnWidth);
        headerDetailsOrder.setBackgroundColor(BLACK, 0.5f);
        headerDetailsOrder.addCell(getHeaderTable("Designation", 1));
        headerDetailsOrder.addCell(getHeaderTable("Unit Price", 2));
        headerDetailsOrder.addCell(getHeaderTable("Quantity", 2));
        headerDetailsOrder.addCell(getHeaderTable("Price", 3));
        document.add(headerDetailsOrder);

        final List<OrderLine> orderLines = order.getOrderLines();

        Table productDetails= new Table(fourColumnWidth);
        for(OrderLine orderLine: orderLines){
            productDetails.addCell(getElementTable(orderLine.getProduct().getName(), 1));
            productDetails.addCell(getElementTable(String.valueOf(orderLine.getPrice()), 2));
            productDetails.addCell(getElementTable(String.valueOf(orderLine.getQuantity()), 2));
            productDetails.addCell(getElementTable(String.valueOf(orderLine.getPrice()), 3));
        }
        document.add(productDetails);

        Table divider3= new Table(twoColumnWidth);
        divider3.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        divider3.addCell(new Cell().add(divider2).setBorder(Border.NO_BORDER));
        document.add(divider3);

        Table totalTable= new Table(fourColumnWidth);
        totalTable.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        totalTable.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        totalTable.addCell(new Cell().add(new Paragraph("Total")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        totalTable.addCell(new Cell().add(new Paragraph(String.valueOf(order.getTotalPrice()))).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
        document.add(totalTable);
        document.add(divider2);
        document.add(oneSpace);
        document.add(divider);
        document.add(oneSpace);

        document.add(new Paragraph("TERMS AND CONDITIONS").setFontSize(15f).setBold());
        document.add(new Paragraph("1. Le client s'engage à effectuer le paiement intégral du montant dû dans un délai de 15 jours à compter de la date de réception de cette facture."));
        document.add(new Paragraph("2. Les produits/services sont fournis \"tels quels\" sans aucune garantie, expresse ou implicite, sauf indication contraire expresse dans un accord écrit entre les deux parties"));
        document.add(new Paragraph("3. Le fournisseur ne sera pas responsable des dommages indirects ou consécutifs découlant de l'utilisation des produits/services fournis."));

        document.close();
        System.out.println("invoice pdf created");
    }
    public Cell getHeaderText(String value, float size){
        return new Cell().add(new Paragraph(value)).setFontSize(size).setBorder(Border.NO_BORDER).setFontColor(WHITE).setBold().setTextAlignment(TextAlignment.LEFT);
    }
    public Cell getHeaderTable(String value, int alignment){
        return new Cell().add(new Paragraph(value)).setFontColor(WHITE).setBold().setBorder(Border.NO_BORDER).setTextAlignment(
                alignment== 1 ? TextAlignment.LEFT : alignment== 2 ? TextAlignment.CENTER : TextAlignment.RIGHT
        );
    }
    public Cell getElementTable(String value, int alignment){
        return new Cell().add(new Paragraph(value)).setBorder(Border.NO_BORDER).setTextAlignment(
                alignment== 1 ? TextAlignment.LEFT : alignment== 2 ? TextAlignment.CENTER : TextAlignment.RIGHT
        );
    }
    public Cell getCell10left(String text, boolean isBold){
        Cell cell= new Cell().add(new Paragraph(text)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER);
        return isBold ? cell.setBold() : cell;
    }
}
