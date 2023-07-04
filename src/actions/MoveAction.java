package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.swing.AbstractAction;

import gui.ListPanel;
import gui.TextPanel;
import patient.Patient;

/*TODO: write spec */
public class MoveAction extends AbstractAction {

    /** The text panel of the gui */
    private TextPanel text;

    /** The list panel of the gui */
    private ListPanel list;

    /** TODO: write spec */
    private File file;

    /** TODO: write spec */
    private File dest;

    /** TODO: Constructor: . */
    public MoveAction(TextPanel t, ListPanel l, File f, File d) {
        super(d.getName());
        text= t;
        list= l;
        file= f;
        dest= d;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Path pathFile= file.toPath();
            Path pathDest= dest.toPath();
            File currentDir= file.getParentFile();
            Files.move(pathFile, pathDest.resolve(pathFile.getFileName()),
                StandardCopyOption.REPLACE_EXISTING);
            List<Patient> patients= Patient.generatePatients(currentDir);
            list.updateData(patients);
            text.clear();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
