package com.example.lxhstudy.item;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lxhstudy.R;
import com.example.lxhstudy.view.Hencoder_MaterialEdiText;

public class MaterialEditTextActivity extends AppCompatActivity {

    private Hencoder_MaterialEdiText edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_edit_text);
        edittext = (Hencoder_MaterialEdiText) findViewById(R.id.edittext);

    }
}