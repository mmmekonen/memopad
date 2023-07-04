package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import patient.Patient;

/** An instance is a JPanel with the list of filenames. */
public class ListPanel extends JPanel {

    /** The list for the panel */
    private JList<Patient> list;

    /** The scroll bar for the list */
    private JScrollPane listScroller;

    /** The folder who's content is currently opened */
    private File folder;

    /** Constructor: an instance is a JPanel with width width, height height,</br>
     * a list of patients, and the text panel. */
    public ListPanel(int width, int height, File folder, TextPanel text) {
        // Create list
        list= new JList<>();
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        list.setFont(list.getFont().deriveFont(13.0f));

        // Create scroll bar
        listScroller= new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(width, height));
        add(listScroller);

        ((FlowLayout) getLayout()).setHgap(0);
        ((FlowLayout) getLayout()).setVgap(0);
        setBounds(0, 0, width, height);
    }

    /** Updates the size of the panel and its components according to the given</br>
     * width and height. */
    public void updateSize(int width, int height) {
        listScroller.setPreferredSize(new Dimension(width, height));
        setBounds(0, 0, width, height);
    }

    /** Updates the data of the list in alphabetical order. */
    public void updateData(List<Patient> data) {
        Collections.sort(data);
        list.setListData(data.toArray(new Patient[0]));
    }

    /** Selects the specified patient from the list. If null is passed, the</br>
     * selection is cleared. */
    public void setSelection(Patient p) {
        list.setSelectedValue(p, true);
    }

    /** Returns the patient that is currently selected within the JList. */
    public Patient getSelection() {
        return list.getSelectedValue();
    }

    /** TODO: write spec. */
    public void setFolder(File f) {
        folder= f;
    }

    /** TODO: write spec. */
    public File getFolder() {
        return folder;
    }

    /** Adds the patient p to the list in alphabetical order. */
    public void add(Patient p) {
        ListModel<Patient> model= list.getModel();
        List<Patient> data= new ArrayList<>();
        for (int i= 0; i < model.getSize(); i++ ) {
            Patient el= model.getElementAt(i);
            if (el != null) {
                data.add(model.getElementAt(i));
            }
        }
        data.add(p);
        Collections.sort(data);
        list.setListData(data.toArray(new Patient[0]));
    }

    /** Adds a MouseListener to the JList list. */
    public void addListener(MouseAdapter ma) {
        list.addMouseListener(ma);
    }

    /** Sorts the data in the list in alphabetical order. */
    public void sort() {
        ListModel<Patient> model= list.getModel();
        List<Patient> data= new ArrayList<>();
        for (int i= 0; i < model.getSize(); i++ ) {
            Patient el= model.getElementAt(i);
            if (el != null) {
                data.add(model.getElementAt(i));
            }
        }
        Collections.sort(data);
        list.setListData(data.toArray(new Patient[0]));
    }

    /** Deletes patient p in the list. */
    public void deletePatient(Patient p) {
        ListModel<Patient> model= list.getModel();
        List<Patient> data= new ArrayList<>();
        for (int i= 0; i < model.getSize(); i++ ) {
            Patient el= model.getElementAt(i);
            if (el != null && !el.equals(p)) {
                data.add(el);
            }
        }
        p.delete();
        list.setListData(data.toArray(new Patient[0]));
    }
}
