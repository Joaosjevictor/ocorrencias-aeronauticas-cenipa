package br.edu.faculdade.projeto.Service;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;

import br.edu.faculdade.projeto.model.Ocorrencia;
import br.edu.faculdade.projeto.model.Recomendacao;
import br.edu.faculdade.projeto.util.HibernateUtil;

public class RecomendacaoService {

    public String tratarDadoConforme(String valorOriginal) {
        if (valorOriginal == null) return "NÃO INFORMADO";
        String valorLimpo = valorOriginal.trim();
        if (valorLimpo.equals("***") || valorLimpo.isEmpty()) return "NÃO INFORMADO";
        return valorLimpo.toUpperCase();
    }

    public void processarArquivoRecomendacao(String caminhoArquivo) {
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

        try (org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession();
             CSVReader reader = new CSVReaderBuilder(new InputStreamReader(new FileInputStream(caminhoArquivo), "ISO-8859-1"))
                 .withSkipLines(1)
                 .withCSVParser(parser)
                 .build()) {

            session.beginTransaction();
            String[] linha;
            int count = 0;

            while ((linha = reader.readNext()) != null) {
                if (linha[0].contains("codigo")) continue;

                Long codigoOcorrencia = Long.parseLong(linha[0]);
                Ocorrencia ocorrencia = session.get(Ocorrencia.class, codigoOcorrencia);

                if (ocorrencia != null) {
                    Recomendacao recomendacao = new Recomendacao();
                    
                    // Verifique as colunas no seu CSV!
                    recomendacao.setNumero(tratarDadoConforme(linha[1])); 
                    recomendacao.setConteudo(tratarDadoConforme(linha[5]));  
                    recomendacao.setStatus(tratarDadoConforme(linha[6]));  
                    recomendacao.setOcorrencia(ocorrencia);

                    session.persist(recomendacao);
                    count++;
                    
                    if (count % 500 == 0) {
                        System.out.println("Já salvamos " + count + " recomendações...");
                    }
                }
            }
            session.getTransaction().commit();
            System.out.println("Importação de Recomendações concluída! Total inserido: " + count);

        } catch (Exception e) {
            System.err.println("Erro ao ler o CSV de Recomendações: " + e.getMessage());
            e.printStackTrace();
        }
    }
}