package operateur;

import solution.Tournee;

public abstract class OperateurInterTournees extends OperateurLocal{

    protected Tournee autreTournee;
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
        this.deltaCoutTournee = this.evalDeltaCoutTournee();
        this.deltaCoutAutreTournee = this.evalDeltaCoutAutreTournee();
    }

    public abstract int evalDeltaCoutTournee();
    public abstract int evalDeltaCoutAutreTournee();

    @Override
    protected int evalDeltaCout() {
        int coutTournee = evalDeltaCoutTournee();
        int coutAutreTournee = evalDeltaCoutAutreTournee();

        if(coutTournee ==Integer.MAX_VALUE || coutAutreTournee == Integer.MAX_VALUE)
            return Integer.MAX_VALUE;

        return coutTournee + coutAutreTournee;
    }

    public Tournee getAutreTournee() {
        return autreTournee;
    }

    public int getDeltaCoutTournee() {
        return deltaCoutTournee;
    }

    public int getDeltaCoutAutreTournee() {
        return deltaCoutAutreTournee;
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
