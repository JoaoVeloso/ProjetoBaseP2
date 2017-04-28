package pt.ipleiria.estg.ei.p2.blast;

import pt.ipleiria.estg.ei.p2.blast.modelo.EstadoJogo;
import pt.ipleiria.estg.ei.p2.blast.modelo.Jogo;
import pt.ipleiria.estg.ei.p2.blast.modelo.utils.Posicao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

        Jogo jogo = new Jogo();
        RepresentadorTextual representadorTextual = new RepresentadorTextual(jogo);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        boolean jogadaValida;
        do {
            try {
                representadorTextual.representar();
                jogo.iterar();
                representadorTextual.representar();

                do {
                    System.out.print("Posição a jogar: ");
                    String strPosicao = null;
                    strPosicao = br.readLine();
                    String[] partes = strPosicao.split(",");

                    Posicao jogada = new Posicao(Integer.parseInt(partes[0].trim()), Integer.parseInt(partes[1].trim()));

                    jogadaValida = jogo.interagir(jogada.getLinha(), jogada.getColuna());
                    if (!jogadaValida)
                        representadorTextual.representarJogadaInvalida(jogada.getLinha(), jogada.getColuna());
                } while (!jogadaValida);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (jogo.getEstadoJogo() == EstadoJogo.A_DECORRER);

    }
}
