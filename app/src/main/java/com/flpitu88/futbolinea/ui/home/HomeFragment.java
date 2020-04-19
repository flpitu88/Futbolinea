package com.flpitu88.futbolinea.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.flpitu88.futbolinea.ui.PerfilActivity;
import com.flpitu88.futbolinea.R;
import com.flpitu88.futbolinea.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Button logoutButton = root.findViewById(R.id.botonSalir);
        ImageView imagenPerfilButton = root.findViewById(R.id.imagenPerfil);
        ImageView imagenGruposButton = root.findViewById(R.id.imagenGrupo);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });

        imagenPerfilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(getActivity(), PerfilActivity.class),
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                getActivity(),
                                getActivity().findViewById(R.id.imagenPerfil),
                                "transitionPerfil")
                                .toBundle());
            }
        });

        imagenGruposButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(getActivity(), GruposActivity.class),
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                getActivity(),
                                getActivity().findViewById(R.id.imagenGrupo),
                                "transitionGrupos")
                                .toBundle());
            }
        });

        return root;
    }
}
