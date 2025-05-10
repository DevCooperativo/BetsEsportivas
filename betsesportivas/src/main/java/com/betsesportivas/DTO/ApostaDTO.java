package com.betsesportivas.DTO;

public class ApostaDTO implements IBaseDTO {
    public int Id;
    public int IdJogador;
    public String NomeJogador;
    public double Valor;
    public int IdCompetidor;
    public String NomeCompetidor;
    public int IdCompeticao;
    public String NomeCompeticao;
    public double Odd;

    private JogadorDTO JogadorDTO;
    private CompeticaoDTO CompeticaoDTO;
    private CompetidorDTO CompetidorDTO;

    public ApostaDTO(int id, double valor, double odd, JogadorDTO jogadorDTO, CompeticaoDTO competicaoDTO,
            CompetidorDTO competidorDTO) {
        Id = id;
        Valor = valor;
        Odd = odd;
        JogadorDTO = jogadorDTO;
        CompeticaoDTO = competicaoDTO;
        CompetidorDTO = competidorDTO;
    }

    public ApostaDTO(int id, int idJogador, String nomeJogador, double valor, int idCompetidor, String nomeCompetidor,
            int idCompeticao, String nomeCompeticao, double odd) {
        Id = id;
        IdJogador = idJogador;
        NomeJogador = nomeJogador;
        Valor = valor;
        IdCompetidor = idCompetidor;
        NomeCompetidor = nomeCompetidor;
        IdCompeticao = idCompeticao;
        NomeCompeticao = nomeCompeticao;
        Odd = odd;
    }

    public ApostaDTO(JogadorDTO jogadorDTO, double valor, int idCompetidor, int idCompeticao, double odd) {
        JogadorDTO = jogadorDTO;
        Valor = valor;
        IdCompetidor = idCompetidor;
        IdCompeticao = idCompeticao;
        Odd = odd;
    }

    public ApostaDTO(int id, int idJogador, double valor, int idCompetidor, int idCompeticao, double odd) {
        Id = id;
        IdJogador = idJogador;
        Valor = valor;
        IdCompetidor = idCompetidor;
        IdCompeticao = idCompeticao;
        Odd = odd;
    }

    public ApostaDTO(int idJogador, String nomeJogador, double valor, int idCompetidor, String nomeCompetidor,
            int idCompeticao, String nomeCompeticao, double odd) {
        IdJogador = idJogador;
        NomeJogador = nomeJogador;
        Valor = valor;
        IdCompetidor = idCompetidor;
        NomeCompetidor = nomeCompetidor;
        IdCompeticao = idCompeticao;
        NomeCompeticao = nomeCompeticao;
        Odd = odd;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdJogador() {
        return IdJogador;
    }

    public void setIdJogador(int idJogador) {
        IdJogador = idJogador;
    }

    public String getNomeJogador() {
        return NomeJogador;
    }

    public void setNomeJogador(String nomeJogador) {
        NomeJogador = nomeJogador;
    }

    public double getValor() {
        return Valor;
    }

    public void setValor(double valor) {
        Valor = valor;
    }

    public int getIdCompetidor() {
        return IdCompetidor;
    }

    public void setIdCompetidor(int idCompetidor) {
        IdCompetidor = idCompetidor;
    }

    public String getNomeCompetidor() {
        return NomeCompetidor;
    }

    public void setNomeCompetidor(String nomeCompetidor) {
        NomeCompetidor = nomeCompetidor;
    }

    public int getIdCompeticao() {
        return IdCompeticao;
    }

    public void setIdCompeticao(int idCompeticao) {
        IdCompeticao = idCompeticao;
    }

    public String getNomeCompeticao() {
        return NomeCompeticao;
    }

    public void setNomeCompeticao(String nomeCompeticao) {
        NomeCompeticao = nomeCompeticao;
    }

    public double getOdd() {
        return Odd;
    }

    public void setOdd(double odd) {
        Odd = odd;
    }

    public JogadorDTO getJogadorDTO() {
        return JogadorDTO;
    }

    public void setJogadorDTO(JogadorDTO JogadorDTO) {
        this.JogadorDTO = JogadorDTO;
    }

    public CompeticaoDTO getCompeticaoDTO() {
        return CompeticaoDTO;
    }

    public void setCompeticaoDTO(CompeticaoDTO CompeticaoDTO) {
        this.CompeticaoDTO = CompeticaoDTO;
    }

    public CompetidorDTO getCompetidorDTO() {
        return CompetidorDTO;
    }

    public void setCompetidorDTO(CompetidorDTO CompetidorDTO) {
        this.CompetidorDTO = CompetidorDTO;
    }

    @Override
    public String getNomeFormatado() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
