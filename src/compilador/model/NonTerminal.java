package compilador.model;

import java.util.*;

public class NonTerminal {

    public boolean isFollowCalculated;
    String id;

    List<Production> productions;
    private Boolean hasEpProduction;
    private Set<String> firsts;
    private Boolean hasAllFirstAsTerminals;
    private Set<String> follows;

    public NonTerminal(String id){
        this.id = id.trim();
        this.productions = new ArrayList<>();
        this.firsts = new HashSet<>();
        this.follows = new HashSet<>();
        this.isFollowCalculated = false;
    }

    public void addProduction(Production production){
        this.productions.add(production);
    }

    public Set<String> getFirsts(Grammar grammar){
        if(this.firsts.isEmpty()) {
            Set<String> firsts = new HashSet<>(); // cria o conjunto dos firsts
            if(productions.isEmpty()){
                this.productions = grammar.productions.get(this);
            }
            for (int j = 0; j < this.productions.size(); j++) { // para todas as produções do não terminal
                Production production = this.productions.get(j);
                firsts.addAll(production.getFirsts(grammar));

            }
            this.firsts = firsts;
        }
        return this.firsts;
    }

    public Set<String> getFollows(){
        return follows;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!NonTerminal.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final NonTerminal other = (NonTerminal) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


    public boolean hasEpProduction() {
        if(this.hasEpProduction == null){
            Boolean result = false;
            for(Production production: this.productions){
                if(production.isEpProduction()){
                    result = true;
                }
            }
            this.hasEpProduction = result;
        }
        return this.hasEpProduction.booleanValue();
    }

    public Set<String> getFirstsWithoutLoop() {
        if(firsts == null){
            return new HashSet<>();
        }else{
            return this.firsts;
        }
    }

    public boolean hasAllFirstAsTerminals() {
        if(this.hasAllFirstAsTerminals == null){
            Boolean result = true;
            for(Production production: this.productions){
                if(!production.isFirstSymbolTerminal()){
                    result = false;
                }
            }
            this.hasAllFirstAsTerminals = result;
        }
        return hasAllFirstAsTerminals;
    }

    public boolean hasEpInFirsts() {
        return this.firsts.contains(Production.EPSILON);
    }

    public void addToFollow(Set<String> fo) {
        this.follows.addAll(fo);
    }

    public Set<String> getFirstsWithoutEpsilon(Grammar grammar) {
        Set<String> result = new HashSet<>();
        result.addAll(this.firsts);
        result.remove(Production.EPSILON);
        return result;
    }
}
