package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;

import gui.ListPanel;
import gui.TextPanel;
import patient.Patient;

/** An instance is an Action that loads a new folder to the list. */
public class LoadFolderAction extends AbstractAction {

    /** The text panel of the gui */
    private TextPanel text;

    /** The list panel of the gui */
    private ListPanel list;

    /** Constructor: an instance is an action that loads the available files in</br>
     * a selected folder, clears the TextPanel t, and updates the ListPanel l. */
    public LoadFolderAction(TextPanel t, ListPanel l) {
        super("Load file");
        text= t;
        list= l;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (text.currentPatient() != null) {
            text.saveFile();
            text.currentPatient().setName();
            list.sort();
        }
        JComboBox<?> cb= (JComboBox<?>) e.getSource();
        File folder= new File(System.getProperty("user.home") + "\\memopad\\" +
            cb.getSelectedItem());
        List<Patient> patients= Patient.generatePatients(folder);
        list.updateData(patients);
        list.setFolder(folder);
        text.clear();
    }

}
