package operateur;

import solution.Tournee;

public abstract class OperateurInterTournees extends OperateurLocal{

    private Tournee autreTournee;
    private int deltaCoutTournee;
    private int deltaCoutAutreTournee;

    public OperateurInterTournees() {
        super();
        this.autreTournee = null;
    }

    public OperateurInterTournees(Tournee tournee, Tournee autreTournee, int positionI, int positionJ) {
        super(tournee, positionI, positionJ);
        this.autreTournee = autreTournee;
        this.setClientJ(autreTournee.getClientPosition(positionJ));
        this.deltaCout = this.evalDeltaCout();
    }

    public abstract int evalDeltaCoutTournee();
    public abstract int evalDeltaCoutAutreTournee();

    @Override
    protected int evalDeltaCout() {
        return evalDeltaCoutAutreTournee() + evalDeltaCoutAutreTournee();
    }


    @Override
    public String toString() {
        return "OperateurInterTournees{" +
                "deltaCout=" + deltaCout +
                ", autreTournee=" + autreTournee +
                ", deltaCoutTournee=" + deltaCoutTournee +
                ", deltaCoutAutreTournee=" + deltaCoutAutreTournee +
                '}';
    }


}
