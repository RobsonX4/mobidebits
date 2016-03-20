package br.com.mobidebits.control;

import android.content.Context;

import br.com.mobidebits.beans.Usuario;
import br.com.mobidebits.dao.DAOImpl.UsuarioDAOImpl;
import br.com.mobidebits.dao.UsuarioDAO;

/**
 * Created by Robson on 21/12/2015.
 */
public class LoginControl {

    private UsuarioDAO usuarioDAO;

    public LoginControl(Context context) {
        this.usuarioDAO = new UsuarioDAOImpl(context);
    }

    public Usuario entrar(Usuario usuario) {
        return usuarioDAO.consultar(usuario.getUsuario(),usuario.getSenha());
    }

    public boolean inserir(Usuario usuario) {
        if (usuarioDAO.inserir(usuario))
            return true;
        return false;
    }

    //Validação dos campos para Tela de login e Cadastro
    public boolean validateLogin(Usuario usuario) {
        if (usuario.getUsuario().isEmpty() || usuario.getUsuario() == null ||
                usuario.getSenha().isEmpty() || usuario.getSenha() == null)
            return false;
        return true;
    }

    public boolean validateCadastro(Usuario usuario) {
        if (usuario.getNome().isEmpty() || usuario.getNome() == null
            || usuario.getEmail().isEmpty() || usuario.getEmail() == null
                || usuario.getUsuario().isEmpty() || usuario.getUsuario() == null
                    || usuario.getSenha().isEmpty() || usuario.getSenha() == null)
            return false;
        return true;
    }
}
