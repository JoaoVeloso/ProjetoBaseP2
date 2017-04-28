package pt.ipleiria.estg.ei.p2.blast.modelo.objetivos;

import pt.ipleiria.estg.ei.p2.blast.modelo.Objetivavel;

public abstract class ObjetivoParcial extends Objetivo {
    protected int quantidade;

    public ObjetivoParcial(int quantidade) {
        this.quantidade = quantidade;
    }

    public abstract boolean hasInfluencia(Objetivavel objetivavel);

    @Override
    public boolean isConcluido() {
        return quantidade == 0;
    }

    public int getQuantidade() {
        return quantidade;
    }

}
