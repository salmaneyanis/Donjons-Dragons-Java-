package interfacejeu;

public class Case {
    private ContenuCase m_contenu;

    public Case() {
        this.m_contenu = null;
    }

    public ContenuCase getM_contenu() {
        return m_contenu;
    }

    public void setM_contenu(ContenuCase m_contenu) {
        this.m_contenu = m_contenu;
    }

    public boolean estVide() {
        return m_contenu == null;
    }

    public boolean passable() {
        if (m_contenu == null) {
            return true;
        }
        return false;
    }

    public boolean accessibleParJoueur() {
        return passable();
    }


    public String afficher() {
        return estVide() ? " . " : m_contenu.afficher();
    }
}
