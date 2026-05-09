package br.edu.faculdade.projeto.app;

import br.edu.faculdade.projeto.Service.AeronaveService;
import br.edu.faculdade.projeto.Service.FatorContribuinteService;
import br.edu.faculdade.projeto.Service.OcorrenciaService;
import br.edu.faculdade.projeto.Service.RecomendacaoService;
import br.edu.faculdade.projeto.Service.TipoOcorrenciaService;
import br.edu.faculdade.projeto.util.HibernateUtil;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando a importação de dados do CENIPA...");

        
       /*   OcorrenciaService service = new OcorrenciaService();
        String caminhoArquivo = "src/main/resources/ocorrencia.csv";
        service.processarArquivoOcorrencia(caminhoArquivo);

        AeronaveService aeronaveService = new AeronaveService();
        String caminhoAeronave = "src/main/resources/aeronave.csv";
        aeronaveService.processarArquivoAeronave(caminhoAeronave);

        TipoOcorrenciaService tipoService = new TipoOcorrenciaService();
        String caminhoTipo = "src/main/resources/ocorrencia_tipo.csv";
        tipoService.processarArquivoTipo(caminhoTipo);

        FatorContribuinteService fatorService = new FatorContribuinteService();
        String caminhoFator = "src/main/resources/fator_contribuinte.csv";
        System.out.println("\n--- Importando Fatores Contribuintes ---");
        fatorService.processarArquivoFator(caminhoFator); */

        RecomendacaoService recomendacaoService = new RecomendacaoService();
        String caminhoRecomendacao = "src/main/resources/recomendacao.csv";
        System.out.println("\n--- Importando Recomendações ---");
        recomendacaoService.processarArquivoRecomendacao(caminhoRecomendacao);
        

        // Fecha a fábrica de conexões do Hibernate para o programa poder encerrar
        HibernateUtil.shutdown();
        
        System.out.println("Processo finalizado com sucesso!");
    }

}
