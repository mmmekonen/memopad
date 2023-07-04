package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import gui.ListPanel;
import gui.TextPanel;
import patient.Patient;

/** An instance is an Action that creates a new file. */
public class NewFileAction extends AbstractAction {

    /** The text panel of the gui */
    private TextPanel text;

    /** The list panel of the gui */
    private ListPanel list;

    /** Constructor: an instance is an action that creates a new file and adds</br>
     * it to the ListPanel l. */
    public NewFileAction(TextPanel t, ListPanel l) {
        super("New file",
            new ImageIcon(
                NewFileAction.class.getResource("/toolbarButtonGraphics/general/New24.gif")));
        putValue(SHORT_DESCRIPTION, "New file");
        text= t;
        list= l;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (text.currentPatient() != null) {
                text.saveFile();
                text.currentPatient().setName();
                list.sort();
            }
            File folder= list.getFolder();
            File file= File.createTempFile("file", ".txt", folder);
            Patient p= new Patient(file);
            list.add(p);
            list.setSelection(p);
            text.loadFile(p);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
