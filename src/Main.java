import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.EOFException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    /**
     *Main method that will read in a filename, find its metadata, and print with a degree of certainty, whether or not it has been edited.
     * @param args
     * @throws ImageProcessingException
     * @throws IOException
     */
    public static void main(String[] args) throws ImageProcessingException, IOException {
        if (args.length == 0) { //If a file wasn't passed at Runtime, it will continue via prompts
            Scanner input = new Scanner(System.in); //creates scanner for User Input
            System.out.println("What image do you want to analyze");  //Prompt for the user's input
            String fileName = input.next(); //Reads in the input - should be the file name
            File file = new File(fileName);  //creates File object
            boolean exists = file.exists(); //checks if it exists
            if (exists) displayData(file); //displays metadata
            else System.out.println("File " + fileName + " was not found"); //Error message if the file was not found.
        }
        else {
            String fileName = args[0];
            System.out.println("Processing File " + fileName);
            File file = new File(fileName);  //creates File object
            boolean exists = file.exists(); //checks if it exists
            System.out.println("Analyzing: " + fileName); //Simple output message
            if (exists) displayData(file); //displays metadata
            else System.out.println("File "+ fileName +" was not found"); //Error message if the file was not found.
        }
    }
    public static void displayData(File file) throws ImageProcessingException, IOException {
        Metadata fileMetadata = ImageMetadataReader.readMetadata(file); //creates Metadata object out of the file passed to it
        PhotoData checker = new PhotoData(fileMetadata);
        checker.printAll();
    }
}