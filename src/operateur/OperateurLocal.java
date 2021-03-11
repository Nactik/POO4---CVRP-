package operateur;

import instance.reseau.Client;
import solution.Tournee;

public abstract class OperateurLocal extends Operateur {

    protected int positionI;
    protected int positionJ;
    private Client clientI;
    private Client clientJ;

    public OperateurLocal() {
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
                return null;
            case INTER_ECHANGE:
                return null;
            case INTRA_DEPLACEMENT:
                return new IntraDeplacement();
            case INTRA_ECHANGE:
                return new IntraEchange();
            default:
                return null;
        }
    }

    public static OperateurInterTournees getOperateurInter(TypeOperateurLocal type,Tournee tournee,
                                                           int positionI, int positionJ){
        switch (type){
            case INTER_DEPLACEMENT:
                return null;
            case INTER_ECHANGE:
                return null;
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
