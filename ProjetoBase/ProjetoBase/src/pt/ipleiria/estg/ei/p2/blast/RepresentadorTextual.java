package pt.ipleiria.estg.ei.p2.blast;

import pt.ipleiria.estg.ei.p2.blast.modelo.AreaJogavel;
import pt.ipleiria.estg.ei.p2.blast.modelo.Especie;
import pt.ipleiria.estg.ei.p2.blast.modelo.Jogo;
import pt.ipleiria.estg.ei.p2.blast.modelo.OuvinteJogo;
import pt.ipleiria.estg.ei.p2.blast.modelo.bases.Base;
import pt.ipleiria.estg.ei.p2.blast.modelo.bases.BaseAr;
import pt.ipleiria.estg.ei.p2.blast.modelo.bases.BaseSuportadora;
import pt.ipleiria.estg.ei.p2.blast.modelo.objetivos.ObjetivoParcial;
import pt.ipleiria.estg.ei.p2.blast.modelo.objetivos.ObjetivoParcialBalao;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.*;
import pt.ipleiria.estg.ei.p2.blast.modelo.utils.Direcao;
import pt.ipleiria.estg.ei.p2.blast.modelo.utils.Posicao;

public class RepresentadorTextual implements OuvinteJogo {
    private Jogo jogo;

    public RepresentadorTextual(Jogo jogo) {
        this.jogo = jogo;
        jogo.adicionarOuvinte(this);
    }

    public void representar() {

        System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒"); //ALT + 177 (numeric keypad)
        representarAreaJogavel();
        representarPontuacao();
        representarNumeroMovimentosRestantes();
        representarObjetivo();
    }

    private void representarAreaJogavel() {
        AreaJogavel areaJogavel = jogo.getAreaJogavel();
        int numeroLinhas = areaJogavel.getNumeroLinhas();
        int numeroColunas = areaJogavel.getNumeroColunas();
        System.out.print("  ");
        for (int i = 0; i < numeroColunas; i++)
            System.out.print(i + " ");
        System.out.println();
        for (int i = 0; i < numeroLinhas; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < numeroColunas; j++) {
                representar(areaJogavel.getBase(i, j));
            }
            System.out.println();
        }
    }

    private void representar(Base base) {
        String texto = "";
        if (base instanceof BaseAr) {
            texto += " ";
        } else if (base instanceof BaseSuportadora) {

            BaseSuportadora baseSuportadora = (BaseSuportadora) base;
            if (baseSuportadora.isVazia())
                texto += "-";
            else {
                Suportado suportado = baseSuportadora.getSuportado();
                if (suportado instanceof Porco) {
                    Porco porco = (Porco) baseSuportadora.getSuportado();
                    if (porco.getForca() > 1)
                        texto += "P";
                    else
                        texto += "p";
                } else if (suportado instanceof Madeira) {
                    Madeira madeira = (Madeira) baseSuportadora.getSuportado();
                    if (madeira.getForca() > 1)
                        texto += "#";
                    else
                        texto += "+";
                } else if (suportado instanceof Vidro) {
                    texto += ".";
                } else if (suportado instanceof Foguete) {
                    if (((Foguete) suportado).getDirecao() == Direcao.HORIZONTAL)
                        texto += ">";
                    else
                        texto += "^";
                } else {
                    texto += ((Balao) baseSuportadora.getSuportado()).getEspecie().getInicial();
                }
            }
        }
        System.out.print(texto + " ");
    }

    private void representarPontuacao() {
        System.out.println("Pontuação: " + jogo.getPontuacao());
    }

    private void representarNumeroMovimentosRestantes() {
        System.out.println("Número de Movimentos Restantes: " + jogo.getNumeroMovimentosRestantes());
    }

    private void representarObjetivo() {
        System.out.println("Objetivos:");
        for (int i = 0; i < jogo.getObjetivoJogo().getNumeroObjetivosParciais(); i++) {
            ObjetivoParcial objetivoParcial = jogo.getObjetivoJogo().getObjetivoParcial(i);
            representar(objetivoParcial);
        }
    }

    public void representar(ObjetivoParcial objetivoParcial) {

        if (objetivoParcial instanceof ObjetivoParcialBalao) {
            ObjetivoParcialBalao objetivo = (ObjetivoParcialBalao) objetivoParcial;

            Especie especie = objetivo.getEspecie();
            System.out.println("Balões " + objetivo.getEspecie() + ": " + objetivo.getQuantidade());
        } else {
            System.out.println("Porcos: " + objetivoParcial.getQuantidade());
        }
    }

    public void representarJogadaInvalida(int linha, int coluna) {
        System.out.println("Jogada Inválida (linha " + linha + ", coluna " + coluna + ")");
    }

    private String getRepresentacaoPosicaoSuportado(Suportado suportado) {
        return getRepresentacaoPosicao(suportado.getBaseSuportadora().getPosicao());
    }

    private String getRepresentacaoPosicao(Posicao posicao) {
        return "(" + posicao.getLinha() + "," + posicao.getColuna() + ")";
    }

    @Override
    public void suportadoExplodiu(Suportado suportado) {
        System.out.println("O suportado em " + getRepresentacaoPosicaoSuportado(suportado) + " explodiu!");
    }

    @Override
    public void suportadoAgrupavelMovimentou(SuportadoAgrupavel<?> suportado, BaseSuportadora origem, BaseSuportadora destino) {
        System.out.println("O suportado movimentou-se de " + (origem == null ? "fora do tabuleiro" : getRepresentacaoPosicao(origem.getPosicao())) + " para " +
                getRepresentacaoPosicao(destino.getPosicao()));
    }

    @Override
    public void objetivosConcluidos() {
        System.out.println("Parabéns!!!!!\nTodos os objetivos foram concluídos!");
    }

    @Override
    public void movimentosEsgotados() {
        System.out.println("GAME OVER!!!!\nTenta novamente!");
    }

    @Override
    public void suportadoDestruidoParcialmente(SuportadoSensivelOndaChoqueComForca suportado, float percentagemRestante) {
        System.out.println("O suportado em " + getRepresentacaoPosicaoSuportado(suportado) +
                " foi parcialmente destruido. Resta " + (percentagemRestante * 100) + "%");
    }

    @Override
    public void fogueteLancado(Foguete foguete) {
        System.out.println("O foguete foi lançado na direção " + foguete.getDirecao());
    }

    @Override
    public void combinacaoFoguetesLancados(Foguete foguete) {
        System.out.println("A combinação de foguetes foi lançada em " + getRepresentacaoPosicaoSuportado(foguete));
    }

    @Override
    public void fogueteMudaDirecao(Foguete foguete) {
        System.out.println("O foguete em " + getRepresentacaoPosicaoSuportado(foguete) + " mudou de direção");
    }

    @Override
    public void porcoCriado(Porco porco, BaseSuportadora baseSuportadora) {
        System.out.println("Foi criado um porco em " + getRepresentacaoPosicao(baseSuportadora.getPosicao()));
    }

    @Override
    public void vidroCriado(Vidro vidro, BaseSuportadora baseSuportadora) {
        System.out.println("Foi criado um vidro em " + getRepresentacaoPosicao(baseSuportadora.getPosicao()));
    }

    @Override
    public void madeiraCriada(Madeira madeira, BaseSuportadora baseSuportadora) {
        System.out.println("Foi criada uma madeira em " + getRepresentacaoPosicao(baseSuportadora.getPosicao()));
    }

    @Override
    public void fogueteCriado(Foguete foguete, BaseSuportadora baseSuportadora) {
        System.out.println("Foi criado um foguete em " + getRepresentacaoPosicao(baseSuportadora.getPosicao()));
    }

    @Override
    public void balaoCriado(Balao balao, Base baseInsercao) {
        System.out.println("Foi criado um balão em " + getRepresentacaoPosicao(baseInsercao.getPosicao()));
    }

}
