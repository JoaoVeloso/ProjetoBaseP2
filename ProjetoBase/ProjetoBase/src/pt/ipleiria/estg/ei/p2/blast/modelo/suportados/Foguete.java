package pt.ipleiria.estg.ei.p2.blast.modelo.suportados;

import pt.ipleiria.estg.ei.p2.blast.modelo.bases.BaseSuportadora;
import pt.ipleiria.estg.ei.p2.blast.modelo.utils.Direcao;

import java.util.List;

public class Foguete extends SuportadoAgrupavel {

    private Direcao direcao;

    public Foguete(BaseSuportadora base) {
        super(base);
        direcao = Direcao.values()[base.getAreaJogavel().getValorAleatorio(Direcao.values().length)];
    }

    @Override
    public void reagirInteracao() {
        List<Foguete> grupo = getBaseSuportadora().getGrupoFormado();

        int posicao = direcao == Direcao.VERTICAL ? baseSuportadora.getPosicao().getColuna() : baseSuportadora.getPosicao().getLinha();

        for (Foguete v : grupo)
            v.explodir();

        if (grupo.size() == 1) {// só o próprio
            getJogo().informarFogueteLancado(this);
            if (direcao == Direcao.VERTICAL)
                destruirColuna(posicao);
            else
                destruirLinha(posicao);
        } else {
            getJogo().informarCombinacaoFoguetesLancados(this);
            destruirColuna(baseSuportadora.getPosicao().getColuna());
            destruirLinha(baseSuportadora.getPosicao().getLinha());
        }
    }

    @Override
    public boolean agrupaCom(SuportadoAgrupavel suportado) {
        return suportado instanceof Foguete;
    }

    @Override
    public boolean podeInteragir() {
        return true;
    }

    public Direcao getDirecao() {
        return direcao;
    }

    @Override
    public void reagirFoguete() {
        reagirInteracao();
    }

    private void destruirLinha(int linha) {
        List<BaseSuportadora> bases = baseSuportadora.getAreaJogavel().getBasesSuporteDaLinha(linha);
        for (BaseSuportadora base : bases) {
            base.reagirFoguete();
        }
    }

    private void destruirColuna(int coluna) {
        List<BaseSuportadora> bases = baseSuportadora.getAreaJogavel().getBasesSuporteDaColuna(coluna);
        for (BaseSuportadora base : bases) {
            base.reagirFoguete();
        }
    }

    public void inverterDirecao() {
        direcao = direcao.perpendicular();
        getJogo().informarMudancaDirecaoFoguete(this);
    }

}
