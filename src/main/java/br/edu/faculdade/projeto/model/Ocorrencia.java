package br.edu.faculdade.projeto.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;


@Entity
@Table(name = "ocorrencias")
public class Ocorrencia {
    
    @Id
    @Column(name = "codigo_ocorrencia")
    private long codigoOcorrencia;

    @Column(name = "classificacao")
    private String classificacao;

    @Column(name = "data_ocorrencia")
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "id_localidade")
    private Localidade localidade;

    @OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.ALL)
    private List<Aeronave> aeronaves;

    @OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.ALL)
    private List<TipoOcorrencia> tipos;

    @OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.ALL)
    private List<FatorContribuinte> fatores;

    @OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.ALL)
    private List<Recomendacao> recomendacoes;

    public Ocorrencia() {
    }

    public long getCodigoOcorrencia() {
        return codigoOcorrencia;
    }

    public void setCodigoOcorrencia(long codigoOcorrencia) {
        this.codigoOcorrencia = codigoOcorrencia;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Localidade getLocalidade() {
        return localidade;
    }

    public void setLocalidade(Localidade localidade) {
        this.localidade = localidade;
    }


    public List<Aeronave> getAeronaves() {
        return aeronaves;
    }

    public void setAeronaves(List<Aeronave> aeronaves) {
        this.aeronaves = aeronaves;
    }

    public List<TipoOcorrencia> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoOcorrencia> tipos) {
        this.tipos = tipos;
    }

    public List<FatorContribuinte> getFatores() {
        return fatores;
    }

    public void setFatores(List<FatorContribuinte> fatores) {
        this.fatores = fatores;
    }

    public List<Recomendacao> getRecomendacoes() {
        return recomendacoes;
    }

    public void setRecomendacoes(List<Recomendacao> recomendacoes) {
        this.recomendacoes = recomendacoes;
    }
    

}




    