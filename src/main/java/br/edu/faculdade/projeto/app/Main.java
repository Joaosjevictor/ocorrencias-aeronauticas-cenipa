package br.edu.faculdade.projeto.app;

import br.edu.faculdade.projeto.Service.OcorrenciaService;
import br.edu.faculdade.projeto.util.HibernateUtil;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando a importação de dados do CENIPA...");

        // Instancia o serviço que criamos
        OcorrenciaService service = new OcorrenciaService();

        // Aponta para o arquivo CSV que você colocou na pasta resources
        String caminhoArquivo = "src/main/resources/ocorrencia.csv";

        // Executa o processamento
        service.processarArquivoOcorrencia(caminhoArquivo);

        // Fecha a fábrica de conexões do Hibernate para o programa poder encerrar
        HibernateUtil.shutdown();
        
        System.out.println("Processo finalizado com sucesso!");
    }

}
