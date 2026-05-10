# ✈️ CENIPA - ETL e Análise de Ocorrências Aeronáuticas

Este projeto consiste em um pipeline de **Engenharia de Dados (ETL)** desenvolvido em Java, focado na extração, tratamento, normalização e persistência de dados governamentais abertos fornecidos pelo CENIPA (Centro de Investigação e Prevenção de Acidentes Aeronáuticos). O repositório também contempla a visualização e análise estatística dos dados estruturados através de um Dashboard interativo.

## 🎯 Objetivo do Projeto
Processar grandes volumes de dados brutos (arquivos CSV "planilhados"), higienizar inconsistências e aplicar a **Normalização de Banco de Dados** mapeando as informações para um modelo relacional robusto. O objetivo final é viabilizar a análise de indicadores de segurança de voo no Brasil, respondendo a perguntas estruturais: *Onde, O quê, Por que e Como evitar*.

## 🚀 Tecnologias Utilizadas
* **Back-end:** Java
* **ORM (Object-Relational Mapping):** Hibernate / JPA
* **Banco de Dados:** PostgreSQL
* **Processamento de Arquivos:** OpenCSV
* **Data Visualization (BI):** Microsoft Power BI

## ⚙️ Arquitetura e Modelagem de Dados
O sistema quebra os arquivos únicos do governo em um modelo relacional rigoroso, utilizando chaves estrangeiras (Foreign Keys) para evitar redundância e garantir a integridade. A tabela `Ocorrencias` atua como o núcleo do sistema.

Relacionamentos implementados:
* `Localidade` (1:N) `Ocorrencia` - Onde o fato ocorreu.
* `Ocorrencia` (1:N) `Aeronave` - Quais equipamentos estavam envolvidos.
* `Ocorrencia` (1:N) `TipoOcorrencia` - O que aconteceu (ex: Falha de Motor).
* `Ocorrencia` (1:N) `FatorContribuinte` - Por que aconteceu (ex: Condição Meteorológica).
* `Ocorrencia` (1:N) `Recomendacao` - Ações preventivas emitidas.

## 🧹 Processo de Higienização (Data Cleaning)
Durante a leitura dos dados, o sistema aplica regras de negócio para tratar dados sujos ou ausentes herdados da base oficial, como:
* Conversão automática de strings nulas, vazias ou preenchidas com `***` para o padrão `"NÃO INFORMADO"`.
* Validação de tamanho de colunas (ex: adequação de matrículas de aeronaves e siglas de UF).
* Tratamento de exceções (`try-catch` isolados) para garantir que linhas corrompidas no CSV não quebrem a persistência do lote.

## 📊 Dashboard Analítico
<img width="1301" height="735" alt="image" src="https://github.com/user-attachments/assets/d9971da9-4676-4d99-ab79-e5a85e05d0da" />

> O Dashboard consome diretamente as views geradas no PostgreSQL, permitindo a filtragem dinâmica de Acidentes e Incidentes por UF, Fator Contribuinte e linha do tempo histórica.

## 🛠️ Como executar o projeto

1. **Configuração do Banco de Dados:**
   * Crie um banco de dados no PostgreSQL chamado `OcorrenciasAeronauticas`.
   * Atualize as credenciais (usuário e senha) no arquivo `hibernate.cfg.xml`.
2. **Arquivos de Origem:**
   * Certifique-se de que os arquivos `.csv` do CENIPA estão localizados na pasta `src/main/resources/`.
3. **Execução do ETL:**
   * Execute a classe `Main.java`. O Hibernate recriará as tabelas e o console exibirá o progresso da importação em lotes de 500 registros.
4. **Visualização:**
   * Abra o arquivo `.pbix` (Power BI), vá em *Transformar Dados > Configurações da Fonte de Dados* e aponte para o seu `localhost` do PostgreSQL.

## 👨‍💻 Autor

**João Victor Q. de Barros** *Estudante de Análise e Desenvolvimento de Sistemas* [www.linkedin.com/in/joaovictorqueiroz-ads]
