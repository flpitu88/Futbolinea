package com.flpitu88.futbolinea.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.flpitu88.futbolinea.R;
import com.flpitu88.futbolinea.adapters.GrupoAdapter;
import com.flpitu88.futbolinea.model.Grupo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GruposActivity extends AppCompatActivity {

    private List<Grupo> listaGrupos;
    private RecyclerView recyclerView;

    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);

        databaseRef = FirebaseDatabase.getInstance().getReference("usuarios").child(FirebaseAuth.getInstance().getUid());

        recyclerView = findViewById(R.id.listaGrupos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaGrupos = new ArrayList<>();

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List grupos = (dataSnapshot.child("grupos").getValue() != null) ? (List) dataSnapshot.child("grupos").getValue() : Collections.EMPTY_LIST;

                final DatabaseReference databaseRefToGrupos = FirebaseDatabase.getInstance().getReference("grupos");

                for (final Object grupoDelUsuario : grupos) {
                    final String grupoString = (String) grupoDelUsuario;
                    databaseRefToGrupos.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Map grupo = (dataSnapshot.child(grupoString) != null) ? (Map) dataSnapshot.child(grupoString).getValue() : null;
                            if (grupo != null) {
                                List<String> jugadores = (List<String>) grupo.get("jugadores");
                                String titulo = (String) grupo.get("titulo");
                                Grupo grupoObject = new Grupo(titulo,jugadores);
                                listaGrupos.add(grupoObject);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                GrupoAdapter adapter = new GrupoAdapter(GruposActivity.this, listaGrupos);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
