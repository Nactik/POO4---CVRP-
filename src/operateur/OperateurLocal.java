package operateur;

import instance.reseau.Client;
import solution.Tournee;

public abstract class OperateurLocal extends Operateur {

    protected int positionI;
    protected int positionJ;
    protected Client clientI;
    protected Client clientJ;

    public OperateurLocal() {
        super();
        this.clientI = null;
        this.clientJ = null;
    }

    public OperateurLocal(Tournee tournee,int positionI,int positionJ) {
        super(tournee);
        this.positionI = positionI;
        this.positionJ = positionJ;
        this.clientI = tournee.getClientPosition(positionI);
        this.clientJ = tournee.getClientPosition(positionJ);
    }

    public int getPositionI() {
        return positionI;
    }

    public int getPositionJ() {
        return positionJ;
    }

    protected void setClientJ(Client clientJ) {
        this.clientJ = clientJ;
    }

    public static OperateurLocal getOperateur(TypeOperateurLocal type){
        switch (type){
            case INTER_DEPLACEMENT:
                return new InterDeplacement();
            case INTER_ECHANGE:
                return new InterEchange();
            case INTRA_DEPLACEMENT:
                return new IntraDeplacement();
            case INTRA_ECHANGE:
                return new IntraEchange();
            default:
                return null;
        }
    }

    public static OperateurInterTournees getOperateurInter(TypeOperateurLocal type,Tournee tournee,Tournee autreTournee,
                                                           int positionI, int positionJ){
        switch (type){
            case INTER_DEPLACEMENT:
                return new InterDeplacement(tournee,autreTournee,positionI,positionJ);
            case INTER_ECHANGE:
                return new InterEchange(tournee,autreTournee,positionI,positionJ);
            default:
                return null;
        }
    }

    public static OperateurIntraTournee getOperateurIntra(TypeOperateurLocal type,Tournee tournee,
                                                           int positionI, int positionJ){
        switch (type){
            case INTRA_DEPLACEMENT:
                return new IntraDeplacement(tournee,positionI,positionJ);
            case INTRA_ECHANGE:
                return new IntraEchange(tournee,positionI,positionJ);
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return "OperateurLocal{" +
                "positionI=" + positionI +
                ", positionJ=" + positionJ +
                ", clientI=" + clientI +
                ", clientJ=" + clientJ +
                '}';
    }
}
