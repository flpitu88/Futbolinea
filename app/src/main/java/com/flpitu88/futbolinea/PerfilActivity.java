package com.flpitu88.futbolinea;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.flpitu88.futbolinea.model.Jugador;
import com.flpitu88.futbolinea.ui.dialog.DatePickerFragment;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PerfilActivity extends AppCompatActivity {

    private static final String TAG = "PerfilActivity";

    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseAuth auth;
    private StorageReference mStorageRef;
    private DatabaseReference databaseRef;
    private Button buttonGuardar;
    private Button elegirFoto;
    private EditText nombre;
    private EditText apellido;
    private EditText apodo;
    private EditText celular;
    private Uri pathImagenPerfil;
    private ImageView imagenPerfil;
    private ProgressBar mProgressBar;
    private EditText etPlannedDate;
    private FirebaseUser user;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        TextView correo = findViewById(R.id.correo);
        correo.setText(user.getEmail());

        mStorageRef = FirebaseStorage.getInstance().getReference("imagenes_perfil");
        databaseRef = FirebaseDatabase.getInstance().getReference("usuarios");

        buttonGuardar = findViewById(R.id.guardarPerfil);
        elegirFoto = findViewById(R.id.botonElegirPath);
        imagenPerfil = findViewById(R.id.imagen_perfil);
        mProgressBar = findViewById(R.id.progressBar);
        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        apodo = findViewById(R.id.apodo);
        celular = findViewById(R.id.celular);
        etPlannedDate = (EditText) findViewById(R.id.etPlannedDate);

        elegirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String nombreDB = dataSnapshot.child(user.getUid()).child("nombre").getValue() != null ? dataSnapshot.child(user.getUid()).child("nombre").getValue().toString() : "";
                String apellidoDB = dataSnapshot.child(user.getUid()).child("apellido").getValue() != null ? dataSnapshot.child(user.getUid()).child("apellido").getValue().toString() : "";
                String apodoDB = dataSnapshot.child(user.getUid()).child("apodo").getValue() != null ? dataSnapshot.child(user.getUid()).child("apodo").getValue().toString() : "";
                String celularDB = dataSnapshot.child(user.getUid()).child("celular").getValue() != null ? dataSnapshot.child(user.getUid()).child("celular").getValue().toString() : "";
                String fechaNacimientoDB = dataSnapshot.child(user.getUid()).child("fechaNacimiento").getValue() != null ? dataSnapshot.child(user.getUid()).child("fechaNacimiento").getValue().toString() : "";
                String imagenPerfilDB = dataSnapshot.child(user.getUid()).child("imagenPerfil").getValue() != null ? dataSnapshot.child(user.getUid()).child("imagenPerfil").getValue().toString() : null;
                nombre.setText(nombreDB);
                apellido.setText(apellidoDB);
                apodo.setText(apodoDB);
                celular.setText(celularDB);
                etPlannedDate.setText(fechaNacimientoDB);

                if (imagenPerfilDB != null) {
                    StorageReference imageRef = mStorageRef.child(imagenPerfilDB);
                    File localFile = null;
                    try {
                        localFile = File.createTempFile("images", "jpg");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    final File finalLocalFile = localFile;
                    imageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Picasso.get().load(finalLocalFile).into(imagenPerfil);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(PerfilActivity.this,"Upload in progress",Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        etPlannedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.etPlannedDate:
                        showDatePickerDialog();
                        break;
                }
            }
        });

    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                etPlannedDate.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            pathImagenPerfil = data.getData();
            Picasso.get().load(pathImagenPerfil).into(imagenPerfil);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(){
        if (pathImagenPerfil != null){
            final String imagenFileName = user.getUid() + "." + getFileExtension(pathImagenPerfil);
            StorageReference fileReference = mStorageRef.child(imagenFileName);
            fileReference.putFile(pathImagenPerfil)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable(){
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 5000);

                            Toast.makeText(PerfilActivity.this,"Upload succesful",Toast.LENGTH_LONG).show();
                            Upload upload = new Upload(user.getUid(),
                                    taskSnapshot.getStorage().getDownloadUrl().toString());
//                            String uploadId = databaseRef.push().getKey();
                            Jugador jugador = new Jugador(
                                    user.getUid(),nombre.getText().toString(),
                                    apellido.getText().toString(),
                                    apodo.getText().toString(),
                                    etPlannedDate.getText().toString(),
                                    celular.getText().toString());
                            jugador.setImagenPerfil(imagenFileName);
                            databaseRef.child(jugador.getId()).setValue(jugador);
                            startActivity(new Intent(PerfilActivity.this,MainActivity.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PerfilActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int)progress);
                        }
                    });
        } else {
            Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
        }
    }
}
