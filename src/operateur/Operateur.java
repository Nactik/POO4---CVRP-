package operateur;

import solution.Tournee;

public abstract class Operateur {

    protected Tournee tournee;
    protected int deltaCout;

    public Operateur() {
        tournee = null;
        this.deltaCout = Integer.MAX_VALUE;
    }

    public Operateur(Tournee tournee) {
        this();
        this.tournee = tournee;
    }

    public int getDeltaCout(){
        return deltaCout;
    }

    public boolean isMouvementRealisable(){
        return deltaCout != Integer.MAX_VALUE;
    }

    public boolean isMeilleur(Operateur op){
        return this.deltaCout < op.deltaCout;
    }

    public boolean doMouvementIfRealisable(){
        if(isMouvementRealisable()){
            return doMouvement();
        }
        return false;
    }

    public boolean isMouvementAmeliorant(){
        return deltaCout < 0;
    }

    protected abstract int evalDeltaCout();

    protected abstract boolean doMouvement();

}
