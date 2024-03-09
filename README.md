# MVC_FXML_226a_JDK17LTS
## C'est quoi ?
C'est un template Visual Studio Code pour des projets et/ou exercices Java dès le ~~226a~~ 320 :
- Basé sur JDK-17-LTS et FXML/JavaFX-17
- Utilise le pattern MVC décidé en GT-DEV 2023
- Utilise des interfaces entre les couches (car elles sont vues au module ~~226a~~ 320)

**-> Utile dès le module 226a quand on veut une ihm simple**
## Plusieurs versions de JavaFX sont inclues dans ce template !
### Pourquoi plusieurs versions ?
De nombreux enseignants et apprentis qui font du DEV utilisent des Mac et/ou des PC, souvent les deux. Comme JavaFX ne fait plus partie du JDK il doit être séparément installé/disponible.

Pour simplifier la vie et gagner du temps, ce template contient déjà 3 versions communes de JavaFX 17 : pour **PC/i64**, pour **Mac/i64** et pour **Mac/M1M2**.

### Comment changer de version ?
Par défaut **ce template utilise la version pour PC/i64**.

VSC est malin et charge automatiquement les librairies du dossier `/lib`. Donc coder sur un PC puis passer ensuite un moment sur un Mac est chose facile : il suffit de copier la bonne version de JavaFX du dossier `/libfx` dans `/lib` pour fonctionner de manière transparente. Et ça roule !

Par exemple, si l'on souhaite développer sur PC, c'est aussi simple que de copier le dossier `openjfx_17_0_8_windows_x64` du dossier `/libfx` dans `/lib` (et d'y enlever la version précédemment utilisée). C'est tout 8-).

### Versions de JavaFX présentes ?
| Version | Plateforme | Architecture | Dossier à copier dans `/lib` | Commentaires |
| :---- | :---- | :---- | :---- | :---- |
| 17.0.7 | PC | i64 | `openjfx_17_0_8_windows_x64` | Déjà présent dans `/lib` par défaut |
| 17.0.7 | Mac | i64 | `openjfx_17_0_7_osx_x64` | |
| 17.0.7 | Mac | aarch64 (M1 ou M2) | `openjfx_17_0_7_osx_aarch64` | |

### Comment libérer de l'espace de stockage inutile ?
Si dans votre projet vous n'avez pas utilité d'avoir plusieurs versions de JavaFX, ne conservez que la version souhaitée dans votre dossier `/lib` et supprimez simplement l'ensemble du dossier `/libfx`. Vous gagnerez environ 3 x ~80MB.

## Vue d'ensemble UML du projet
### Détail des classes du projet - avec Entreprise Architect

<img src="uml/MVC_FXML_226A_JDK17LTS.png" />

### Détail des classes du projet - avec `mermaid`
Voici une vue d'ensemble du contenu de ce projet sous forme de diagramme UML en utilisant la notation `mermaid` (qui génère des diagrammes à la volée, dynamiquement).

### Les packages utilisés
S'affiche correctement sous VSC mais pas encore correctement sur GitHub qui n'a pas encore mis à jour sa version de `mermaid`...

```mermaid
classDiagram
namespace app {
    class Application
}
namespace ctrl {
    class Controller
    class IControllerForView{ <<interface>> }
}

namespace views {
    class View
    class IViewForController{ <<interface>> }
}
namespace models {
    class PrevisionMeteo
    class Voiture
}
namespace services {
    class ServiceLocation
    class ServiceMeteo
    class IServiceLocation{ <<interface>> }
    class IServiceMeteo{ <<interface>> }
}
```
### Détail des classes du projet - les modèles
```mermaid
classDiagram

    class PrevisionMeteo {
        -String prevision
        PrevisionMeteo( String prevision )
        +getPrevision() String
    }

    class Voiture {
        -String marque
        -String modele
        Voiture( String marque, String modele )
        +getMarque() String
        +getModele() String
        +toString() String
    }

```
### Détail des classes du projet - les services
```mermaid
classDiagram

    class ServiceMeteo {
        ServiceMeteo()
        +prochainBulletinMeteo() PrevisionMeteo
    }

    class ServiceLocation {
        -int MAX_VOITURES_LOUEES = 4$
        -Voiture[] voituresDisponibles
        -Voiture[] voituresLoueesParLeClient
        ServiceLocation()
        +listeDesVoituresDisponibles() Voiture[]
        +listeDesVoituresLoueesParLeClient() Voiture[]
        +clientLoueUneVoiture(Voiture voiture) boolean
        +clientRestitueUneVoiture(Voiture voiture) boolean
    }

    class Controller {
        -IViewForController view
        -IServiceMeteo serviceMeteo
        -IServiceLocation serviceLocation
        Controller()
        +start() void
        actionRafraichirPrevisionMeteo() void
        actionLouerUneVoiture(Voiture voiture) void
        actionRestituerUneVoiture(Voiture voiture) void
    }

    class IServiceLocation {
        <<interface>>
        listeDesVoituresDisponibles() Voiture[]
        listeDesVoituresLoueesParLeClient() Voiture[]
        clientLoueUneVoiture(Voiture voiture) boolean
        clientRestitueUneVoiture(Voiture voiture) boolean
    }

    class IServiceMeteo {
        <<interface>>
        prochainBulletinMeteo() PrevisionMeteo
    }

    Controller o--> "1" IServiceMeteo : serviceMeteo
    Controller o--> "1" IServiceLocation : serviceLocation

    ServiceLocation o--> "0..N" Voiture : voituresDisponibles
    ServiceLocation o--> "0..N" Voiture : voituresLoueesParLeClient

    ServiceMeteo ..|> IServiceMeteo : "implemente"
    ServiceLocation ..|> IServiceLocation : "implemente"

```

### Détail des classes du projet - le pattern MVC
```mermaid
classDiagram

    class Application {
        +main(String[] args)$ void
    }

    class Controller {
        -IViewForController view
        -IServiceMeteo serviceMeteo
        -IServiceLocation serviceLocation
        Controller()
        +start() void
        actionRafraichirPrevisionMeteo() void
        actionLouerUneVoiture(Voiture voiture) void
        actionRestituerUneVoiture(Voiture voiture) void
    }

    class View {
        -IControllerForView controller
        -TextField textFieldPrevisionMeteo
        -ListView<Voiture> listViewVoituresDisponibles
        -ListView<Voiture> listViewVoituresLouees
        View(Controller controller)
        +start() void
        -actionRafraichirPrevisionMeteo() void
        -actionLouerUneVoiture(Voiture voiture) void
        -actionRestituerUneVoiture(Voiture voiture) void
        +initialize(URL url, ResourceBundle rb) void
        +messageInfo(String message) void
        +messageErreur(String message) void
        +afficheDernierePrevision(PrevisionMeteo prevision) void
        +afficheVoituresDisponibles(Voiture[] voituresDisponibles, Voiture voitureASelectionner) void
        +afficheVoituresLouees(Voiture[] voituresLouees, Voiture voitureASelectionner) void
        -tableauEnListeSansNulls(Voiture[] voitures) ArrayList<Voiture>
    }
    class IControllerForView {
        <<interface>>
        actionRafraichirPrevisionMeteo() void
        actionLouerUneVoiture( Voiture voiture) void
        actionRestituerUneVoiture( Voiture voiture) void
    }

    class IViewForController {
        <<interface>>
        start() void
        afficheDernierePrevision( PrevisionMeteo prevision ) void
        aafficheVoituresDisponibles( List<Voiture> voituresDisponibles, Voiture voitureASelectionner ) void
        afficheVoituresLouees( List<Voiture> voituresLouees, Voiture voitureASelectionner ) void
        messageInfo( String message ) void
        messageErreur( String message ) void
    }

    View ..|> Initializable : "implemente"
    
    Controller o--> "1" IViewForController : view
    View o--> "1" IControllerForView : controller
    View ..|> IViewForController : "implemente"
    Controller ..|> IControllerForView : "implemente"

    Application ..> Controller : "utilise"

```
