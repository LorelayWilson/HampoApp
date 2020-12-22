package org.example.lf52.hampo.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.example.lf52.hampo.R;

public class HomeFragment extends Fragment {
    private TextView textView;
    private HomeViewModel homeViewModel;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textView = root.findViewById(R.id.text_home);
        if (user!=null){
            textView.setText(user.getUid().toString());
        }else {
            Log.d("Coso","No logeado");
        }
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (user!=null){
            textView.setText(user.getUid().toString());
        }else {
            Log.d("Coso","No logeado");
        }
    }
}