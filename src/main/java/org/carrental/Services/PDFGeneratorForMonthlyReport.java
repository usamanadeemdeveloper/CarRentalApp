package org.carrental.Services;

import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPCell;
import org.carrental.Model.VehicleOwner;

import javax.swing.JTable;

public class PDFGeneratorForMonthlyReport {


    public PDFGeneratorForMonthlyReport(JTable jTable, String pdfName, Double totalAmount, List<VehicleOwner> commissionList) throws Exception {
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

        Paragraph total = new Paragraph("Total Amount = " + totalAmount);
        Paragraph commission = new Paragraph("Total Commission = " + commissionList.get(0).getCommission());

        Double profitAmount = totalAmount - commissionList.get(0).getCommission();
        Paragraph profit = new Paragraph("Total profit = " + profitAmount);

        document.add(table);
        document.add(total);
        document.add(commission);
        document.add(profit);
        document.close();
    }
}
