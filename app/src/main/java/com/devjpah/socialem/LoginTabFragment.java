package com.devjpah.socialem;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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

    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    EditText etEmail, etPassword;
    TextView tvForget;
    Button btnIngresar;
    float v = 0;
    Dialog dialog;
    LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);
        conectar(root);

        dialog = new Dialog(getContext());

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
                if (!email.isEmpty() && !pass.isEmpty()) {
                    loadingDialog = new LoadingDialog(getActivity());
                    loadingDialog.startLoadingDialog();
                    login(email, pass);
                } else {
                    Toast.makeText(getContext(),"Debe ingresar email y contraseña", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private void login(String email, String password) {
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loadingDialog.dismissDialog();
                if (task.isSuccessful()){
                    startActivity(new Intent(getContext(),MainActivity.class));
                }
                else {
                    dialogNoSession();
                }
            }
        });
    }

    private void dialogNoSession() {
        Button btnOk;
        dialog.setContentView(R.layout.no_session);
        btnOk = dialog.findViewById(R.id.btn_ok);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    private void conectar(ViewGroup root) {
        etEmail = root.findViewById(R.id.et_email);
        etPassword = root.findViewById(R.id.et_password);
        tvForget = root.findViewById(R.id.tv_forgetpassword);
        btnIngresar = root.findViewById(R.id.btn_ingresar);
    }
}
