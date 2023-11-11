package org.example;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import javax.xml.bind.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class XMLtoTXTConverter {

    public static void main(String[] args) {
        // Create and show the GUI
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("XML to TXT Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a file chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose XML File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML Files", "xml"));

        // Show the file chooser dialog
        int userSelection = fileChooser.showOpenDialog(frame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Call the conversion method with the selected file
            convertXMLtoTXT(selectedFile);
        }

        frame.pack();
        frame.setVisible(false); // GUI is no longer needed once the file is selected
    }
    public static void convertXMLtoTXT(File inputFile) {
        try {
            JAXBContext context = JAXBContext.newInstance(DocumentWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Deserialize XML from the input file
            DocumentWrapper documentWrapper = (DocumentWrapper) unmarshaller.unmarshal(inputFile);

            // Extract the Invoice from the wrapper
            List<Invoice> invoices = documentWrapper.getInvoices();


            // Scrierea datelor în fișierul TXT
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));

            // [InfoPachet]
            writer.write("[InfoPachet]");
            writer.newLine();
            writer.write("AnLucru=" + invoices.get(0).getIssueDate().substring(0, 4));
            writer.newLine();
            writer.write("LunaLucru=" + invoices.get(0).getIssueDate().substring(5, 7));
            writer.newLine();
            writer.write("TipDocument=FACTURA INTRARE");
            writer.newLine();
            writer.write("TotalFacturi=" + invoices.size());
            writer.newLine();
            // ...
            for (int invoiceIndex = 0; invoiceIndex < invoices.size(); invoiceIndex++) {
                Invoice invoice = invoices.get(invoiceIndex);
            // [Factura_n]
            writer.newLine();
            writer.write("[Factura_" + (invoiceIndex + 1) + "]");
            writer.newLine();
            String idDigitsOnly = invoice.getID().replaceAll("\\D", "");

            String last4Digits = idDigitsOnly.substring(Math.max(0, idDigitsOnly.length() - 4));

            writer.write("NrDoc=" + last4Digits);
            writer.newLine();
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date date = inputFormat.parse(invoice.getIssueDate());
            String formattedIssueDate = outputFormat.format(date);

            writer.write("Data=" + formattedIssueDate);

            writer.newLine();
            writer.write("CodFurnizor=1");
            List<InvoiceLine> invoiceLines = invoice.getInvoiceLines();
            String currency = invoice.getDocumentCurrencyCode();

            if(currency.equals("RON"))
                currency="Lei";
            else
                currency="Eur";

            writer.newLine();
            writer.write("Moneda=" + currency);
            writer.newLine();
            writer.write("Scadenta=" + formattedIssueDate);

            if(invoiceLines.get(0).getItem().getClassifiedTaxCategory().getPercent().equals("0.00")) {
                writer.newLine();
                writer.write("TotalArticole=" + invoiceLines.size());
                writer.newLine();
                writer.write("TaxareInversa=D");
            }
            else{
                writer.newLine();
                writer.write("TotalArticole=2");
                writer.newLine();
                writer.write("TaxareInversa=N");
            }

            writer.newLine();
            writer.write("[Items_"+ (invoiceIndex + 1) +"]");
            String itemCode= "0";
            float tvas[] = new float[999];
            float totalTvaValue = 0.0f;
            float totalItemValue = 0.0f;
            float percent=0.0f;
            for (int i = 0; i < invoiceLines.size(); i++) {
                InvoiceLine invoiceLine = invoiceLines.get(i);

                //Calculam tva-ul

                float tvaValue = Float.parseFloat(invoiceLine.getLineExtensionAmount());
                percent = Float.parseFloat(invoiceLine.getItem().getClassifiedTaxCategory().getPercent());
                tvaValue = (tvaValue * percent)/100;
                BigDecimal decimalTvaValue = new BigDecimal(tvaValue);
                BigDecimal roundedTvaValue = decimalTvaValue.setScale(4, RoundingMode.HALF_UP);
                tvas[i] = roundedTvaValue.floatValue();



                //System.out.println(tvaValue);
                if(tvas[0]!=0.0) {
                    if (invoiceLine.getInvoicedQuantity().getUnitCode().equals("LTR")) {
                        itemCode = "2";
                    }
                    float ItemValue = Float.parseFloat(invoiceLine.getLineExtensionAmount());
                    totalItemValue += ItemValue;
                }
                else{
                    writer.newLine();
                    writer.write("Item_" + (i+1) + "=4;"  +  "Buc;" + "1.00;" + invoiceLine.getLineExtensionAmount() + ";;0;");
                }
            }

            if(tvas[0]!=0.0){

                totalTvaValue=(totalItemValue*percent)/100;
                totalItemValue+=totalTvaValue;

                writer.newLine();
                writer.write("Item_1"  + "=2;" + "Lei;" + "1.00;" + totalItemValue + ";;0;");



                totalItemValue=0;

                writer.newLine();
                writer.write("Item_2"  + "=3;" + "Buc" + ";" + "1.00;" + "-" + totalTvaValue + ";;0;");

                totalTvaValue=0;

            }

            for (int i = 0; i < tvas.length; i++) {
                tvas[i] = 0;
            }}

            // Închidem fișierul TXT
            writer.close();
            System.out.println("Conversie XML -> TXT finalizată cu succes.");
            System.exit(0);
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
