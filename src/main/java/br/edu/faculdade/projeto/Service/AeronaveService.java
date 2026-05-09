package br.edu.faculdade.projeto.Service;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;

import br.edu.faculdade.projeto.model.Aeronave;
import br.edu.faculdade.projeto.model.Ocorrencia;
import br.edu.faculdade.projeto.util.HibernateUtil;

public class AeronaveService {

    public String tratarDadoConforme(String valorOriginal) {
        if (valorOriginal == null) return "NÃO INFORMADO";
        String valorLimpo = valorOriginal.trim();
        if (valorLimpo.equals("***") || valorLimpo.isEmpty()) {
            return "NÃO INFORMADO";
        }
        return valorLimpo.toUpperCase();
    }

    public void processarArquivoAeronave(String caminhoArquivo) {
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
                // Pula o cabeçalho, caso o OpenCSV se perca
                if (linha[0].contains("codigo")) continue;

                Long codigoOcorrencia = Long.parseLong(linha[0]);

                // A MÁGICA AQUI: Busca a ocorrência que já existe no banco de dados
                Ocorrencia ocorrencia = session.get(Ocorrencia.class, codigoOcorrencia);

                // Só salva a aeronave se a ocorrência existir (para não quebrar a Foreign Key)
                if (ocorrencia != null) {
                    Aeronave aeronave = new Aeronave();
                    
                    // IMPORTANTE: Olhe no seu aeronave.csv para confirmar essas colunas!
                    aeronave.setMatricula(tratarDadoConforme(linha[1]));
                    aeronave.setEquipamento(tratarDadoConforme(linha[2])); 
                    aeronave.setFabricante(tratarDadoConforme(linha[3]));  
                    aeronave.setModelo(tratarDadoConforme(linha[4]));      
                    
                    // A fase de operação costuma ficar lá pro final da planilha
                    aeronave.setFaseOperacao(tratarDadoConforme(linha[16])); 

                    // Liga a aeronave à ocorrência
                    aeronave.setOcorrencia(ocorrencia);

                    session.persist(aeronave);
                    count++;
                    
                    // Imprime a cada 500 registros para não "inundar" seu terminal
                    if (count % 500 == 0) {
                        System.out.println("Já salvamos " + count + " aeronaves...");
                    }

                } else {
                    // Algumas aeronaves no CSV podem não ter a ocorrência correspondente (falha nos dados do governo)
                    System.out.println("Aviso: Ocorrência " + codigoOcorrencia + " não encontrada no banco. Aeronave ignorada.");
                }
            }

            session.getTransaction().commit();
            System.out.println("Importação de Aeronaves concluída com Sucesso! Total inserido: " + count);

        } catch (Exception e) {
            System.err.println("Erro ao ler o CSV de Aeronaves: " + e.getMessage());
            e.printStackTrace();
        }
    }
}