package operateur;

import instance.reseau.Client;
import solution.Tournee;

public class InterDeplacement extends OperateurInterTournees {

    public InterDeplacement() {
        super();
    }

    public InterDeplacement(Tournee tournee, Tournee autreTournee, int positionI, int positionJ) {
        super(tournee, autreTournee, positionI, positionJ);
    }

    @Override
    public int evalDeltaCoutTournee() {
        return this.tournee.deltaCoutSuppression(positionI);
    }

    @Override
    public int evalDeltaCoutAutreTournee() {
        Client clientToAdd = this.tournee.getClientPosition(positionI);
        return this.autreTournee.deltaCoutInsertionInter(positionJ,clientToAdd);
    }

    @Override
    protected boolean doMouvement() {
        return this.tournee.doDeplacement(this);
    }



    @Override
    public String toString() {
        return "InterDeplacement{" +
                "tournee=" + tournee +
                ", deltaCout=" + deltaCout +
                ", positionI=" + positionI +
                ", positionJ=" + positionJ +
                '}';
    }
}
