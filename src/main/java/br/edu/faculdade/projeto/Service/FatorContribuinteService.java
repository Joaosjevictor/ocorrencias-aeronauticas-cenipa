package br.edu.faculdade.projeto.Service;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;

import br.edu.faculdade.projeto.model.Ocorrencia;
import br.edu.faculdade.projeto.model.FatorContribuinte;
import br.edu.faculdade.projeto.util.HibernateUtil;

public class FatorContribuinteService {

    public String tratarDadoConforme(String valorOriginal) {
        if (valorOriginal == null) return "NÃO INFORMADO";
        String valorLimpo = valorOriginal.trim();
        if (valorLimpo.equals("***") || valorLimpo.isEmpty()) return "NÃO INFORMADO";
        return valorLimpo.toUpperCase();
    }

    public void processarArquivoFator(String caminhoArquivo) {
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
                    FatorContribuinte fator = new FatorContribuinte();
                    fator.setNome(tratarDadoConforme(linha[1])); 
                    fator.setArea(tratarDadoConforme(linha[2]));  
                    fator.setOcorrencia(ocorrencia);

                    session.persist(fator);
                    count++;
                    
                    if (count % 500 == 0) {
                        System.out.println("Já salvamos " + count + " fatores contribuintes...");
                    }
                }
            }
            session.getTransaction().commit();
            System.out.println("Importação de Fatores concluída! Total inserido: " + count);

        } catch (Exception e) {
            System.err.println("Erro ao ler o CSV de Fatores: " + e.getMessage());
            e.printStackTrace();
        }
    }
}