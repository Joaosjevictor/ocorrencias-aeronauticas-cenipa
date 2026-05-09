package br.edu.faculdade.projeto.model;

import jakarta.persistence.*;


@Entity
@Table(name = "aeronave")
public class Aeronave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "matricula", length = 20)
    private String matricula;

    @Column(name = "equipamento", length = 50)
    private String equipamento;

    @Column(name = "fabricante", length = 100)
    private String fabricante;

    @Column(name = "modelo", length = 50)
    private String modelo;

    @Column(name = "fase_operacao", length = 50)
    private String faseOperacao;

    @ManyToOne
    @JoinColumn(name = "codigo_ocorrencia")
    private Ocorrencia ocorrencia;

    public Aeronave() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFaseOperacao() {
        return faseOperacao;
    }

    public void setFaseOperacao(String faseOperacao) {
        this.faseOperacao = faseOperacao;
    }

    public Ocorrencia getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    
}
