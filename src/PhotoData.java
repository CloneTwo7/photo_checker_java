import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.util.LinkedList;

/**
 * This PhotoData class is designed to take in metadata from a JPG and store
 * it all in an accessible way so that it's easy to get information and manipulate
 * the information in ways to verify whether the data had been manipulated or not
 */
public class PhotoData {
    EditInfo info; //Class created to store information about whether the data has been edited
    private LinkedList<Pair> directories; //LinkedList that will store the directories and a LinkedList of their paired Tag values

    /**
     * Constructor for the PhotoData class. It will read in a Metadata Class and sift through the directories and Tags.
     * It will store them in a LinkedList Pair where each pair contains the directory and a LinkedList of the associated Tags.
     * Finally it will construct an EditInfo class that stores the details as to how much the file has been edited and a simple
     * boolean variable that declares whether the file has been edited or not.
     * @param fileMetadata
     */
    public PhotoData(Metadata fileMetadata) { //Constructor will take in Metadata from a file
        directories = new LinkedList<Pair>();
        for(Directory directory : fileMetadata.getDirectories()) { //increment through each directory
            Pair p = new Pair(directory); //and create a pair of the directory and its corresponding tags
            directories.add(p); //add the pairs to the following LinkedList
        }
        info = new EditInfo(directories);
    }

    /**
     * This method will cycle through each directory and search it for the corresponding Tag value
     * @param t
     * @return
     */
    public boolean containsTag(Tag t) { //Takes in Tag t
        for(int i = 0;  i < directories.size(); i++) { //Cycles through each directory
            if(directories.get(i).containsTag(t))return true; //if a match was found, return true.
        }
        return false; //else, return false
    }

    /**
     * The following method will take in a String and search through all the directories to
     * see if the PhotoData contains a matching Directory
     * @param directoryName
     * @return
     */
    public boolean containsDirectory(String directoryName) {
        for(Pair p : directories) { //increments through each Pair in LinkedList
            String keyword = p.getDirectory().getName(); //Checks the toString of each Directory
            if(keyword.compareTo(directoryName) == 0) return true; //returns True if there's a match
        }
        return false; //False if there is no match
    }

    /**
     * printAll is a method used to print all the directories and all the Tags associated with the given file
     */
    public void printAll() {
        for(int i = 0;  i < directories.size(); i++) {
            System.out.println("Directory "+(i+1)+directories.get(i).getDirectory());
            System.out.println("Tags: \t");
            directories.get(i).printTags();
        }
        System.out.println(info);
    }

    /**
     * Simple getter method to see if the flag variable has been updated
     * @return
     */
    public boolean isEdited() {
        return info.isEdited();
    }
    /*This Pair class is constructed to
      organize and pair the directory to a
      LinkedList of the tags within the directory*/
    private class Pair {
        private Directory directory;  //The directory that is stored within the pair
        private LinkedList<Tag> tags; //The list of Tags stored inside that directory
        int size;

        /**
         * Constructor of the Pair will take in a directory.
         * It stores the directory itself and then sorts all the corresponding Tags into a LinkedList for easy access
         * @param d
         */
        public Pair(Directory d) {
            directory = d; //initializes the directory to the directory passed to it
            tags = new LinkedList<Tag>();
            for(Tag t : directory.getTags()) { //grabs each Tag
                tags.add(t); //adds them to the LinkedList
            }
            size = tags.size();
        }
        public Directory getDirectory() { //Simply returns the directory that is a part of the pair
            return directory;
        }

        /**
         * This will print the tags stored in the linkedList
         */
        public void printTags() { //This will print to the terminal the tags contained in the pair
            for(int i = 0; i < size; i++) { //Increments through each LinkedList element
                System.out.println("\tTag "+(i+1)+tags.get(i)); //Simply prints
            }
        }

        /**
         * This first containsTag method uses the toString method of the Tags
         * @param s
         * @return
         */
        public boolean containsTag(String s) { //Compares the names of the Tags to the String passed to it
            for(int i = 0;  i < size; i++) { //increments through each LinkedList element
                if(tags.get(i).toString().compareTo(s)==0) return true; //return true
            }
            return false; //else return false
        }

        /**
         * This second toString method uses the actual Tag's value, not just the corresponding name
         * @param t
         * @return
         */
        public boolean containsTag(Tag t) { //Compares the Tag sent to it to all the Tags stored in the LinkedList
            for(int i = 0;  i < size; i++) { //Increments through each element of the Linked List
                if(tags.get(i) == t)return true; //if equal, return true
            }
            return false; //else, return false
        }

        /**
         * This will return the Tag at the corresponding index
         * @param i
         * @return
         */
        public Tag getTag(int i) { //returns the Tag at the desired Element
            return(tags.get(i));
        }

        /**
         * Simple getter to streamline getting the size of the corresponding LinkedList
         * @return
         */
        public int size() { //returns the size of the Tags LinkedList
            return size;
        }
    }

    /**
     * The following class is designed to make using the PhotoData class an easier experience.
     * It will handle all the work of processing the actual edit information.
     * It will hold a String array of reasons that the image has been flagged as Edited.
     */
    public static class EditInfo {
        private boolean isEdited; //flag variable as to whether the image has been edited
        private double certainty; //This certainty variable will store a percentage
        LinkedList<String> reasons; //LinkedList of reasons the file was flagged as edited.
        public EditInfo(LinkedList<Pair> directories) {
            int i = 0; //counter variable to track how many reasons exist
            reasons = new LinkedList<String>();
            for(Pair p : directories) { //Increments through each directory, checking for different bits of data
                if(p.getDirectory().getName().compareTo("Photoshop") == 0 ) { //First reason to be flagged is if a Photoshop Directory exists
                    isEdited = true; //changes edit flag
                    reasons.add("Edited in Photoshop"); //Adds reason
                    certainty = 100.00;
                }
            }
            if(reasons.size()<=0) reasons.add("has not been edited"); //If reasons has not changed after reading the data, it will
        }

        /**
         * Simply returns the bool to see whether the file has been edited or not
         * @return
         */
        public boolean isEdited() {
            return isEdited;
        }

        /**
         * Will return the reason at the index specified
         * @param i
         * @return
         */
        public String reasons(int i) {
            return(reasons.get(i));
        }

        /**
         * The following toString() method will override the basic toString() so that it displays
         * the Edit information in a simple, readable way.
         * It will first state, whether the file was found to be
         * @return
         */
        public String toString() {
            String f = "File ";
            if(isEdited) {
                f = f + "has been edited because of the following reasons: ";
                for (String s : reasons) {
                    f = f + ("\n\t - " + s);
                }
                f = f + ("This was determined with certainty: %"+certainty);
            }
            else f = f+"has not been edited";
            return f;
        }
    }
}