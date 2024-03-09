# Notion Cleaner
## C'est quoi ?
C'est une application JavaFX qui permet de nettoyer les archives ZIP de Notion.
Lorsque vous utilisez l'exportation HTML ou Markdown de Notion, il se trouve que Notion ajoute un numéro de 32 caractères à chaque document html ou répertoire. Si vous devez le stocker dans une arborescence Windows, ce sera souvent problématique car le chemin d'accès ne doit pas être trop long.

## Comment l'utiliser ?
Déposer tout simplement votre document zip en réalisant un glisser / déposer. 
Voici les étapes réalisées par l'application :
- L'archive Zip est décompressée dans le même répertoire où se trouve l'archive
- Les fichiers et répertoires extraits sont renommés sans le numéro à 32 caractères
- Le contenu des fichiers est également modifiés pour que les liens soient fonctionnels

## Téléchargement
Vous pouvez récupérer le Jar Notion Cleaner se trouvant dans le répertoire [dist](https://github.com/ramalhom/NotionCleaner/tree/main/dist). Il a été déployer avec la version du JDK 17 sous Windows.