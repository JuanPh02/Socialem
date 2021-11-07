package com.devjpah.socialem;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
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

import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity2 extends AppCompatActivity {

    DatabaseReference fDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser;
    CircleImageView imgProfile;
    TextView tvUsername, tvFollows, tvStars;
    TextInputEditText etName, etLastName, etBirthday, etProfession, etJob, etLocation;
    TextInputLayout tlName, tlLastName, tlProfession, tlJob, tlBirthday;
    Button btnEdit;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile2);
        connectXml();
        currentUser = fAuth.getCurrentUser();
        loadingDialog = new LoadingDialog(EditProfileActivity2.this);
        if(currentUser != null) {
            loadingDialog.startLoadingDialog();
            getUsername();
            getDataCounters();
            getDataProfile();
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
                    updateInfo();
                }
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

    private void updateInfo() {
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
        fDatabase.child("Users").child(currentUser.getUid()).child("profile").updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    onBackPressed();
                    finish();
                }
            }
        });
    }

    private void getUsername() {
        fDatabase.child("Users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String username = snapshot.child("username").getValue().toString();
                    tvUsername.setText(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDataCounters() {
        fDatabase.child("Users").child(currentUser.getUid()).child("counters").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String follows = snapshot.child("follows").getValue().toString();
                    String stars = snapshot.child("stars").getValue().toString();
                    tvFollows.setText(follows);
                    tvStars.setText(stars);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDataProfile() {
        fDatabase.child("Users").child(currentUser.getUid()).child("profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String birthday = snapshot.child("birthday").exists() ? snapshot.child("birthday").getValue().toString() : "";
                    String firstName = snapshot.child("firstName").exists() ? snapshot.child("firstName").getValue().toString() : "";
                    String job = snapshot.child("job").exists() ? snapshot.child("job").getValue().toString() : "";
                    String lastName = snapshot.child("lastName").exists() ? snapshot.child("lastName").getValue().toString() : "";
                    String location = snapshot.child("location").exists() ? snapshot.child("location").getValue().toString() : "";
                    String profession = snapshot.child("profession").exists() ? snapshot.child("profession").getValue().toString() : "";

                    etBirthday.setText(birthday);
                    etName.setText(firstName);
                    etJob.setText(job);
                    etLastName.setText(lastName);
                    etLocation.setText(location);
                    etProfession.setText(profession);
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

        DatePickerDialog datePicker = new DatePickerDialog(EditProfileActivity2.this, new DatePickerDialog.OnDateSetListener() {
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
        imgProfile = findViewById(R.id.img_profile_edit2);
        tvUsername = findViewById(R.id.tv_username_ep);
        tvFollows = findViewById(R.id.tv_follows_ep);
        tvStars = findViewById(R.id.tv_stars_ep);
        etName = findViewById(R.id.et_name_ep);
        etLastName = findViewById(R.id.et_lastname_ep);
        etBirthday = findViewById(R.id.et_birthday_ep);
        etProfession = findViewById(R.id.et_profession_ep);
        etJob = findViewById(R.id.et_job_ep);
        etLocation = findViewById(R.id.et_location_ep);
        tlName = findViewById(R.id.tl_name_ep);
        tlLastName = findViewById(R.id.tl_lastname_ep);
        tlBirthday = findViewById(R.id.tl_birthday_ep);
        tlProfession = findViewById(R.id.tl_profession_ep);
        tlJob = findViewById(R.id.tl_job_ep);
        btnEdit = findViewById(R.id.btn_edit_profile_ep);
    }
}