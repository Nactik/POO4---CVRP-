package solveur;

import instance.Instance;
import instance.reseau.Client;
import instance.reseau.Route;
import solution.Solution;

import java.util.LinkedList;
import java.util.List;

public class InsertionProcheVoisin implements Solveur{

    private Client getNearestClient(Client client, LinkedList<Client> clientsList){
        Client toRet = null;
        int cout = Integer.MAX_VALUE;

        for (Client c : clientsList) {
            if(client.getCoutVers(c) < cout){
                cout = client.getCoutVers(c);
                toRet = c;
            }
        }

        return toRet;
    }

    @Override
    public String getNom() {
        return "Insertion Proche voisin";
    }

    @Override
    public Solution solve(Instance instance) {
        Solution solution = new Solution(instance);
        LinkedList<Client> clientList = instance.getClients();
        Client clientToAdd = clientList.getFirst();

        while(!clientList.isEmpty()){
            if(!solution.addClientLastTournee(clientToAdd)){
                solution.addClientNewTournee(clientToAdd);
            }
            clientList.remove(clientToAdd);
            clientToAdd = getNearestClient(clientToAdd,clientList);
        }

        return solution;
    }
}
