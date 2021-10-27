package com.devjpah.socialem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTabFragment extends Fragment {

    EditText etEmail, etPassword;
    TextView tvForget;
    Button btnIngresar;
    float v = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);
        conectar(root);

        etEmail.setTranslationX(800);
        etPassword.setTranslationX(800);
        tvForget.setTranslationX(800);
        btnIngresar.setTranslationX(800);

        etEmail.setAlpha(v);
        etPassword.setAlpha(v);
        tvForget.setAlpha(v);
        btnIngresar.setAlpha(v);

        etEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        etPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        tvForget.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        btnIngresar.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Forget Password", Toast.LENGTH_SHORT).show();
            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String pass = etPassword.getText().toString();
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), "Ingreso Exitoso", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), "Ha ocurrido un error al ingresar", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //Toast.makeText(getContext(), "Login", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void conectar(ViewGroup root) {
        etEmail = root.findViewById(R.id.et_email);
        etPassword = root.findViewById(R.id.et_password);
        tvForget = root.findViewById(R.id.tv_forgetpassword);
        btnIngresar = root.findViewById(R.id.btn_ingresar);
    }
}
