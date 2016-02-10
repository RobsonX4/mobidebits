package br.com.mobidebits.beans;

import java.io.Serializable;
import java.util.List;

import br.com.mobidebits.dao.Tabela;

/**
 * Created by Robson on 13/12/2015.
 */
public class Usuario implements Serializable, Tabela {

    private Integer id;
    private String nome;
    private String email;
    private String usuario;
    private String senha;
    private Integer status;
    private List<Divida> dividas;

    //Metodos Getters & Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Divida> getDividas() {
        return dividas;
    }

    public void setDividas(List<Divida> dividas) {
        this.dividas = dividas;
    }

    //Para gerar tabela
    public static final String TABELA   = "usuarios";
    public static final String ID       = "id";
    public static final String NOME     = "nome";
    public static final String EMAIL    = "email";
    public static final String USUARIO  = "usuario";
    public static final String SENHA    = "senha";
    public static final String STATUS   = "status";

    @Override
    public String getTabela() {
        String tabela = (
                "CREATE TABLE " + TABELA + " ("
                        + ID        + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + NOME      + " TEXT NOT NULL,"
                        + EMAIL     + " TEXT NOT NULL,"
                        + USUARIO   + " TEXT NOT NULL,"
                        + SENHA     + " TEXT NOT NULL,"
                        + STATUS    + " INTEGER NOT NULL"
                        + ")"
        );
        return tabela;
    }
}