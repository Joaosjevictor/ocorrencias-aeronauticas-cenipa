package br.edu.faculdade.projeto.model;

import jakarta.persistence.*;

@Entity
@Table(name = "recomendacao")
public class Recomendacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_recomendacao", length = 50)
    private String numero; 

    
    @Column(name = "conteudo", columnDefinition = "TEXT")
    private String conteudo;

    @Column(name = "status", length = 50)
    private String status; 

    @ManyToOne
    @JoinColumn(name = "codigo_ocorrencia")
    private Ocorrencia ocorrencia;

    public Recomendacao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Ocorrencia getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

}   
