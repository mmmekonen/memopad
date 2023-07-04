package gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import actions.ClickAction;
import actions.DeleteFileAction;
import actions.DeleteFolderAction;
import actions.LoadFolderAction;
import actions.NewFileAction;
import actions.NewFolderAction;
import actions.PrintAction;
import actions.SaveAction;

/** An instance is a GUI for the memopad. Run this <br>
 * file as a Java application to open the memopad. */
public class GUI extends JFrame {

    /** The GUI for the app */
    public static GUI gui;

    /** Width and height of the window */
    public static int SCREEN_WIDTH= 600, SCREEN_HEIGHT= 700;

    /** Position of the window on the screen */
    public static int POSITION_X= 700;

    public static int POSITION_Y= 100;

    /** Height of the toolbar */
    public static int BAR_HEIGHT;

    /** Horizonatl spacing between components */
    private static int PADDING= 7;

    /** The menu bar for the window */
    private static MenuBar menu;

    /** The tool bar for the window */
    private static ToolBar tools;

    /** Panel that provides text area */
    private static TextPanel text;

    /** The panel for listing file names */
    private static ListPanel list;

    /** The panel that holds the list and text panels */
    private static JPanel content;

    /** The directory for the gui's data */
    private static File folder;

    /** The main program. Runs the memopad gui. */
    public static void main(String args[]) {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        gui= new GUI(); // Create and show the GUI
    }

    /** Constructor: a new display for the memopad. */
    public GUI() {
        super("Memopad");

        // Initialize frame
        setLocation(POSITION_X, POSITION_Y);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(
            new ImageIcon(GUI.class.getResource("/toolbarButtonGraphics/general/Paste16.gif"))
                .getImage());

        // Creates the folder with all the data
        createFolder();

        // Create text area
        text= new TextPanel(SCREEN_WIDTH / 2, SCREEN_HEIGHT);

        // Create list
        list= new ListPanel(SCREEN_WIDTH / 2, SCREEN_HEIGHT, folder, text);

        // Create actions
        SaveAction sa= new SaveAction(text, list);
        NewFileAction nfa= new NewFileAction(text, list);
        DeleteFileAction dfa= new DeleteFileAction(text, list);
        LoadFolderAction la= new LoadFolderAction(text, list);
        ClickAction ca= new ClickAction(text, list, folder);
        PrintAction pa= new PrintAction(list, folder);

        list.addListener(ca);

        // Create tool bar
        tools= new ToolBar(sa, nfa, dfa, pa, la, folderNames());
        add(tools, BorderLayout.PAGE_START);
        BAR_HEIGHT= tools.getSize().height;

        // Create content panel
//        content= new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
//        content.add(list);
//        content.add(text);
//        add(content);
        add(list, BorderLayout.WEST);
        add(text, BorderLayout.EAST);

        // Create the menu bar
        NewFolderAction na= new NewFolderAction(tools, folder);
        DeleteFolderAction da= new DeleteFolderAction(tools, folder);
        menu= new MenuBar(sa, nfa, na, dfa, da);
        setJMenuBar(menu);

        setVisible(true);

        // Repaint the GUI to fit the new size
        addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                int state= e.getNewState();
                if ((state & Frame.MAXIMIZED_VERT) != 0 || (state & Frame.MAXIMIZED_HORIZ) != 0) {
                    resizeWindow();
                }
            }
        });

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeWindow();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                Point top_corner= getLocation();
                POSITION_X= top_corner.x;
                POSITION_Y= top_corner.y;
            }

            @Override
            public void componentShown(ComponentEvent e) {}

            @Override
            public void componentHidden(ComponentEvent e) {}
        });

        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    private void resizeWindow() {
        SCREEN_WIDTH= getWidth();
        SCREEN_HEIGHT= getHeight();
        text.updateSize(SCREEN_WIDTH / 2 - PADDING, SCREEN_HEIGHT - BAR_HEIGHT);
        list.updateSize(SCREEN_WIDTH / 2 - PADDING, SCREEN_HEIGHT - BAR_HEIGHT);
        text.revalidate();
        list.revalidate();
        text.repaint();
        list.repaint();
    }

    /** Creates the data directory is one doesn't already exist. */
    private void createFolder() {
        folder= new File(System.getProperty("user.home") + "\\memopad");
        if (!folder.exists() && !folder.mkdirs()) {
            System.out.println("Folder creation failed.");
            setVisible(false); // hide window
            dispose();         // close window
            System.exit(0);    // stop program
        }
    }

    /** TODO: write spec */
    public static String[] folderNames() {
        File[] f= folder.listFiles();
        LinkedList<String> s= new LinkedList<>();
        for (File file : f) {
            if (file.isDirectory()) {
                s.add(file.getName());
            }
        }
        return s.toArray(new String[0]);
    }
}
