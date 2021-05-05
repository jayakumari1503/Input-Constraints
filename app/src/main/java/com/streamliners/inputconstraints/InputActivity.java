package com.streamliners.inputconstraints;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.streamliners.inputconstraints.databinding.ActiviyInputBinding;

public class InputActivity extends AppCompatActivity {
    ActiviyInputBinding binding;

    private StringBuilder regex = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActiviyInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("Input Activity");

        setupHideErrorForEditText();

        checkConstraints();
    }

    /**
     * to hide error of text field when text changes
     */
    private void setupHideErrorForEditText() {
        binding.textField.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    binding.textField.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private String checkConstraints() {
        Bundle bundle = getIntent().getExtras();
        regex.append("^[");
        if(Boolean.parseBoolean(bundle.getString(Constants.UPPERCASE_ALPHABETS,"false"))) {
            regex.append("A-Z");
        }

        if(Boolean.parseBoolean(bundle.getString(Constants.LOWERCASE_ALPHABETS,"false"))) {
            regex.append("a-z");
        }

        if(Boolean.parseBoolean(bundle.getString(Constants.DIGITS,"false"))) {
                regex.append("0-9");
        }

        if(Boolean.parseBoolean(bundle.getString(Constants.MATHEMATICAL_OPERATORS,"false"))) {
            regex.append("+-/*%");
        }

        if(Boolean.parseBoolean(bundle.getString(Constants.OTHER_SYMBOLS,"false"))) {
            regex.append("@#$&!");
        }

        regex.append("]*");

        return regex.toString();
    }

    public void sendInput(View view) {
                String input = binding.textField.getEditText().getText().toString().trim();

                //check input is not empty
                if(input.isEmpty()) {
                    binding.textField.setError("Please give input");
                    return;
                }

                //matching input with regex generated
                else if (!input.matches(checkConstraints())) {
                    binding.textField.setError("Enter valid input!!");
                    return;
                }
                //sending the result back to the activity
                Intent intent = new Intent(InputActivity.this, InputConstraintsActivity.class);
                intent.putExtra(Constants.RESULT_STRING, input);

                setResult(RESULT_OK, intent);

                finish();
            }
}


