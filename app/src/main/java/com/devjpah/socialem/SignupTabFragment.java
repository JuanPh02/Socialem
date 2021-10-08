package com.devjpah.socialem;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SignupTabFragment extends Fragment {

    EditText etEmail, etPassword;
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
                Toast.makeText(getContext(), "Selected: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        cbPolitica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String msg = isChecked ? "Checked" : "Not Checked";
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Signup", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void conectar(ViewGroup root) {
        etEmail = root.findViewById(R.id.et_email);
        etPassword = root.findViewById(R.id.et_password);
        rgRol = root.findViewById(R.id.rg_rol);
        cbPolitica = root.findViewById(R.id.cb_politica);
        btnSignup = root.findViewById(R.id.btn_signup);
    }
}
