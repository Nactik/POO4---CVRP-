package operateur;

import solution.Tournee;

public abstract class Operateur {

    protected Tournee tournee;
    protected int cout;

    public Operateur() {
        tournee = null;
        int cout = Integer.MAX_VALUE;
    }

    public Operateur(Tournee tournee) {
        this.tournee = tournee;
    }

    public int getDeltaCout(){
        return cout;
    }

    public boolean isMouvementRealisable(){
        return cout != Integer.MAX_VALUE;
    }

    public boolean isMeilleur(Operateur op){
        return this.cout < op.cout;
    }

    public boolean doMouvementIfRealisable(){

    }

    protected abstract int evalDeltaCout();

    protected abstract boolean doMouvement();

}
