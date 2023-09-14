package com.shoppingms.billingservice.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.shoppingms.billingservice.model.Bill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor @NoArgsConstructor
@RequiredArgsConstructor
public class GenerateReport {
    private final String path= "E:\\shopping-ms\\shopping-microservice";
    public void generate(Bill bill){
        Document document= new Document(PageSize.A4);

        document.open();

    }

}
