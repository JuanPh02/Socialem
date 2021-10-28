package com.devjpah.socialem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    FirebaseFirestore bd = FirebaseFirestore.getInstance();
    HashMap<String,Object> map = new HashMap<String,Object>();
    CircleImageView imgProfile;
    TextView tvUsername;
    TextInputEditText etName, etLastname, etBirthday, etProfession, etJob, etLocation;
    TextInputLayout tlName, tlProfession, tlJob;
    Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        connectXml();
        Bundle bundle = this.getIntent().getExtras();
        String username = bundle.get("Username").toString();
        String email= bundle.get("Email").toString();
        String rol = bundle.get("Rol").toString();
        if (bundle!=null){
            tvUsername.setText(username);
        }




        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().isEmpty()) {
                    tlName.setError("El campo esta vacio");
                }
                if (etProfession.getText().toString().isEmpty()) {
                    tlProfession.setError("El campo esta vacio");
                }
                if (etJob.getText().toString().isEmpty()) {
                    tlJob.setError("El campo esta vacio");
                } else {
                    Toast.makeText(getApplicationContext(), etName.getText().toString() + " " + etLastname.getText().toString(),Toast.LENGTH_LONG).show();
                }

                String name = etName.getText().toString();
                String lastname = etLastname.getText().toString();
                String fenac = etBirthday.getText().toString();
                String profesion = etProfession.getText().toString();
                String cargo = etJob.getText().toString();
                String localidad = etLocation.getText().toString();
                map.put("Username", username);
                map.put("Email", email);
                map.put("Rol",rol);
                map.put("Name", name);
                map.put("Lastname", lastname);
                map.put("FechaNacimiento", fenac);
                map.put("Profession", profesion);
                map.put("Job", cargo);
                map.put("Location", localidad);
                bd.collection("Users").add(map);





            }
        });

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tlName.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        etProfession.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tlProfession.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        etJob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tlJob.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void connectXml() {
        imgProfile = findViewById(R.id.img_profile_edit);
        tvUsername = findViewById(R.id.tv_username_edit);
        etName = findViewById(R.id.et_name);
        etLastname = findViewById(R.id.et_lastname);
        etBirthday = findViewById(R.id.et_birthday);
        etProfession = findViewById(R.id.et_profession);
        etJob = findViewById(R.id.et_job);
        etLocation = findViewById(R.id.et_location);
        tlName = findViewById(R.id.tl_name);
        tlProfession = findViewById(R.id.tl_profession);
        tlJob = findViewById(R.id.tl_job);
        btnEdit = findViewById(R.id.btn_edit_profile);
    }
}