package solution;

import instance.Instance;
import instance.reseau.Client;
import instance.reseau.Depot;
import instance.reseau.Point;
import io.InstanceReader;
import io.exception.ReaderException;
import operateur.*;

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

        if(cout == Integer.MAX_VALUE)
            return false;

        this.coutTotal += cout;
        return true;
    }

    private boolean isAjoutRealisable(Client clientToAdd){
        return clientToAdd != null && this.demandeTotale + clientToAdd.getDemande() <= this.capacite;
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

    private Point getNext(int position){
        if(position == this.clients.size()-1)
            return this.depot;

        return  this.clients.get(position+1);
    }

    private boolean isPositionValide(int position){
        return position >= 0 && position < this.clients.size();
    }//OK

    private boolean isPositionInsertionValide(int position){
        return position >= 0 && position <= this.clients.size();
    } //OK

    public int deltaCoutInsertion(int position, Client clientToAdd){
        if(clientToAdd == null || !this.isPositionInsertionValide(position))
            return Integer.MAX_VALUE;

        Point prec = this.getPrec(position);
        Point current = this.getCurrent(position);

        if(prec.getCoutVers(clientToAdd) == Integer.MAX_VALUE || clientToAdd.getCoutVers(current) == Integer.MAX_VALUE)
            return Integer.MAX_VALUE;

        if(prec.equals(current))
            return prec.getCoutVers(clientToAdd) + clientToAdd.getCoutVers(prec);

        return prec.getCoutVers(clientToAdd) + clientToAdd.getCoutVers(current) - prec.getCoutVers(current);
    } //OK

    public int deltaCoutInsertionInter(int position, Client clientToAdd){
        if(!this.isAjoutRealisable(clientToAdd))
            return Integer.MAX_VALUE;
        return deltaCoutInsertion(position,clientToAdd);
    }

    public int deltaCoutSuppression(int position){
        if(!isPositionValide(position) || this.clients.isEmpty())
            return Integer.MAX_VALUE;

        Point prec = this.getPrec(position);
        Point current = this.getCurrent(position);
        Point next = this.getNext(position);

        //Si 1 client, la nouvelle route est de 0 (dépot -> dépot)
        int newCout  = (this.clients.size() == 1) ? 0 : prec.getCoutVers(next);

        return newCout - prec.getCoutVers(current) - current.getCoutVers(next);

    }

    public int deltaCoutDeplacement(int positionI, int positionJ){
        if(!isPositionDeplacementValide(positionI,positionJ))
            return Integer.MAX_VALUE;

        Client clientToAdd = getClientPosition(positionI);

        return this.deltaCoutInsertion(positionJ,clientToAdd) + this.deltaCoutSuppression(positionI);
    }

    public int deltaCoutEchange(int positionI, int positionJ){
        if(!isPositionEchangeValide(positionI,positionJ))
            return Integer.MAX_VALUE;

        Client clientJ = this.getClientPosition(positionJ);
        Client clientI = this.getClientPosition(positionI);

        if(positionJ == positionI+1)
            return deltaCoutEchangeConsecutif(positionI);
        else{
            return deltaCoutRemplacement(positionI, clientJ) + deltaCoutRemplacement(positionJ, clientI);
        }
    }

    private int deltaCoutEchangeConsecutif(int positionI){

        // Attention au cas ou size == 1, osef pour l'instant

        Point precI = this.getPrec(positionI); //prec de I
        Point clientI = this.getCurrent(positionI); //Point I
        Point clientJ = this.getNext(positionI); //Point J du coup
        Point nextJ = this.getNext(positionI + 1); //Point J + 1

        int coutToRemove =  precI.getCoutVers(clientI) + clientI.getCoutVers(clientJ) + clientJ.getCoutVers(nextJ);
        int coutToAdd = precI.getCoutVers(clientJ) + clientJ.getCoutVers(clientI) + clientI.getCoutVers(nextJ);

        return coutToAdd - coutToRemove;
    }

    private int deltaCoutRemplacement(int position, Client client){
        if(client == null)
            return Integer.MAX_VALUE;

        Point toReplace = this.getCurrent(position); //Point à remplacer
        Point precToReplace = this.getPrec(position); //Point avant celui à remplacer
        Point nextToReplace = this.getNext(position); //Point après celui à remplacer

        int coutToRemove = precToReplace.getCoutVers(toReplace) + toReplace.getCoutVers(nextToReplace);
        int coutToAdd = precToReplace.getCoutVers(client) + client.getCoutVers(nextToReplace);

        return  coutToAdd - coutToRemove;
    }

    private boolean isPositionEchangeValide(int positionI, int positionJ){
        if(!isPositionValide(positionI) || !isPositionValide(positionJ))
            return false;

        return positionI < positionJ;
    }

    private boolean isPositionDeplacementValide(int positionI, int positionJ){
        if(!isPositionValide(positionI))
            return false;
        if(!isPositionInsertionValide(positionJ))
            return false;

        int diff = Math.abs(positionI-positionJ);

        return diff != 0 && diff != 1;
    }

    public Operateur getMeilleureInsertion(Client clientToInsert){
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
        return null;
    }

    public boolean doInsertion(InsertionClient infos){
        if(infos == null || this.clients.contains(infos.getClientToAdd())) return false;

        this.coutTotal += infos.getDeltaCout();
        this.demandeTotale +=  infos.getClientToAdd().getDemande();

        this.clients.add(infos.getPosition(), infos.getClientToAdd());

        return check();
    }

    public boolean doDeplacement(IntraDeplacement infos){
        if(infos == null)
            return false;
        if (deltaCoutDeplacement(infos.getPositionI(), infos.getPositionJ()) == Integer.MAX_VALUE)
            return false;

        Client toMove = this.clients.get(infos.getPositionI());
        if(toMove== null) return false;

        this.clients.remove(infos.getPositionI());
        if (infos.getPositionI() > infos.getPositionJ()) {
            this.clients.add(infos.getPositionJ(), toMove);
        }else{
            this.clients.add(infos.getPositionJ()-1, toMove);
        }

        this.coutTotal += infos.getDeltaCout();
        return check();
    }

    public boolean doDeplacement(InterDeplacement infos){
        if(infos == null) return false;

        int posI = infos.getPositionI();
        int posJ = infos.getPositionJ();


        Client toMove = this.clients.get(posI);
        if(toMove == null) return false;

        this.clients.remove(posI);
        this.coutTotal+=infos.getDeltaCoutTournee();
        this.demandeTotale-=toMove.getDemande();

        infos.getAutreTournee().clients.add(posJ, toMove);
        infos.getAutreTournee().coutTotal+=infos.getDeltaCoutAutreTournee();
        infos.getAutreTournee().demandeTotale+=toMove.getDemande();

        return check();
    }

    public  boolean doEchange(IntraEchange infos){
        if(infos == null)
            return false;

        int positionI = infos.getPositionI();
        int positionJ = infos.getPositionJ();

        Client clientI = this.clients.get(positionI);
        Client clientJ = this.clients.get(positionJ);

        if(clientI == null || clientJ == null)
            return false;

        this.clients.remove(positionJ);
        this.clients.add(positionJ,clientI);

        this.clients.remove(positionI);
        this.clients.add(positionI,clientJ);

        this.coutTotal += infos.getDeltaCout();
        return check();
    }

    public Client getClientPosition(int position){
        if(!isPositionValide(position))
            return null;
        return this.clients.get(position);
    }

    public OperateurLocal getMeilleurOperateurIntra(TypeOperateurLocal type) {

        OperateurLocal best = OperateurLocal.getOperateur(type);
        for(int i=0; i<clients.size(); i++) {
            for(int j=0; j<clients.size()+1; j++) {
                OperateurIntraTournee op = OperateurLocal.getOperateurIntra(type, this, i, j);
                if(op != null && op.isMeilleur(best)) {
                    best = op;
                }
            }
        }
        return best;
    }

    public OperateurLocal getMeilleurOperateurInter(Tournee autreTournee,TypeOperateurLocal type) {
        if(autreTournee.equals(this))
            return this.getMeilleurOperateurIntra(TypeOperateurLocal.INTRA_DEPLACEMENT);

        OperateurLocal best = OperateurLocal.getOperateur(type);
        for(int i=0; i<clients.size(); i++) {
            for(int j=0; j<autreTournee.clients.size()+1; j++) {
                OperateurInterTournees op = OperateurLocal.getOperateurInter(type, this, autreTournee,i, j);
                if(op != null && op.isMeilleur(best)) {
                    best = op;
                }
            }
        }
        return best;
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
