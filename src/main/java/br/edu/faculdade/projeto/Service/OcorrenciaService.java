package br.edu.faculdade.projeto.Service;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.LocalDate; 
import java.time.format.DateTimeFormatter;


import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;

import br.edu.faculdade.projeto.dao.LocalidadeDao;
import br.edu.faculdade.projeto.model.Localidade;
import br.edu.faculdade.projeto.model.Ocorrencia;

public class OcorrenciaService {

    public String tratarDadoConforme(String valorOriginal) {
        if (valorOriginal == null) return "NÃO INFORMADO";
        
        String valorLimpo = valorOriginal.trim();

        if (valorLimpo.equals("***") || valorLimpo.isEmpty()) {
            return "NÃO INFORMADO";
        }

        return valorLimpo.toUpperCase();
    }

    public void processarArquivoOcorrencia(String caminhoArquivo) {

        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

        try (org.hibernate.Session session = br.edu.faculdade.projeto.util.HibernateUtil.getSessionFactory().openSession();
            CSVReader reader = new CSVReaderBuilder(new InputStreamReader(new FileInputStream(caminhoArquivo), "ISO-8859-1"))
                .withSkipLines(1)
                .withCSVParser(parser)
                .build()) {

            session.beginTransaction();
            
            String[] linha;
            while ((linha = reader.readNext()) != null) {
                
                Long codigo = Long.parseLong(linha[0]);
                String cidadeNome = tratarDadoConforme(linha[8]);
                String ufSigla = tratarDadoConforme(linha[9]);

                if (ufSigla.equals("NÃO INFORMADO") || ufSigla.length() > 2) {
                    ufSigla = "NI"; 
                }

                System.out.println("Lendo Ocorrência: " + codigo + " em " + cidadeNome + "/" + ufSigla);
                
                LocalidadeDao localRepo = new LocalidadeDao(session);
                Localidade localidade = localRepo.buscarPorNomeEUf(cidadeNome, ufSigla);

                if (localidade == null) {
                localidade = new Localidade();
                localidade.setCidade(cidadeNome);
                localidade.setUf(ufSigla);
                localRepo.salvar(localidade); 
                }

                Ocorrencia ocorrencia = new Ocorrencia();
                ocorrencia.setCodigoOcorrencia(codigo);
                ocorrencia.setLocalidade(localidade);

                ocorrencia.setClassificacao(tratarDadoConforme(linha[5]));

                DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                try {
                    LocalDate data = LocalDate.parse(linha[12].trim(), formatador);
                    ocorrencia.setData(data);
                } catch (Exception e) {
                    System.out.println("Aviso: Data inválida ou ausente para a ocorrência " + codigo);
                }
                
                session.persist(ocorrencia);
            }
            
            session.getTransaction().commit();
        
        } catch (Exception e) {
            System.err.println("Erro ao ler o CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
