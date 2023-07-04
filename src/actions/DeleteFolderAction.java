package actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import gui.GUI;
import gui.ToolBar;

/** TODO: write spec. */
public class DeleteFolderAction extends AbstractAction {

    /** TODO: write spec */
    private ToolBar toolbar;

    /** TODO: write spec */
    public DeleteFolderAction(ToolBar tb) {
        super("Delete folder");
        putValue(SHORT_DESCRIPTION, "Delete folder");
        toolbar = tb;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String fname = (String) JOptionPane.showInputDialog(null, "Select folder to delete:",
                "Delete Folder", JOptionPane.PLAIN_MESSAGE, null, GUI.folderNames(), null);
        if (fname != null && fname != "") {
            Object[] options = new Object[] { "Yes", "No" };
            int n = JOptionPane.showOptionDialog(null,
                    "Are you sure you want to delete folder \"" + fname +
                            "\" and all of its files?",
                    "Warning", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[1]);
            if (n == 0) {
                File folder = new File(System.getProperty("user.home") + "\\memopad\\" + fname);
                File[] files = folder.listFiles();
                for (File f : files) {
                    f.delete();
                }
                folder.delete();
                toolbar.removeFolder(fname);
            }
        }
    }

}
