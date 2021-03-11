package solution;

import instance.Instance;
import instance.reseau.Client;
import io.InstanceReader;
import io.exception.ReaderException;
import operateur.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Solution {

    private int coutTotal;
    private List<Tournee> tournees;
    private Instance instance;

    public Solution(Instance instance) {
        this.instance = instance;
        this.tournees = new LinkedList<>();
    }

    public int getCoutTotal() {
        return coutTotal;
    }

    private boolean addClientToTournee(Client clientToAdd, Tournee t){
        int coutTemp = t.getCoutTotal();
        if(t.ajouterClient(clientToAdd)){
            this.coutTotal += t.getCoutTotal() - coutTemp;
            return true;
        };
        return false;
    }

    public boolean addClientNewTournee(Client clientToAdd){
        if(clientToAdd == null) return false;

        Tournee tournee = new Tournee(this.instance);
        if(!tournee.ajouterClient(clientToAdd)) return false;

        this.tournees.add(tournee);

        this.coutTotal += tournee.getCoutTotal();
        return true;
    }

    public boolean addClient(Client clientToAdd){
        if(clientToAdd == null) return false;

        for(Tournee t : tournees){
            if(this.addClientToTournee(clientToAdd,t)) return true;
        }
        return false;
    }

    public boolean addClientLastTournee(Client clientToAdd){
        if(clientToAdd == null) return false;
        if(tournees.isEmpty()) return false;
        Tournee lastTournee = ((LinkedList<Tournee>)tournees).getLast();
        return this.addClientToTournee(clientToAdd,lastTournee);
    }

    public Operateur getMeilleureInsertion(Client clientToInsert){
        Operateur current = null;
        if(!this.tournees.isEmpty()){
            current = this.tournees.get(0).getMeilleureInsertion(clientToInsert);
            if(current != null) {
                for (int i = 1; i < this.tournees.size(); i++) {
                    Operateur test = this.tournees.get(i).getMeilleureInsertion(clientToInsert);
                    if (test != null && test.isMeilleur(current))
                        current = test;
                }
            }
        }
        return  current;
    }

    private OperateurLocal getMeilleurOperateurIntra(TypeOperateurLocal type){
        OperateurLocal best = null;
        if(!this.tournees.isEmpty()){
            best = this.tournees.get(0).getMeilleurOperateurIntra(TypeOperateurLocal.INTRA_DEPLACEMENT);
            if(best != null){
                for (int i = 1; i < this.tournees.size(); i++) {
                    OperateurLocal test = this.tournees.get(i).getMeilleurOperateurIntra(TypeOperateurLocal.INTRA_DEPLACEMENT);
                    if (test != null && test.isMeilleur(best))
                        best = test;
                }
            }
        }
        return best;
    }


    public OperateurLocal getMeilleurOperateur(TypeOperateurLocal type){
        if(OperateurLocal.getOperateur(type) instanceof OperateurIntraTournee){
            return this.getMeilleurOperateurIntra(type);
        }else{
            return null;
        }
    }

    public boolean doInsertion(InsertionClient infos){
        if(infos.doMouvementIfRealisable()) {
            this.coutTotal += infos.getDeltaCout();
            return true;
        }
        return false;
    }

    public boolean doMouvementRechercheLocale(OperateurLocal infos){
        if(infos.doMouvementIfRealisable()) {
            this.coutTotal += infos.getDeltaCout();
            return true;
        }
        return false;
    }

    private boolean checkCoutTotal(){
        int cout = 0;

        for (Tournee t: this.tournees) {
            cout += t.getCoutTotal();
        }

        return cout == coutTotal;
    }

    private boolean checkClient(){
        for(Client c : this.instance.getClients()){
            int nbOccurence = 0;
            for(Tournee t : this.tournees){
                for(Client client : t.getClients()){
                    if(client.equals(c)) nbOccurence ++;
                }
            }

            if(nbOccurence != 1) return false;
        }
        return true;
    }

    public boolean check(){
        for(Tournee t : this.tournees){
            if(!t.check()){
                System.err.println("Erreur Tournées non réalisable");
                return false;
            }
        }
        if(!this.checkCoutTotal()){
            System.err.println("Erreur calcul cout (Solution)");
            return false;
        }
        if(!this.checkClient()){
            System.err.println("Erreur client présent plusieurs fois");
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution solution = (Solution) o;
        return Objects.equals(tournees, solution.tournees) && Objects.equals(instance, solution.instance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tournees, instance);
    }

    @Override
    public String toString() {
        return "Solution{" +
                "coutTotal=" + coutTotal +
                ", tournees=" + tournees +
                ", instance=" + instance +
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

        Solution s1 = new Solution(instance);

        for(Client client : instance.getClients()){
            if(s1.addClient(client)){
                System.out.println("ajout dans une tournée existante");
            } else{
                if(s1.addClientNewTournee(client)){
                    System.out.println("Ajout dans une nouvelle tournée");
                }
                else System.out.println("Ajout impossible");
            }
        }

        if(s1.check()){
            System.out.println("C'est bon");
        } else {
            System.out.println("C'est pas bon");
        }

    }
}
