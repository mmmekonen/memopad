package actions;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import gui.ListPanel;
import gui.TextPanel;
import patient.Patient;

/** An instance is an Action that responds to mouse clicks on the list. */
public class ClickAction extends MouseAdapter {

    /** The text panel of the gui */
    private TextPanel text;

    /** The list panel of the gui */
    private ListPanel list;

    /** TODO: write spec */
    private File mainDir;

    /** Constructor: an instance is an action that responds to mouse clicks on</br>
     * the list and updates the TextPanel t and ListPanel l accordingly. */
    public ClickAction(TextPanel t, ListPanel l, File folder) {
        text= t;
        list= l;
        mainDir= folder;
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        // TODO: write each part as a helper?

        JList<?> jl= (JList<?>) evt.getSource();
        Rectangle r= jl.getCellBounds(0, jl.getLastVisibleIndex());
        Patient p= (Patient) jl.getSelectedValue();

        // Save currently opened file
        if (text.currentPatient() != null) {
            text.saveFile();
            text.currentPatient().setName();
            list.sort();
        }

        // If click in cell bounds
        if (r != null && r.contains(evt.getPoint())) {

            // If left click
            if (evt.getButton() == MouseEvent.BUTTON1) {

                // load selected file
                jl.setSelectedValue(p, true);
                text.loadFile(p);

                // If right click
            } else if (SwingUtilities.isRightMouseButton(evt)) {
                int row= jl.locationToIndex(evt.getPoint());
                jl.setSelectedIndex(row);
                Patient p2= (Patient) jl.getSelectedValue();
                JPopupMenu menu= new JPopupMenu();

                JMenuItem item= new JMenuItem(new DeleteFileAction(text, list));
                item.setIcon(null);
                menu.add(item);

                JMenu submenu= new JMenu("Move to");
                File[] files= mainDir.listFiles(); // TODO: fix to hide files not folders
                for (File f : files) {
                    item= new JMenuItem(new MoveAction(text, list, p2.file(), f));
                    submenu.add(item);
                }
                menu.add(submenu);
                menu.show(list, evt.getPoint().x, evt.getPoint().y);
            }

            // Click outside of cell bounds
        } else {
            jl.setSelectedValue(null, true);
            text.clear();
        }
    }
}
