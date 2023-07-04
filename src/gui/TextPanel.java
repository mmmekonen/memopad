package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import patient.Patient;

/** An instance is a JPanel with the text area. */
public class TextPanel extends JPanel {

    /** The text area for the panel */
    private JTextArea textArea;

    /** The scroll bar for the text area */
    private JScrollPane scrollPane;

    /** The patient whose file is currently open */
    private Patient currentPatient;

    /** Constructor: an instance is a JPanel with width width and height height. */
    public TextPanel(int width, int height) {
        // Create text area
        textArea= new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(textArea.getFont().deriveFont(13.0f));

        // Create scroll bar
        scrollPane= new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(width, height));
        add(scrollPane);

        ((FlowLayout) getLayout()).setHgap(0);
        ((FlowLayout) getLayout()).setVgap(0);
        setBounds(width, 0, width, height);
    }

    /** Returns the patient whose file is currently open. */
    public Patient currentPatient() {
        return currentPatient;
    }

    /** Updates the size of the panel and its components according to the given</br>
     * width and height. */
    public void updateSize(int width, int height) {
        scrollPane.setPreferredSize(new Dimension(width, height));
        setBounds(width, 0, width, height);
    }

    /** Loads the selected patient file into the text area. */
    public void loadFile(Patient p) {
        try {
            BufferedReader br= new BufferedReader(new FileReader(p.file()));
            textArea.read(br, p.name());
            br.close();
            currentPatient= p;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Writes the current text in the text area to the currently opened file. */
    public void saveFile() {
        try {
            BufferedWriter bw= new BufferedWriter(new FileWriter(currentPatient.file()));
            textArea.write(bw);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Clears all current text from the text area. */
    public void clear() {
        textArea.setText(null);
        currentPatient= null;
    }
}
