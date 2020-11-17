package com.vad.calk;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogNameSport extends DialogFragment {
    Datable datable;
    EditText nameSport;
    Button btnOk;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        datable = (Datable) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.name_sport,container, false);
        nameSport = (EditText) view.findViewById(R.id.name_sport_type);
        btnOk = (Button) view.findViewById(R.id.btn_ok);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datable.nameSport(nameSport.getText().toString());
                getDialog().dismiss();
            }
        });
        return view;

    }
}
