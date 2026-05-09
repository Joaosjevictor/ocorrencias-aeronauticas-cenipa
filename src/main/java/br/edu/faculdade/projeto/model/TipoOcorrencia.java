package br.edu.faculdade.projeto.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ocorrencia_tipo")
public class TipoOcorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo", length = 100)
    private String tipo;

   
    @ManyToOne
    @JoinColumn(name = "codigo_ocorrencia")
    private Ocorrencia ocorrencia;


    public TipoOcorrencia() {
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getTipo() {
        return tipo;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public Ocorrencia getOcorrencia() {
        return ocorrencia;
    }


    public void setOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
    }


   
}