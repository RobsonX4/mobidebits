package br.com.mobidebits.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import br.com.mobidebits.beans.Usuario;
import br.com.mobidebits.control.LoginControl;
import br.com.mobidebits.R;

/**
 * Created by Robson on 12/12/2015.
 */
public class CadastrarUsuarioActivity extends Activity {

    private LoginControl login;

    private EditText nomeEditText;
    private EditText emailEditText;
    private EditText usuarioEditText;
    private EditText senhaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_usuario);

        login = new LoginControl(getBaseContext());

        nomeEditText    = (EditText) findViewById(R.id.nome);
        emailEditText   = (EditText) findViewById(R.id.email);
        usuarioEditText = (EditText) findViewById(R.id.usuario);
        senhaEditText   = (EditText) findViewById(R.id.senha);
    }

    public void cadastrar(View view) {
        Usuario usuario = new Usuario();
        usuario.setNome(nomeEditText.getText().toString());
        usuario.setEmail(emailEditText.getText().toString());
        usuario.setUsuario(usuarioEditText.getText().toString());
        usuario.setSenha(senhaEditText.getText().toString());
        usuario.setStatus(1);

        if (!login.validateCadastro(usuario))
            Toast.makeText(this, "Preencha os campos para efetuar cadastro.", Toast.LENGTH_SHORT).show();
        else {
            if (login.inserir(usuario)) {
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
            } else
                Toast.makeText(this, "Ops. 'Usuario' ou 'Email' já cadastrado.", Toast.LENGTH_SHORT).show();
        }
    }

    public void voltar(View view) {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }
}