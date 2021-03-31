package operateur;

import solution.Tournee;

public class InterEchange extends OperateurInterTournees{

    public InterEchange() {
        super();
    }

    public InterEchange(Tournee tournee, Tournee autreTournee, int positionI, int positionJ) {
        super(tournee, autreTournee, positionI, positionJ);
    }

    @Override
    public int evalDeltaCoutTournee() {
        return this.tournee.deltaCoutRemplacementInter(positionI, clientJ,clientJ);
    }

    @Override
    public int evalDeltaCoutAutreTournee() {
        return this.autreTournee.deltaCoutRemplacementInter(positionJ,clientI,clientJ);
    }

    @Override
    protected boolean doMouvement() {
        return this.tournee.doEchange(this);
    }
}
