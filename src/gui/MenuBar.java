package gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import actions.DeleteFileAction;
import actions.DeleteFolderAction;
import actions.NewFileAction;
import actions.NewFolderAction;
import actions.SaveAction;

/** An instance is a a JMenuBar. */
public class MenuBar extends JMenuBar {
    // TODO: write this class

    private JMenu file;

    private JMenu edit;

    /** */
    public MenuBar(SaveAction sa, NewFileAction nfa, NewFolderAction na, DeleteFileAction dfa,
        DeleteFolderAction da) {
        file= new JMenu("File");
        add(file);
        edit= new JMenu("Edit");
        add(edit);

        JMenuItem menuItem= new JMenuItem(sa);
        menuItem.setIcon(null);
        file.add(menuItem);

        menuItem= new JMenuItem(nfa);
        menuItem.setIcon(null);
        file.add(menuItem);

        menuItem= new JMenuItem(dfa);
        menuItem.setIcon(null);
        file.add(menuItem);

        file.addSeparator();

        menuItem= new JMenuItem(na);
        menuItem.setIcon(null);
        file.add(menuItem);

        menuItem= new JMenuItem(da);
        menuItem.setIcon(null);
        file.add(menuItem);
    }
}
