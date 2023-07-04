package patient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** An instance is a patient which includes the patient's name and file. */
public class Patient implements Comparable<Patient> {

    /** The name of the patient. Must be the first line of the file. */
    private String name;

    /** The file for the patient. */
    private File file;

    /** Constructor: an instance is a patient with file f and a name that is</br>
     * the first line of the file. */
    public Patient(File f) {
        file= f;
        setName();
    }

    /** Returns the file of this patient. */
    public File file() {
        return file;
    }

    /** Make f the file of this patient.</br>
     * Precondition: the name of the file must be the same as the patient name. */
    public void setFile(File f) {
        // TODO: check whether this method is necessary
        file= f;
        setName();
    }

    /** Returns the name of this patient. */
    public String name() {
        return name;
    }

    /** Make the patient's name the of the first line of the file. */
    public void setName() {
        try {
            BufferedReader br= new BufferedReader(new FileReader(file));
            String n= br.readLine();
            br.close();
            if (n == null) {
                name= "UNTITLED";
            } else {
                name= n;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Deletes the file associated with this patient. */
    public void delete() {
        file.delete();
    }

    @Override
    /** Returns a string representation of the patient. */
    public String toString() {
        return name;
    }

    @Override
    /** Returns true iff this and obj are of the same class and their name and</br>
     * file fields are the same. */
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) { return false; }
        Patient p= (Patient) obj;
        return name == p.name && file == p.file;
    }

    @Override
    /** Compares this patient with the specified patient p for order. Returns a</br>
     * negative integer, zero, or a positive integer as this patient comes after,</br>
     * the same as, or before the specified patient p alphabetically. */
    public int compareTo(Patient p) {
        return name.compareTo(p.name);
    }

    /** Generates a list of patients given a file directory. */
    public static List<Patient> generatePatients(File folder) {
        File[] files= folder.listFiles();
        List<Patient> patients= new ArrayList<>();
        for (File f : files) {
            patients.add(new Patient(f));
        }
        return patients;
    }
}
