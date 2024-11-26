package utils;

import java.sql.Connection;

public class ConexaoBanco { // classe ConexaoBanco

    private String url;
    private String usuario;
    private String senha;

    // construtor
    public ConexaoBanco(String url, String usuario, String senha){
        this.url = url;
        this.usuario = usuario;
        this.senha = senha;
    }

//    public Connection conectar(){
//        // ...
//    }
//
//    public void desconectar(){
//        // ...
//    }
}