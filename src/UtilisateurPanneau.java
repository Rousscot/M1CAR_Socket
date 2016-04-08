import gui.IHM;

/**
 * Created by Cyril on 08/04/2016.
 */
public class UtilisateurPanneau {

    public static void main(String[] args) {

        //  Creation  de l’gui.IHM du  panneau d’affichage.
        IHM ihm = new IHM("Ma  borne d’affichage");

        //  Mettre  un  listener  sur  les  evenements  de la  fenetre.
        IHM.mettreListenerSortieProgramme(ihm);

        //  Afficher  la  fenetre.
        ihm.setVisible(true);

        //  Ajouter  des  lignes  de texte  dans le  panneau d’affichage.
        for (int i = 0; i < 30; i++)
            ihm.ajouterLigne("Le  message " + i);

        //  Effacer  le  contenu  du  panneau d’affichage.
        ihm.raz();

        //  Ajouter  de  nouvelles  lignes  de  texte.
        for (int i = 30; i < 50; i++)
            ihm.ajouterLigne("Le  message " + i);
    }
}
