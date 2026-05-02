package br.edu.faculdade.projeto.model;

import java.time.LocalDate;

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


}
    