package com.streamliners.inputconstraints;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.streamliners.inputconstraints.databinding.ActivityInputConstraintsBinding;

public class InputConstraintsActivity extends AppCompatActivity {
    private static final int REQUEST_INPUT = 0;
    ActivityInputConstraintsBinding binding;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInputConstraintsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("InputConstraints Activity");

        binding.result.setVisibility(View.GONE);

        setupTextFieldErrorHide();
    }

    private void setupTextFieldErrorHide() {

        /**
         * To hide error when any of the check box selected
         */
        CompoundButton.OnCheckedChangeListener Listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.result.setVisibility(View.GONE);
            }
        };

        //link the listener to all check boxes
        binding.upperCaseAlphabetCheckBox.setOnCheckedChangeListener(Listener);
        binding.lowerCaseAlphabetCheckBox.setOnCheckedChangeListener(Listener);
        binding.digitsCheckBox.setOnCheckedChangeListener(Listener);
        binding.otherSymbolCheckBox.setOnCheckedChangeListener(Listener);
        binding.mathematicalOperationCheckBox.setOnCheckedChangeListener(Listener);
    }

    public void takeInput(View view) {
        binding.buttonInput.setOnClickListener(v -> {

            if(!(binding.upperCaseAlphabetCheckBox.isChecked() || binding.lowerCaseAlphabetCheckBox.isChecked() || binding.digitsCheckBox.isChecked() || binding.mathematicalOperationCheckBox.isChecked() || binding.otherSymbolCheckBox.isChecked())) {

                binding.result.setTextColor(getResources().getColor(R.color.red));
                binding.result.setVisibility(View.VISIBLE);
                binding.result.setText("Please select at least one constraints type.");
                return;
            }

            bundle = new Bundle();
            if (binding.upperCaseAlphabetCheckBox.isChecked()) {
                String constraints = "true";
                bundle.putString(Constants.UPPERCASE_ALPHABETS, constraints);
            }

            if (binding.lowerCaseAlphabetCheckBox.isChecked()) {
                String constraints = "true";
                bundle.putString(Constants.LOWERCASE_ALPHABETS, constraints);
            }

            if (binding.digitsCheckBox.isChecked()) {
                String constraints = "true";
                bundle.putString(Constants.DIGITS, constraints);
            }

            if (binding.mathematicalOperationCheckBox.isChecked()) {
                String constraints = "true";
                bundle.putString(Constants.MATHEMATICAL_OPERATORS, constraints);
            }

            if (binding.otherSymbolCheckBox.isChecked()) {
                String constraints = "true";
                bundle.putString(Constants.OTHER_SYMBOLS, constraints);
            }


            Intent intent = new Intent(InputConstraintsActivity.this, InputActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_INPUT);

        });
    }

      @Override
      protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            //check the result
            if(requestCode == REQUEST_INPUT && resultCode == RESULT_OK) {
                binding.result.setTextColor(Color.BLACK);
                binding.result.setText("Result is : " + data.getStringExtra(Constants.RESULT_STRING));
            }

         binding.result.setVisibility(View.VISIBLE);
    }
}