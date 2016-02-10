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
public class LoginActivity extends Activity {

    private LoginControl loginControl;

    private EditText usuarioEditText;
    private EditText senhaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginControl = new LoginControl(getBaseContext());

        usuarioEditText = (EditText) findViewById(R.id.usuarioLogin);
        senhaEditText = (EditText) findViewById(R.id.senhaLogin);
    }

    public void cadastrar(View view) {
        Intent cadastrar = new Intent(this, CadastrarUsuarioActivity.class);
        startActivity(cadastrar);
    }

    public void entrar(View view) {
        Usuario usuario = new Usuario();
        usuario.setUsuario(usuarioEditText.getText().toString());
        usuario.setSenha(senhaEditText.getText().toString());

        if (!loginControl.validateLogin(usuario))
            Toast.makeText(this, "Preencha os campos para efetuar login!", Toast.LENGTH_SHORT).show();
        else {
            usuario = loginControl.entrar(usuario);
            if (usuario != null) {
                Bundle parametros = new Bundle();
                parametros.putInt("idUsuario", usuario.getId());

                Intent home = new Intent(this, HomeActivity.class);
                home.putExtra("parametros", parametros);
                startActivity(home);
            } else
                Toast.makeText(this, "Ops. Usuario ou senha inválida", Toast.LENGTH_SHORT).show();
        }
    }
}