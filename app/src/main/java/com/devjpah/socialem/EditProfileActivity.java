package com.devjpah.socialem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


import java.util.Calendar;


import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    DatabaseReference fDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser;
    CircleImageView imgProfile;
    TextView tvUsername;
    TextInputEditText etName, etLastName, etBirthday, etProfession, etJob, etLocation;
    TextInputLayout tlName, tlLastName, tlProfession, tlJob, tlBirthday;
    Button btnEdit;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        connectXml();





        if (this.getIntent().getExtras() != null){
            Bundle bundle = this.getIntent().getExtras();
            username = bundle.get("Username").toString();
            email= bundle.get("Email").toString();
            rol = bundle.get("Rol").toString();
            tvUsername.setText(username);

        currentUser = fAuth.getCurrentUser();
        loadingDialog = new LoadingDialog(EditProfileActivity.this);
        if(currentUser != null) {
            loadingDialog.startLoadingDialog();
            getUsernameAndSex();

        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String lastName = etLastName.getText().toString();
                String profession = etProfession.getText().toString();
                String job = etJob.getText().toString();

                if (name.isEmpty()) {
                    tlName.setError("El campo esta vacio");
                }
                if (lastName.isEmpty()) {
                    tlLastName.setError("El campo esta vacio");
                }
                if (profession.isEmpty()) {
                    tlProfession.setError("El campo esta vacio");
                }
                if (job.isEmpty()) {
                    tlJob.setError("El campo esta vacio");
                }
                if(!name.isEmpty() && !lastName.isEmpty() && !profession.isEmpty() && !job.isEmpty()) {
                    addInfo();
                }

                String Name = etName.getText().toString();
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

        etLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tlLastName.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
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
    }

    private void addInfo() {
        HashMap<String,Object> map = new HashMap<String,Object>();
        String name = etName.getText().toString();
        String lastname = etLastName.getText().toString();
        String fenac = etBirthday.getText().toString();
        String profesion = etProfession.getText().toString();
        String cargo = etJob.getText().toString();
        String localidad = etLocation.getText().toString();
        map.put("firstName", name);
        map.put("lastName", lastname);
        map.put("birthday", fenac);
        map.put("profession", profesion);
        map.put("job", cargo);
        map.put("location", localidad);
        fDatabase.child("Users").child(currentUser.getUid()).child("profile").setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(EditProfileActivity.this, MainActivity.class));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfileActivity.this, "Ha ocurrido un error inesperado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUsernameAndSex() {
        fDatabase.child("Users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String username = snapshot.child("username").getValue().toString();
                    String sex = snapshot.child("sex").getValue().toString();
                    int resource = (sex.equals("Hombre")) ? R.drawable.avatar_man : R.drawable.avatar_woman;
                    imgProfile.setImageResource(resource);
                    tvUsername.setText(username);
                }
                loadingDialog.dismissDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendario = Calendar.getInstance();
        int dd = calendario.get(Calendar.DAY_OF_MONTH);
        int mm = calendario.get(Calendar.MONTH);
        int yy = calendario.get(Calendar.YEAR);

        DatePickerDialog datePicker = new DatePickerDialog(EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int dayOfMonth, int monthOfYear, int year) {

                String fecha = twoDigits(year) + "/" + twoDigits(monthOfYear + 1)
                        + "/" + twoDigits(dayOfMonth);
                etBirthday.setText(fecha);
            }
        }, yy, mm, dd);

        datePicker.show();
    }

    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }

    private void connectXml() {
        imgProfile = findViewById(R.id.img_profile_edit);
        tvUsername = findViewById(R.id.tv_username_edit);
        etName = findViewById(R.id.et_name);
        etLastName = findViewById(R.id.et_lastname);
        etBirthday = findViewById(R.id.et_birthday);
        etProfession = findViewById(R.id.et_profession);
        etJob = findViewById(R.id.et_job);
        etLocation = findViewById(R.id.et_location);
        tlName = findViewById(R.id.tl_name);
        tlLastName = findViewById(R.id.tl_lastname);
        tlBirthday = findViewById(R.id.tl_birthday);
        tlProfession = findViewById(R.id.tl_profession);
        tlJob = findViewById(R.id.tl_job);
        btnEdit = findViewById(R.id.btn_edit_profile);
    }
}