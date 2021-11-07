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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupTabFragment extends Fragment {

    DatabaseReference fDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    EditText etEmail, etPassword, etUsername;
    RadioGroup rgSex, rgRol;
    RadioButton rbSex, rbRol;
    CheckBox cbPolitica;
    Boolean acceptPolicy;
    Button btnSignup;
    LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);
        conectar(root);

        rgRol.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioId = rgRol.getCheckedRadioButtonId();
                rbRol = root.findViewById(radioId);
            }
        });

        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioId = rgSex.getCheckedRadioButtonId();
                rbSex = root.findViewById(radioId);
            }
        });

        cbPolitica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                acceptPolicy = isChecked;
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String user = etUsername.getText().toString();
                    String email = etEmail.getText().toString();
                    String password = etPassword.getText().toString();
                    String rol = rbRol.getText().toString();
                    String sex = rbSex.getText().toString();
                    if(!user.isEmpty() && !email.isEmpty() && !password.isEmpty() && !rol.isEmpty() && !sex.isEmpty() && acceptPolicy) {
                        if (password.length() >= 6 ) {
                            loadingDialog = new LoadingDialog(getActivity());
                            loadingDialog.startLoadingDialog();
                            register(user, email, password, rol, sex);
                        } else {
                            Toast.makeText(getContext(), "La contraseña debe tener minimo 6 caracteres", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Llenar todos los campos y aceptar politica", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception exception) {
                    Toast.makeText(getContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }

    private void register(String user, String email, String password, String rol, String sex) {
        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser currentUser = fAuth.getCurrentUser();

                if (task.isSuccessful() && currentUser != null){
                    HashMap<String,Object> mapUser = new HashMap<String,Object>();
                    HashMap<String,Object> mapCounters = new HashMap<String,Object>();
                    mapUser.put("username",user);
                    mapUser.put("email",email);
                    mapUser.put("password",password);
                    mapUser.put("rol",rol);
                    mapUser.put("sex",sex);
                    mapCounters.put("follows", 0);
                    mapCounters.put("stars", 0);
                    fDatabase.child("Users").child(currentUser.getUid()).child("counters").setValue(mapCounters);
                    fDatabase.child("Users").child(currentUser.getUid()).updateChildren(mapUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            loadingDialog.dismissDialog();
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getContext(), EditProfileActivity.class));
                                getActivity().finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Error al registar el usuario", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void conectar(ViewGroup root) {
        etUsername = root.findViewById(R.id.et_user);
        etEmail = root.findViewById(R.id.et_email);
        etPassword = root.findViewById(R.id.et_password);
        rgRol = root.findViewById(R.id.rg_rol);
        rgSex = root.findViewById(R.id.rg_sex);
        cbPolitica = root.findViewById(R.id.cb_politica);
        btnSignup = root.findViewById(R.id.btn_signup);
    }
}
