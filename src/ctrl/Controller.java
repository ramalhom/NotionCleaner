package ctrl;

import java.io.IOException;

import services.IServiceCleaner;
import services.ServiceCleaner;
import views.View;
import views.IViewForController;

/**
 * Classe Controller de l'application Notion Cleaner
 * 
 * @author Ramalho Mario
 * @version 1.0
 * @since 1.0
 */

public class Controller implements IControllerForView {

    private final IViewForController view;
    private final IServiceCleaner serviceCleaner;

    public Controller() {
        view = new View(this);
        serviceCleaner = new ServiceCleaner();

    }

    public void start() {
        view.start();

    }

    public void processNotionZip(String notionZipFile) {
        // Dézipper le fichier
        try {
            serviceCleaner.unzipFile(notionZipFile, notionZipFile.substring(0, notionZipFile.lastIndexOf("\\")));
        } catch (IOException e) {
            view.messageErreur(
                    "Une erreur est survenue lors de l'opération d'extraction de l'archive. Veuillez réessayer.");
        }

        // Renommer les fichiers et répertoires
        serviceCleaner.renameFilesAndDirectoriesRecursively(
                new java.io.File(notionZipFile.substring(0, notionZipFile.lastIndexOf("\\"))));

        // Remplacer le contenu des fichiers
        try {
            serviceCleaner.replaceFileContentRecursively(
                    new java.io.File(notionZipFile.substring(0, notionZipFile.lastIndexOf("\\"))));
        } catch (IOException e) {
            view.messageErreur(
                    "Une erreur est survenue lors de l'opération de remplacement de contenu. Veuillez réessayer.");
        }
    }

}
