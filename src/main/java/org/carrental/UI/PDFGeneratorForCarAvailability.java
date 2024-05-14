package org.carrental.UI;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.carrental.Repository.VehicleDao;
import org.carrental.Services.VehicleOwnerService;

import javax.swing.*;
import java.io.FileOutputStream;

public class PDFGeneratorForCarAvailability {
    private final VehicleDao vehicleDao = new VehicleDao();
    private final VehicleOwnerService vehicleOwnerService = new VehicleOwnerService();
    public PDFGeneratorForCarAvailability(String pdfName, JTable jTable) throws Exception{
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


        Paragraph totalCarAvailable = new Paragraph("Total Cars Available = " + vehicleDao.getTotalAvailableCars());
        Paragraph carOwners = new Paragraph("Car Owners = " +  vehicleOwnerService.getAvailableOwnerNames());

        document.add(table);
        document.add(totalCarAvailable);
        document.add(carOwners);
        document.close();
    }
}
