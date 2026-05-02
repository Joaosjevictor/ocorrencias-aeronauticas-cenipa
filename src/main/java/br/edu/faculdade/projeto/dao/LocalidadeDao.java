package br.edu.faculdade.projeto.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import br.edu.faculdade.projeto.model.Localidade;

public class LocalidadeDao {
    
    private Session session;

    public LocalidadeDao(Session session) {
        this.session = session;
    }

    public Localidade buscarPorNomeEUf(String nome, String uf) {
        String hql = "FROM Localidade WHERE cidade = :nome AND uf = :uf";
        Query<Localidade> query = session.createQuery(hql, Localidade.class);
        query.setParameter("nome", nome);
        query.setParameter("uf", uf);
        return query.uniqueResult();
    }

    public void salvar(Localidade localidade) {
        session.persist(localidade);
    }


}
