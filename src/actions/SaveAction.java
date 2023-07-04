package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import gui.ListPanel;
import gui.TextPanel;

/** An instance is an Action that saves a file. */
public class SaveAction extends AbstractAction {

    /** The text panel of the gui */
    private TextPanel text;

    /** The list panel of the gui */
    private ListPanel list;

    /** Constructor: an instance is an action that saves the file currently</br>
     * opened in the TextPanel t and sorts the updated ListPanel l. */
    public SaveAction(TextPanel t, ListPanel l) {
        super("Save",
            new ImageIcon(
                SaveAction.class.getResource("/toolbarButtonGraphics/general/Save24.gif")));
        putValue(SHORT_DESCRIPTION, "Save file");
        text= t;
        list= l;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (text.currentPatient() != null) {
            text.saveFile();
            text.currentPatient().setName();
            list.sort();
            list.setSelection(text.currentPatient());
        }
    }

}
