package actions;

import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.tools.TextToPDF;

import gui.GUI;
import gui.ListPanel;

/** TODO: write spec */
public class PrintAction extends AbstractAction {

    /** The list panel of the gui */
    private ListPanel list;

    /** The file to print */
    private File printFile;

    /** TODO: write spec */
    private File mainDir;

    /** TODO: Constructor:. */
    public PrintAction(ListPanel l, File folder) {
        super("Print",
            new ImageIcon(
                NewFileAction.class.getResource("/toolbarButtonGraphics/general/Print24.gif")));
        putValue(SHORT_DESCRIPTION, "Print");
        list= l;
        mainDir= folder;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        createPrintFile();
        try (FileReader reader= new FileReader(printFile);) {
            try (PDDocument document= new TextToPDF().createPDFFromText(reader);) {
                PrintService service= findPrintService();
                if (service == null) { return; }
                PrinterJob job= PrinterJob.getPrinterJob();
                job.setPageable(new PDFPageable(document));
                job.setPrintService(service);
                job.print();
            } catch (IOException | PrinterException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    /** TODO: write spec */
    private PrintService findPrintService() {
        PrintRequestAttributeSet attributes= new HashPrintRequestAttributeSet();
        PrintService[] services= PrintServiceLookup.lookupPrintServices(
            null, attributes);
        if (services.length <= 0) { return null; }

        PrintService defaultPrintService= PrintServiceLookup
            .lookupDefaultPrintService();

        PrintService service= ServiceUI.printDialog(null, GUI.POSITION_X + 5,
            GUI.POSITION_Y + 5,
            services, defaultPrintService, null,
            attributes);
        return service;
    }

    /** TODO: write spec */
    private boolean createPrintFile() {
        File folder= list.getFolder();
        try {
            printFile= File.createTempFile("print", ".txt", mainDir);
            printFile.deleteOnExit();
            BufferedWriter bw= new BufferedWriter(new FileWriter(printFile));
            for (File file : folder.listFiles()) {
                BufferedReader br= new BufferedReader(new FileReader(file));
                String lin= br.readLine();
                while (lin != null) {
                    bw.write(lin);
                    bw.newLine();
                    lin= br.readLine();
                }
                br.close();
                bw.newLine();
                bw.newLine();
            }
            bw.close();
            return true;
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return false;
    }

}
