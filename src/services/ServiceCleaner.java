package services;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Classe ServiceCleaner de l'application Notion Cleaner
 * Cette classe implémente l'interface IServiceCleaner et permet de nettoyer les fichiers extraits d'une archive Notion exportée.
 * L'archive Notion exportée est décompressée, les fichiers et répertoires extraits sont renommés et le contenu des fichiers est remplacé.
 * Le type de format de l'archive Notion exportée est .zip et le type de format des fichiers extraits est .html.
 * 
 * @see IServiceCleaner
 * @author Ramalho Mario
 * @version 1.0
 * @since 1.0
 */

public class ServiceCleaner implements IServiceCleaner {

    public ServiceCleaner() {
    }

    /**
     * Dézipper un fichier
     * 
     * @param zipFilePath le chemin du fichier zip à dézipper
     * @param destDir     le répertoire de destination où extraire le contenu du fichier zip
     * @return true si le fichier zip a été dézippé avec succès, false sinon
     * @throws IOException si une erreur d'entrée/sortie survient
     */
    public boolean unzipFile(String zipFilePath, String destDir) throws IOException {
        boolean isUnzipped = false;
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                String filePath = destDir + File.separator + entry.getName();
                if (entry.isDirectory()) {
                    File dir = new File(filePath);
                    dir.mkdirs();
                } else {
                    File file = new File(filePath);
                    File parentDir = file.getParentFile();
                    if (!parentDir.exists()) {
                        parentDir.mkdirs();
                    }
                    try (FileOutputStream fileOut = new FileOutputStream(file);
                            BufferedOutputStream buffOut = new BufferedOutputStream(fileOut)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipIn.read(buffer)) > 0) {
                            buffOut.write(buffer, 0, length);
                        }
                        isUnzipped = true;
                    }
                }
            }
        }
        return isUnzipped;
    }

    /**
     * Renommer les fichiers et répertoires récursivement
     * 
     * @param dir le répertoire où renommer les fichiers et répertoires
     * @return true si les fichiers et répertoires ont été renommés avec succès, false sinon
     */
    public boolean renameFilesAndDirectoriesRecursively(File dir) {
        boolean hasRenamed = false;

        // Spécifiez l'expression régulière à utiliser pour renommer les fichiers et
        // répertoires
        String renameRegex = "\\s\\w{32}";

        // Spécifiez le nouveau nom à utiliser pour remplacer l'ancien nom
        String newName = "";

        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                renameFilesAndDirectoriesRecursively(file);
            }
            String fileName = file.getName();
            String newFileName = fileName.replaceAll(renameRegex, newName);
            if (!fileName.equals(newFileName)) {
                File newFile = new File(file.getParent(), newFileName);
                file.renameTo(newFile);
                hasRenamed = true;
            }
        }
        return hasRenamed;
    }

    /**
     * Remplacer le contenu des fichiers récursivement
     * 
     * @param dir le répertoire où remplacer le contenu des fichiers
     * @return true si le contenu des fichiers a été remplacé avec succès, false sinon
     * @throws IOException si une erreur d'entrée/sortie survient
     */
    public boolean replaceFileContentRecursively(File dir) throws IOException {
        boolean isReplaced = true;

        // Spécifiez l'expression régulière à utiliser pour remplacer le contenu des
        // fichiers
        String contentRegex = "%20([a-z]|[0-9]){32}";

        // Spécifiez le nouveau contenu à utiliser pour remplacer l'ancien contenu
        String newContent = "";

        List<File> fileList = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".html")) {
                fileList.add(file);
            } else if (file.isDirectory()) {
                isReplaced = replaceFileContentRecursively(file);
            }
        }
        for (File file : fileList) {
            List<String> newLines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(file, Charset.forName("UTF-8")))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String newLine = line.replaceAll(contentRegex, newContent);
                    newLines.add(newLine);
                }
            } catch (IOException e) {
                System.err.println("Erreur lors de la lecture du contenu du fichier "
                        + file.getAbsolutePath() + ": " + e.getMessage());
                isReplaced = false;
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, Charset.forName("UTF-8")))) {
                for (String newLine : newLines) {
                    writer.write(newLine);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Erreur lors de l'écriture du nouveau contenu dans le fichier "
                        + file.getAbsolutePath() + ": " + e.getMessage());
                isReplaced = false;
            }
            newLines.clear();
        }
        return isReplaced;
    }
}
