package validator;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

public interface Validator {

    static File validate(File folder) {
        if (!folder.isDirectory()) {
            System.out.println("There is no folder in the specified path.");
            return null;
        }

        File[] files = folder.listFiles();

        if (files.length == 0) {
            System.out.println("This folder is empty.");
            return null;
        }

        files = Arrays.stream(files)
                .filter(x -> x.toString().endsWith(".xml"))
                .toArray(File[]::new);

        if (files.length == 0) {
            System.out.println("Folder does not contains \".xml\" files.");
            return null;
        } else if (files.length > 1) {
            System.out.println("There is more than one \".xml\" file in the folder.");
            return null;
        }
        return files[0];
    }

}
