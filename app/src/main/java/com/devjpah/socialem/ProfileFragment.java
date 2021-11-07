package com.devjpah.socialem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    DatabaseReference fDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser;
    TextView tvTitle, tvCountFollows, tvCountStars, tvName, tvProfession, tvJob, tvLocation;
    CircleImageView imgProfile;
    ImageButton imgbFollow, imgbStar;
    Button btnEdit, btnSignout;
    RecyclerView publicationsProfile;
    List<Publication> publications = new ArrayList<Publication>();
    LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        connectXml(view);
        currentUser = fAuth.getCurrentUser();
        loadingDialog = new LoadingDialog(getActivity());
        if(currentUser != null) {
            loadingDialog.startLoadingDialog();
            getUsername();
            getDataCounters();
            getDataProfile();
        }
        initPublications();

        //Events
        imgbFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Follow", Toast.LENGTH_SHORT).show();
            }
        });

        imgbStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Star", Toast.LENGTH_SHORT).show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),EditProfileActivity2.class));
            }
        });

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder
                        .setTitle("Cerrar Sesión")
                        .setMessage("¿Está seguro que desea cerrar sesión?")
                        .setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                signOut();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });
        return view;
    }

    private void initPublications() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("eli@h.com", "Que juiciioooooo"));
        //Request Options
        publications.add(new Publication("Juliana De la rosa", "20211020", "media\\fddfs.png", "Trabajando ando", 34, comments));
        publications.add(new Publication("Juliana De la rosa", "20211020", "media\\fddfs.png", "Trabajando ando", 34, comments));
        publications.add(new Publication("Juliana De la rosa", "20211020", "media\\fddfs.png", "Trabajando ando", 34, comments));
        publications.add(new Publication("Juliana De la rosa", "20211020", "media\\fddfs.png", "Trabajando ando", 34, comments));
        publications.add(new Publication("Juliana De la rosa", "20211020", "media\\fddfs.png", "Trabajando ando Trabajando ando OEEE Trabajando ando Trabajando ando Trabajando ando Trabajando ando Trabajando ando Trabajando ando Trabajando ando Trabajando ando", 34, comments));
        //Create feed
        PublicationsAdapter adapter = new PublicationsAdapter(publications, getContext());
        publicationsProfile.setAdapter(adapter);
        publicationsProfile.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    private void getUsername() {
        fDatabase.child("Users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String title = "Perfil de " + snapshot.child("username").getValue().toString();
                    tvTitle.setText(title);
                    String sex = snapshot.child("sex").getValue().toString();
                    int resource = (sex.equals("Hombre")) ? R.drawable.avatar_man : R.drawable.avatar_woman;
                    imgProfile.setImageResource(resource);
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
                    tvCountFollows.setText(follows);
                    tvCountStars.setText(stars);
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

                    String fullName = firstName + " " + lastName;

                    tvName.setText(fullName);
                    tvProfession.setText(profession);
                    tvJob.setText(job);
                    tvLocation.setText(location);

                }
                loadingDialog.dismissDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void signOut() {
        LoadingDialog loadingDialog = new LoadingDialog(getActivity(), "Cerrando Sesion...");
        loadingDialog.startLoadingDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fAuth.signOut();
                loadingDialog.dismissDialog();
                startActivity(new Intent(getActivity(),LoginActivity.class));
                getActivity().finish();
            }
        }, 2000);
    }

    private void connectXml(View view) {
        tvTitle = view.findViewById(R.id.tv_title);
        tvCountFollows = view.findViewById(R.id.tv_count_follows);
        tvCountStars = view.findViewById(R.id.tv_count_stars);
        tvName = view.findViewById(R.id.tv_name);
        tvProfession = view.findViewById(R.id.tv_profession);
        tvJob = view.findViewById(R.id.tv_job);
        tvLocation = view.findViewById(R.id.tv_location);
        imgProfile = view.findViewById(R.id.img_profile_edit);
        imgbFollow = view.findViewById(R.id.imgb_follow);
        imgbStar = view.findViewById(R.id.imgb_star);
        btnEdit = view.findViewById(R.id.btn_edit);
        btnSignout = view.findViewById(R.id.btn_signout);
        publicationsProfile = view.findViewById(R.id.publications_profile);
    }
}