package br.edu.faculdade.projeto.model;

import jakarta.persistence.*;

@Entity
@Table(name = "fator_contribuinte")
public class FatorContribuinte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Aumentei o limite para 200 porque os nomes dos fatores podem ser longos
    @Column(name = "nome_fator", length = 200)
    private String nome;

    @Column(name = "area", length = 100)
    private String area; 

    @ManyToOne
    @JoinColumn(name = "codigo_ocorrencia")
    private Ocorrencia ocorrencia;

    public FatorContribuinte() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Ocorrencia getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    
}