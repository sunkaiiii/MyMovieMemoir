package com.example.mymoviememoir.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RestfulPostModel;
import com.example.mymoviememoir.network.request.SignUpCredentialRequest;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class SignUpActivity extends BaseRequestRestulServiceActivity {
    /**
     * Use regex to make an mail validation
     * reference the regex string from
     * https://blog.mailtrap.io/java-email-validation/
     */
    private final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,7}$";

    private View btnSelectBirthday;
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    private Calendar m_birthday;

    private TextInputEditText eFirstName;
    private TextInputEditText eLastName;
    private TextView tvShowBirthday;
    private TextInputEditText ePostcode;
    private TextInputEditText eEmail;
    private TextInputEditText ePassword;
    private TextInputEditText eCheckedPassword;
    private RadioGroup genderGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
    }

    private void initViews() {
        eFirstName = findViewById(R.id.e_first_name);
        eLastName = findViewById(R.id.e_last_name);
        tvShowBirthday = findViewById(R.id.tv_show_birthday);
        ePostcode = findViewById(R.id.e_postcode);
        eEmail = findViewById(R.id.e_email);
        ePassword = findViewById(R.id.e_password);
        eCheckedPassword = findViewById(R.id.e_checked_password);
        btnSelectBirthday = findViewById(R.id.btn_select_birthday);
        genderGroup = findViewById(R.id.gender_group);
        btnSelectBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, dayOfMonth);
                        m_birthday = calendar;
                        TextView birthday = findViewById(R.id.tv_show_birthday);
                        birthday.setText(format.format(calendar.getTime()));
                        birthday.setVisibility(View.VISIBLE);
                    }
                }, 1990, 0, 1).show();
            }
        });

        findViewById(R.id.btn_sign_up).setOnClickListener(v -> {
            if (isInformationValid()) {
                tryToSignUp();
            }
        });
    }

    private void tryToSignUp() {
        final String password = ePassword.getText().toString();
        final String email = eEmail.getText().toString();
        final RestfulPostModel postModel = new SignUpCredentialRequest(email, password);
        requestRestfulService(MyMovieMemoirRestfulAPI.SIGN_UP_CREDENTIALS, postModel);
    }

    private boolean isInformationValid() {
        //TODO 邮箱验证不对
        if (!isValidEmail(eEmail.getText().toString())) {
            eEmail.setError("the email address is invalid");
            return false;
        }
        if (isValidBirthDay(m_birthday)) {
            TextView tvErrorBirthday = findViewById(R.id.tv_error_birthday_text);
            tvErrorBirthday.setVisibility(View.VISIBLE);
            tvErrorBirthday.setText("the birth day is invalid");
            return false;
        }
        if (!isValidPostCode(ePostcode.getText().toString())) {
            ePostcode.setError("the post code is invalid");
            return false;
        }
        return true;
    }

    private boolean isValidEmail(@Nullable String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        final Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidBirthDay(@Nullable Calendar birthday) {
        if (birthday == null) {
            return false;
        }
        return birthday.compareTo(Calendar.getInstance(Locale.getDefault())) > 0;
    }


    private boolean isValidPostCode(@Nullable String mState) {

        if (TextUtils.isEmpty(mState) || mState.length() != 4) {
            return false;
        }
        for (char c : mState.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private Gender getGenderEnumByRadioButton() {
        final Gender gender;
        switch (genderGroup.getCheckedRadioButtonId()) {
            case R.id.rb_male:
                gender = Gender.MALE;
                break;
            case R.id.rb_female:
                gender = Gender.FEMALE;
                break;
            default:
                gender = Gender.UNKNOWN;
                break;
        }
        return gender;
    }


    private static enum Gender {
        MALE("M"),
        FEMALE("F"),
        UNKNOWN("Unknown");
        String genderSymbol;

        private Gender(String s) {
            this.genderSymbol = s;
        }

        @NonNull
        @Override
        public String toString() {
            return genderSymbol;
        }

    }
}
