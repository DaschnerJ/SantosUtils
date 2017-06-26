/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santosutils.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author justdasc
 */
public class FileManager {

    /**
     * Creates a new file manager which is used for creating basic files
     * in most of the other Santos jars.
     */
    public void FileManager() {
        /**
         * This is empty because we only want this for instantaneous reference.
         */
    }
    
    /**
     * Used to create a directory at the specified location.
     * @param location The directory to be created.
     * @return Returns true if the directory can be created. False if it cannot.
     */
    public boolean createDirectory(String location) {
        /**
         * Creates a temporary folder at the location.
         * Treating the folder like a file allows us to check
         * if the folder actually exists with predefined
         * file methods.
         */
        File theDir = new File(location);
        
        //Here we check with the predefined file methods if the directory exists.
        if (!theDir.exists()) {
            try {
                //We try and make the folder directory.
                theDir.mkdir();
            } catch (SecurityException e) {
                /**
                 * Due to security of certain folders that do not
                 * allow file creation with in them, the file sometimes
                 * cannot be created if lacking privileges. So we
                 * return false to indicate the failed attempt.
                 */
                return false;
            }
        }
        //Everything executed properly.
        return true;
    }

    /**
     * Used to create a file with the specified path.
     * @param path The full path of the file to be created.
     * @return Returns true if the file was created or exists.
     */
    public boolean createFile(String path) {
        try {
            /**
             * Assuming the directory already exists, we create a new temporary
             * file at the location.
             */
            File f = new File(path);
            /**
             * Here the file is created and checks if the already exists.
             * If the file already exists then this line does nothing.
             */
            f.createNewFile();
            /**
             * To ensure we actually was able to make a file we created
             * and close an output stream to ensure the file exists.
             */
            FileOutputStream oFile = new FileOutputStream(f, false);
            oFile.close();
        } catch (IOException ex) {
            /**
             * We catch normal IO exceptions and return false if the file was
             * unable to be created.
             */
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //Everything executed properly.
        return true;
    }

    /**
     * Gets the current running jar directory.
     * @return Returns the current running jar directory.
     */
    public String GetExecutionPath() {
        /**
         * We get the jar's path by getting using the already known path
         * of the class inside the jar. We do not use URL or URI because
         * they are incompatible with most versions and systems and often
         * lead to unexpected errors.
         */
        String absolutePath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        /**
         * Cut off extra / or \ in front.
         */
        absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
        absolutePath = absolutePath.replaceAll("^\\\\", "");
        absolutePath = absolutePath.replaceAll("^/", "");

        /**
         * Due to windows allowing the ' ' in directory naming, windows will
         * replace the ' ' with %20 as an indication. However, we do not want
         * this and we only want the actual ' ' so we must replace it.
         */
        absolutePath = absolutePath.replaceAll("%20", " ");
        //Return the path obtained.
        return absolutePath;
    }

}
