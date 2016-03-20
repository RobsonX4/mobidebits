package br.com.mobidebits.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;

import br.com.mobidebits.beans.Usuario;
import br.com.mobidebits.control.LoginControl;
import br.com.mobidebits.R;

/**
 * Created by Robson on 12/12/2015.
 */
public class LoginActivity extends Activity {

    private LoginControl loginControl;
    private EditText loginUsuarioEditText;
    private EditText loginSenhaEditText;
    private EditText nomeEditText;
    private EditText emailEditText;
    private EditText usuarioEditText;
    private EditText senhaEditText;

    private ViewFlipper viewFlipper;
    private float lastX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginControl = new LoginControl(getBaseContext());
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

        //Tela de Login
        loginUsuarioEditText = (EditText) findViewById(R.id.loginUsuarioEditText);
        loginSenhaEditText = (EditText) findViewById(R.id.loginSenhaEditText);

        //Tela de Cadastro
        nomeEditText    = (EditText) findViewById(R.id.nome);
        emailEditText   = (EditText) findViewById(R.id.email);
        usuarioEditText = (EditText) findViewById(R.id.usuario);
        senhaEditText   = (EditText) findViewById(R.id.senha);
    }

    //ViewFlipper
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float currentX = touchevent.getX();
                if (lastX < currentX) {
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;
                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_left);
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_right);
                    viewFlipper.showNext();
                }
                if (lastX > currentX) {
                    if (viewFlipper.getDisplayedChild() == 1)
                        break;
                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);
                    viewFlipper.showPrevious();
                }
                break;
        }
        return false;
    }

    public void novoCadastro(View view) {
        viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
        viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);
        viewFlipper.showPrevious();
    }

    public void entrar(View view) {
        Usuario usuario = new Usuario();
        usuario.setUsuario(loginUsuarioEditText.getText().toString());
        usuario.setSenha(loginSenhaEditText.getText().toString());

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

    public void cadastrar(View view) {
        Usuario usuario = new Usuario();
        usuario.setNome(nomeEditText.getText().toString());
        usuario.setEmail(emailEditText.getText().toString());
        usuario.setUsuario(usuarioEditText.getText().toString());
        usuario.setSenha(senhaEditText.getText().toString());
        usuario.setStatus(1);

        if (!loginControl.validateCadastro(usuario))
            Toast.makeText(this, "Preencha os campos para efetuar cadastro.", Toast.LENGTH_SHORT).show();
        else {
            if (loginControl.inserir(usuario)) {
                Toast.makeText(this, "Usuario cadastrado!", Toast.LENGTH_SHORT).show();
                viewFlipper.setInAnimation(this, R.anim.slide_in_from_left);
                viewFlipper.setOutAnimation(this, R.anim.slide_out_to_right);
                viewFlipper.showNext();
            } else
                Toast.makeText(this, "Ops. 'Usuario' ou 'Email' já cadastrado.", Toast.LENGTH_SHORT).show();
        }
    }
}