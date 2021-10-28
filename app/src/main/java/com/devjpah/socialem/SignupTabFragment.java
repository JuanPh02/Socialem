package com.devjpah.socialem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupTabFragment extends Fragment {

    FirebaseFirestore bd = FirebaseFirestore.getInstance();
    HashMap<String,Object> map = new HashMap<String,Object>();
    EditText etEmail, etPassword, etUsername;
    RadioGroup rgRol;
    RadioButton radioButton;
    CheckBox cbPolitica;
    Button btnSignup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);
        conectar(root);

        rgRol.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioId = rgRol.getCheckedRadioButtonId();
                radioButton = root.findViewById(radioId);
                //Toast.makeText(getContext(), "Selected: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        cbPolitica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String msg = isChecked ? "Checked" : "Not Checked";
                //Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String pass = etPassword.getText().toString();
                String user = etUsername.getText().toString();
                String rol = radioButton.getText().toString();
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), "El usuario ha sido creado", Toast.LENGTH_SHORT).show();
                            cargarPantalla(email, user, rol);
                        }
                        else {
                            Toast.makeText(getContext(), "Ha ocurrido un error al crear el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return root;
    }

    private void cargarPantalla(String email, String user, String rol){
        Intent cargar =  new Intent(getContext(), EditProfileActivity.class);
        cargar.putExtra("Email", email);
        cargar.putExtra("Username", user);
        cargar.putExtra("Rol", rol);
        startActivity(cargar);
    }

    private void conectar(ViewGroup root) {
        etEmail = root.findViewById(R.id.et_email);
        etPassword = root.findViewById(R.id.et_password);
        rgRol = root.findViewById(R.id.rg_rol);
        cbPolitica = root.findViewById(R.id.cb_politica);
        btnSignup = root.findViewById(R.id.btn_signup);
        etUsername = root.findViewById(R.id.et_user);
    }
}
