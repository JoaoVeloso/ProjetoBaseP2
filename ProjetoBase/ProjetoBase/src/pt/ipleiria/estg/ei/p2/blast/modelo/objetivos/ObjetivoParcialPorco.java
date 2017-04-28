package pt.ipleiria.estg.ei.p2.blast.modelo.objetivos;

import pt.ipleiria.estg.ei.p2.blast.modelo.Objetivavel;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.Porco;

public class ObjetivoParcialPorco extends ObjetivoParcial {

    public ObjetivoParcialPorco(int quantidade) {
        super(quantidade);
    }

    @Override
    public boolean hasInfluencia(Objetivavel objetivavel) {
        return objetivavel instanceof Porco;
    }

    @Override
    public void influenciar(Objetivavel objetivavel) {
        if (hasInfluencia(objetivavel)) {
            quantidade = Math.max(0, quantidade - 1);
        }
    }
}
