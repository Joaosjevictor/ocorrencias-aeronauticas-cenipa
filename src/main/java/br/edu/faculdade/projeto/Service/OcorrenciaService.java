package br.edu.faculdade.projeto.Service;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

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
        try (org.hibernate.Session session = br.edu.faculdade.projeto.util.HibernateUtil.getSessionFactory().openSession();
            CSVReader reader = new CSVReaderBuilder(new FileReader(caminhoArquivo, StandardCharsets.UTF_8))
                .withSkipLines(1)
                .build()) {

            session.beginTransaction();
            
            String[] linha;
            while ((linha = reader.readNext()) != null) {
                
                Long codigo = Long.parseLong(linha[0]);
                String cidadeNome = tratarDadoConforme(linha[10]);
                String ufSigla = tratarDadoConforme(linha[11]);

                System.out.println("Lendo Ocorrência: " + codigo + " em " + cidadeNome + "/" + ufSigla);
                
                LocalidadeDao localRepo = new LocalidadeDao(session);
                Localidade localidade = localRepo.buscarPorNomeEUf(cidadeNome, ufSigla);

                if (localidade == null) {
                localidade = new Localidade();
                localidade.setCidade(cidadeNome);
                localidade.setUf(ufSigla);
                localRepo.salvar(localidade); // Cria apenas se não existir
                }

// 3. Associa a Localidade à Ocorrencia
                Ocorrencia ocorrencia = new Ocorrencia();
                ocorrencia.setCodigoOcorrencia(Long.parseLong(linha[0]));
                ocorrencia.setLocalidade(localidade); // Aqui o Hibernate faz a mágica da FK

// 4. Salvar a Ocorrencia
                session.persist(ocorrencia);
            }
            
            session.getTransaction().commit();
        
        } catch (Exception e) {
            System.err.println("Erro ao ler o CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
