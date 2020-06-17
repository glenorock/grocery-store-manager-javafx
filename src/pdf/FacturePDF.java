/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import Views.Constants;
import alimentation.Facture;
import alimentation.Lignefacture;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



/**
 *
 * @author jhy
 */
public class FacturePDF {
    Facture facture;
    ObservableList<Lignefacture> list = FXCollections.observableArrayList();
    final String FILE = Constants.Facture_PDF;
    Document document;
    
    private final Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
    private final Font subFont = new Font(Font.FontFamily.UNDEFINED, 12);

    
    public FacturePDF(Facture facture,ObservableList<Lignefacture> list){
        this.facture = facture;
        this.list.addAll(list);
        document = new Document(new Rectangle(400, 1000));
    }
    
    public void print(){
        try {
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addContent();
            document.close();
            System.out.println("Printing Done ");
        } catch (FileNotFoundException | DocumentException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void addContent() throws DocumentException, IOException {
        addLogo();
        addSpace();
        addBillInformation();
        addSpace();
        addShopInformation();
        addSpace();
        addProducts();
        addSpace();
        addTotal();

    }

    
    private void addProducts()throws BadElementException, DocumentException {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(6f);
        table.setSpacingAfter(6f);
          
        PdfPCell c1 = new PdfPCell(new Phrase("Code Produit"));
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Nom du Produit"));
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Prix Unitaire (FCFA)"));
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Quantité"));
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Prix Total (FCFA)"));
        table.addCell(c1);

        table.setHeaderRows(1);

        list.stream().map((data) -> {
            table.addCell(data.getCodePro().getId().toString());
            return data;
        }).map((data) -> {
            table.addCell(data.getCodePro().toString());
            return data;
        }).map((data) -> {
            table.addCell(data.getCodePro().getPrixVente().toString());
            return data;
        }).map((data) -> {
            table.addCell(""+data.getQte());
            return data;
        }).forEachOrdered((data) -> {
            table.addCell(data.getPrix().toString());
        });
        
        document.add(table);
    }
 
    
    private void addLogo() throws DocumentException, BadElementException, IOException{
        Image image1 = Image.getInstance(Constants.Logo_Image_BW);
        image1.setAlignment(Image.ALIGN_TOP);
        image1.setAlignment(Image.ALIGN_CENTER);
        image1.scalePercent(40, 40);
        document.add(image1);   
    }

    private void addShopInformation() throws DocumentException{
        document.add(new Paragraph("-Yaoundé Melen" ,this.subFont));
        document.add(new Paragraph("-Numero fiscal: 17458963-c" ,this.subFont));
        document.add(new Paragraph("-Contribuable: 128.456" ,this.subFont));
    }
    
    private void addBillInformation() throws DocumentException{
        document.add(new Paragraph("-Date: " + this.facture.getDate(),this.subFont));
        document.add(new Paragraph("-Facture N°: " + this.facture.getIdFac().toString(),this.subFont));
        addSpace();
        if(!this.facture.getClientidClient().toString().equalsIgnoreCase("Default")){
            addClientInformation();
        } 
    }
    
    private void addClientInformation() throws DocumentException{
        document.add(new Paragraph("Reçu Par: "));
        document.add(new Paragraph("-Nom: " +this.facture.getClientidClient().toString(),this.subFont));
        document.add(new Paragraph("-Client N°: " + this.facture.getClientidClient().getId().toString() ,this.subFont));
        document.add(new Paragraph("-Addresse: " + this.facture.getClientidClient().getAdresse(),this.subFont));
        document.add(new Paragraph("-Tel: " + this.facture.getClientidClient().getTel() ,this.subFont));
        
    }
    
    private void addSpace() throws DocumentException{
        document.add(new Paragraph("\n"));
    }

    private void addTotal() throws DocumentException {
        double total = 0;
        double remise = this.facture.getRemise().doubleValue();
        double net;
        
        total = list.stream().map((data) -> data.getPrix().doubleValue()).reduce(total, (accumulator, _item) -> accumulator + _item);
        net = (1-remise)*total;
        
        Paragraph p1 = new Paragraph("Total: " + Double.valueOf(total).intValue()+ " FCFA");
        p1.setAlignment(Paragraph.ALIGN_RIGHT); 
        document.add(p1);
        
        Paragraph p2 = new Paragraph("Remise: " + remise*100 + "%");
        p2.setAlignment(Paragraph.ALIGN_RIGHT); 
        document.add(p2);
        
        Paragraph p3 = new Paragraph("Net à Payer: " + Double.valueOf(net).intValue() + " FCFA");
        p3.setAlignment(Paragraph.ALIGN_RIGHT); 
        document.add(p3);
        
    }
}
