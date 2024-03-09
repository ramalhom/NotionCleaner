package services;

import java.io.File;
import java.io.IOException;

public interface IServiceCleaner {
    public boolean unzipFile(String zipFilePath, String destDir) throws IOException;
    public boolean renameFilesAndDirectoriesRecursively(File dir);
    public boolean replaceFileContentRecursively(File dir) throws IOException;
}
