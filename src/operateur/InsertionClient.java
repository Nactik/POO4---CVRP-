package operateur;

import instance.reseau.Client;
import solution.Tournee;

public class InsertionClient extends Operateur{

    private Client clientToAdd;
    private int position;

    public InsertionClient() {
        super();
        clientToAdd = null;
    }

    public InsertionClient(Tournee tournee){
        super(tournee);
    }

    public InsertionClient(Tournee tournee, Client clientToAdd, int position) {
        super(tournee);
        this.clientToAdd = clientToAdd;
        this.position = position;
        this.deltaCout = this.evalDeltaCout();
    }

    @Override
    protected int evalDeltaCout() {
        return this.tournee.deltaCoutInsertion(this.position,this.clientToAdd);
    }

    @Override
    protected boolean doMouvement() {
        return this.tournee.doInsertion(this);
    }

    public int getPosition() {
        return this.position;
    }

    public Client getClientToAdd() {
        return this.clientToAdd;
    }

    @Override
    public String toString() {
        return "InsertionClient{" +
                "clientToAdd=" + clientToAdd +
                ", position=" + position +
                ", tournee=" + tournee +
                ", cout=" + deltaCout +
                '}';
    }

}
