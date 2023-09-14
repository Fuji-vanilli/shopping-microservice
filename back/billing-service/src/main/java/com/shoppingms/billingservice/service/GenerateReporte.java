package com.shoppingms.billingservice.service;

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
import com.shoppingms.billingservice.dto.Order;
import com.shoppingms.billingservice.dto.OrderLine;
import com.shoppingms.billingservice.model.Bill;
import com.shoppingms.billingservice.webClient.WebClientOrder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

import static com.itextpdf.kernel.colors.DeviceGray.*;

@Service
@RequiredArgsConstructor
public class GenerateReporte {
    private final WebClientOrder webClient;
    private final String path= "E:\\shopping-ms\\shopping-microservice";
    public void generate(Bill bill) throws FileNotFoundException {
        PdfWriter pdfWriter= new PdfWriter(this.path);
        PdfDocument pdfDocument= new PdfDocument(pdfWriter);
        Document document= new Document(pdfDocument);

        float threeCol= 190f;
        float twoColumn= 285f;
        float twoColumn150= twoColumn+150f;
        float fourColum= 150f;
        float[] twoColumnWidth= {twoColumn150, twoColumn};
        float[] fullWidth= {threeCol, threeCol, threeCol};
        float[] fourColumnWidth= {fourColum, fourColum, fourColum, fourColum};

        Paragraph oneSpace= new Paragraph("\n");

        Table headerTable= new Table(twoColumnWidth);
        headerTable.addCell(getHeaderText("FUJI-T", 18f));

        Table headerRight= new Table(new float[]{twoColumn, twoColumn});
        headerRight.addCell(getCell10left("Invoice n°: ", true));
        headerRight.addCell(getCell10left("INV-10254", false));
        headerRight.addCell(getCell10left("Date: ", true));
        headerRight.addCell(getCell10left("09/09/2023", false));
        headerTable.addCell(headerRight.setBorder(Border.NO_BORDER));
        document.add(headerTable.setMarginBottom(12f));

        Table divider= new Table(fullWidth);
        Border bg= new SolidBorder(GRAY, 1f/2f);
        divider.setBorder(bg);
        document.add(divider.setMarginBottom(12f));

        Table billingShippingTab= new Table(twoColumnWidth);
        billingShippingTab.addCell(getHeaderText("Billing Information", 15f));
        billingShippingTab.addCell(getHeaderText("Shipping Information", 15f));
        document.add(billingShippingTab.setMarginBottom(20f));

        Table infoGen= new Table(twoColumnWidth);
        infoGen.addCell(getCell10left("Company", true));
        infoGen.addCell(getCell10left("Name", false));
        infoGen.addCell(getCell10left("Name", true));
        infoGen.addCell(getCell10left("Address", false));
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

        Order order= webClient.getOrder(bill.getCodeOrder());
        final List<OrderLine> orderLines = order.getOrderLines();

        Table productDetails= new Table(fourColumnWidth);
        for(OrderLine orderLine: orderLines){
            productDetails.addCell(getElementTable(orderLine.getProduct().getName(), 1));
            productDetails.addCell(getElementTable(String.valueOf(orderLine.getPrice()), 1));
            productDetails.addCell(getElementTable(String.valueOf(orderLine.getQuantity()), 1));
            productDetails.addCell(getElementTable(String.valueOf(orderLine.getPrice()), 1));
        }
        document.add(productDetails);
        document.add(oneSpace);

        document.close();
    }
    public Cell getHeaderText(String value, float size){
        return new Cell().add(new Paragraph(value)).setFontSize(size).setBorder(Border.NO_BORDER).setFontColor(WHITE).setBold().setTextAlignment(TextAlignment.LEFT);
    }
    public Cell getHeaderTable(String value, int alignment){
        return new Cell().add(new Paragraph(value)).setBold().setBorder(Border.NO_BORDER).setTextAlignment(
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
