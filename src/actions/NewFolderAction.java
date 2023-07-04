package actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import gui.ToolBar;

/** TODO: write spec */
public class NewFolderAction extends AbstractAction {

    /** TODO: write spec */
    private ToolBar toolbar;

    /** TODO: write spec */
    private File mainDir;

    /** TODO: write spec */
    public NewFolderAction(ToolBar tb, File folder) {
        super("New folder");
        putValue(SHORT_DESCRIPTION, "New folder");
        toolbar= tb;
        mainDir= folder;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s= (String) JOptionPane.showInputDialog(null, "Name for new folder:",
            "New Folder", JOptionPane.PLAIN_MESSAGE, null, null, null);
        if (s != null && s != "") {
            File file= new File(mainDir, s);
            file.mkdirs();
            toolbar.addFolder(s);
        }
    }

}
