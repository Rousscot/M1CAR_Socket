package gui;

public class IHM extends javax.swing.JFrame
{ 
  /**
   **  Les �l�ments graphiques.
   **/
  protected javax.swing.JTextArea lignes;

  /**
   **  Le constructeur avec un titre.
   **/
  public IHM(String titre)
  {
    // Cr�er une fen�tre Swing.
    super(titre);

    // Fixer la taille de la fen�tre.
    super.setSize(400, 300);

    // Cr�er la liste des lignes affich�es.
    lignes = new javax.swing.JTextArea(40, 20);
    lignes.setEditable(false);

    // Obtenir le panel contenu par la fen�tre.
    java.awt.Container contentPane = super.getContentPane();

    // Fixer la mise en forme du panel.
    contentPane.setLayout(new java.awt.BorderLayout());

    // Ajouter un panel scrollable pour afficher les lignes.
    contentPane.add(
      java.awt.BorderLayout.CENTER,
      new javax.swing.JScrollPane(lignes)
    );
  }

  /**
   **   Ajouter une ligne � la liste des lignes affich�es.
   **/
  public void ajouterLigne(String ligne)
  {
    lignes.append(ligne + "\n");
  }

  /**
   **   Remettre � z�ro de la liste des lignes affich�es.
   **/
  public void raz()
  {
    lignes.setText("");
  }

  /**
   **  Mettre un listener de sortie de programme.
   **/

  public static void mettreListenerSortieProgramme(IHM ihm)
  {
    // Mettre un listener sur les �v�nements de la fen�tre.
    ihm.addWindowListener(
      new java.awt.event.WindowAdapter() {
        public void windowClosing(java.awt.event.WindowEvent e)
        {
          // Arr�ter le programme.
          System.exit(0);
        }
      }
    );
  }

  /**
   **  Un exemple d'utilisation.
   **/

  public static void main(String[] args)
  {
    // Cr�ation de l'gui.IHM du panneau d'affichage.
    IHM ihm = new IHM("Une borne d'affichage");

    // Mettre un listener sur les �v�nements de la fen�tre.
    IHM.mettreListenerSortieProgramme(ihm);

    // Afficher la fen�tre.
    ihm.setVisible(true);

    // Ajouter des lignes de texte dans le panneau d'affichage.
    for (int i=0; i<30; i++)
    {
      ihm.ajouterLigne("Le message " + i);
    }

    // Effacer le contenu du panneau d'affichage.
    ihm.raz();

    // Ajouter d'autres lignes de texte dans le panneau d'affichage.
    for (int i=30; i<50; i++)
    {
      ihm.ajouterLigne("Le message " + i);
    }
  }
}

