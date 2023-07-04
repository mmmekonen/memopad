package gui;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

import actions.DeleteFileAction;
import actions.LoadFolderAction;
import actions.NewFileAction;
import actions.PrintAction;
import actions.SaveAction;

/** An instance is a JToolBar that contains JButtons and a JComboBox. */
public class ToolBar extends JToolBar {

    /** The button for saving the currently open file */
    private JButton save;

    /** The button for creating a new file */
    private JButton newFile;

    /** The button for deleting the currently open file */
    private JButton delete;

    /** The button for printing the files in the currently open folder */
    private JButton print;

    /** The combo box for selecting the current folder */
    private JComboBox<String> folderSelect;

    /** Constructor: an instance is a JToolBar with SaveAction sa,</br>
     * NewFileAction nfa, DeleteAction da, LoadAction la, folders f. */
    public ToolBar(SaveAction sa, NewFileAction nfa, DeleteFileAction dfa, PrintAction pa,
        LoadFolderAction la, String[] f) {
        // TODO: write this class
        setFloatable(false);

        save= new JButton(sa);
        save.setText("");
        add(save);

        newFile= new JButton(nfa);
        newFile.setText("");
        add(newFile);

        delete= new JButton(dfa);
        delete.setText("");
        add(delete);

        print= new JButton(pa);
        print.setText("");
        add(print);

        folderSelect= new JComboBox<>(f);
        folderSelect.setAction(la);
        folderSelect.setSelectedIndex(0); // TODO: change to item?
        folderSelect.setFont(folderSelect.getFont().deriveFont(12.0f));
        add(folderSelect);
    }

    /** Returns the currently opened folder. */
    public File currentFolder() {
        // TODO: need this?
        File file= new File(System.getProperty("user.home") + "\\memopad\\" +
            folderSelect.getSelectedItem());
        return file;
    }

    /** TODO: write spec */
    public void addFolder(String s) {
        // TODO: add items in alphabetical order?
        folderSelect.addItem(s);
    }

    /** TODO: write spec */
    public void removeFolder(String s) {
        folderSelect.removeItem(s);
    }

}
