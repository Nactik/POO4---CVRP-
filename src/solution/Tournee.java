package solution;

import instance.Instance;
import instance.reseau.Client;
import instance.reseau.Depot;
import instance.reseau.Point;
import io.InstanceReader;
import io.exception.ReaderException;
import operateur.InsertionClient;
import operateur.Operateur;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Tournee {

    private Depot depot;
    private List<Client> clients;

    private final int capacite;
    private int demandeTotale;
    private int coutTotal;

    public Tournee(Instance instance){
        this.depot = instance.getDepot();
        this.capacite = instance.getCapacite();
        this.clients = new LinkedList<>();
    }

    public Depot getDepot() {
        return depot;
    }

    public int getCapacite() {
        return capacite;
    }

    public int getDemandeTotale() {
        return demandeTotale;
    }

    public int getCoutTotal() {
        return coutTotal;
    }

    public List<Client> getClients() {
        return clients;
    }

    private boolean majCoutTotal(Client clientToAdd){
        int cout = this.deltaCoutInsertion(this.clients.size(),clientToAdd);

        this.coutTotal += cout;
        return true;
    }

    private boolean isAjoutRealisable(Client clientToAdd){
        if(clientToAdd == null) return false;
        return this.demandeTotale + clientToAdd.getDemande() <= this.capacite;
    }

    public boolean ajouterClient(Client clientToAdd){
        if(!isAjoutRealisable(clientToAdd)) return false;

        if(!this.majCoutTotal(clientToAdd)) return false;
        this.demandeTotale += clientToAdd.getDemande();
        this.clients.add(clientToAdd);

        return true;
    }

    private boolean checkDemande(){
        int demande = 0;
        for(Client client : this.clients){
            demande += client.getDemande();
        }
        return demande == this.demandeTotale;
    }

    private boolean checkCout(){
        int cout = 0;
        int i = 0;

        cout += this.depot.getCoutVers(this.clients.get(i));

        for(i=0;i<this.clients.size() - 1; i++) {
            cout+= this.clients.get(i).getCoutVers(this.clients.get(i+1));
        }

        cout += this.clients.get(i).getCoutVers(this.depot);

        return cout == this.coutTotal;
    }

    public boolean check(){
        if(this.demandeTotale > this.capacite){
            System.err.println("Erreur demande supérieur à capacité (Tournées)");
            return false;
        }
        if(!checkDemande()){
            System.err.println("Erreur calcul demande (Tournées)");
            return false;
        }
        if(!checkCout()){
            System.err.println("Erreur calcul cout (Tournées)");
            return false;
        }

        return true;
    }

    private Point getPrec(int position){
        if(position == 0) return this.depot;
        return this.clients.get(position-1);
    }

    private Point getCurrent(int position){
        if(position == this.clients.size()){
            return this.depot;
        }
        return this.clients.get(position);
    }

    private boolean isPositionInsertionValide(int position){
        return position >= 0 && position <= this.clients.size();
    }

    public int deltaCoutInsertion(int position, Client clientToAdd){
        if(clientToAdd == null || !isPositionInsertionValide(position))
            return Integer.MAX_VALUE;

        Point prec = this.getPrec(position);
        Point current = this.getCurrent(position);

        if(prec.equals(current))
            return prec.getCoutVers(clientToAdd) + clientToAdd.getCoutVers(prec);

        return prec.getCoutVers(clientToAdd) + clientToAdd.getCoutVers(current) - prec.getCoutVers(current);
    }


    public Operateur getMeilleurInsertion(Client clientToInsert){
        if(isAjoutRealisable(clientToInsert)){
            Operateur current = new InsertionClient(this,clientToInsert,0);
            for(int i=1;i<=this.clients.size();i++){
                Operateur test = new InsertionClient(this,clientToInsert,i);
                if(test.isMouvementRealisable() && test.isMeilleur(current)){
                    current = test;
                }
            }
            return current;
        }
        return new InsertionClient();
    }

    public boolean doInsertion(InsertionClient infos){
        if(infos == null) return false;

        this.coutTotal += infos.getDeltaCout();
        this.demandeTotale +=  infos.getClientToAdd().getDemande();

        this.clients.add(infos.getPosition(), infos.getClientToAdd());

        return check();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tournee tournee = (Tournee) o;
        return capacite == tournee.capacite && coutTotal == tournee.coutTotal && Objects.equals(depot, tournee.depot) && Objects.equals(clients, tournee.clients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depot, clients, capacite, coutTotal);
    }

    @Override
    public String toString() {
        return "Tournee{" +
                "depot=" + depot +
                ", clients=" + clients +
                ", capacite=" + capacite +
                ", demandeTotale=" + demandeTotale +
                ", coutTotal=" + coutTotal +
                '}';
    }

    public static void main(String[] args) {
        Instance instance = null;
        try {
            InstanceReader reader = new InstanceReader("instances/A-n32-k5.vrp");
            instance = reader.readInstance();
            System.out.println("Instance lue avec success !");
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }

        Tournee t1 = new Tournee(instance);

        instance.getClients().forEach(x -> {
            if(!t1.ajouterClient(x)){
                System.out.println("Ajout impossible");
            } else System.out.println("Ajout réussi");
        });

        System.out.println(t1);
    }
}
