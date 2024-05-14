package org.carrental.UI;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.carrental.Services.VehicleOwnerService;
import org.carrental.Services.VehicleService;

import javax.swing.*;
import java.io.FileOutputStream;

public class PDFGeneratorForAllBookedCars {
    private final VehicleService vehicleService = new VehicleService();
    private final VehicleOwnerService vehicleOwnerService = new VehicleOwnerService();
    public PDFGeneratorForAllBookedCars(JTable jTable, String pdfName) throws Exception{
        Document document = new Document(PageSize.A4.rotate());

        PdfWriter.getInstance(document, new FileOutputStream(pdfName));
        document.open();

        PdfPTable table = new PdfPTable(jTable.getColumnCount());
        table.setWidthPercentage(100);

        for (int i = 0; i < jTable.getColumnCount(); i++) {
            table.addCell(new PdfPCell(new Paragraph(jTable.getColumnName(i))));
        }

        for (int i = 0; i < jTable.getRowCount(); i++) {
            for (int j = 0; j < jTable.getColumnCount(); j++) {
                table.addCell(new PdfPCell(new Paragraph(jTable.getValueAt(i, j).toString())));
            }
        }

        Paragraph totalBookedCars = new Paragraph("Most Booked Cars = " + vehicleService.getMostBookedCars());
        Paragraph lowestBookedCars = new Paragraph("Lowest Booked Cars = " + vehicleService.getLowestBookedCars());
        Paragraph highestProfitGivenCar = new Paragraph("Highest Profit Given Car = " + vehicleService.getHighestProfitGivenCars());
        Paragraph highestCommissionHolderOwner = new Paragraph("Highest Commission Of Owner Of One Day= " + vehicleOwnerService.getHighestCommission());


        document.add(table);
        document.add(totalBookedCars);
        document.add(lowestBookedCars);
        document.add(highestProfitGivenCar);
        document.add(highestCommissionHolderOwner);
        document.close();
    }
}
