package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import gui.ListPanel;
import gui.TextPanel;
import patient.Patient;

/** An instance is an Action that deletes a file. */
public class DeleteFileAction extends AbstractAction {

    /** The text panel of the gui */
    private TextPanel text;

    /** The list panel of the gui */
    private ListPanel list;

    /** Constructor: an instance is an action that deletes the file currently</br>
     * opened in the TextPanel t and updates the ListPanel l. */
    public DeleteFileAction(TextPanel t, ListPanel l) {
        super("Delete file",
            new ImageIcon(
                DeleteFileAction.class.getResource("/toolbarButtonGraphics/general/Delete24.gif")));
        putValue(SHORT_DESCRIPTION, "Delete file");
        text= t;
        list= l;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Patient p= list.getSelection();
        if (p != null) {
            Object[] options= new Object[] { "Yes", "No" };
            int n= JOptionPane.showOptionDialog(null,
                "Are you sure you want to delete file \"" + p + "\"?",
                "Warning", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE, null, options, options[1]);
            if (n == 0) {
                list.deletePatient(p);
                text.clear();
            }
        }
    }

}
