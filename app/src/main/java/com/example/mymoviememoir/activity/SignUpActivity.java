package com.example.mymoviememoir.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RequestHelper;
import com.example.mymoviememoir.network.RestfulGetModel;
import com.example.mymoviememoir.network.RestfulPostModel;
import com.example.mymoviememoir.network.request.SignUpCredentialRequest;
import com.example.mymoviememoir.network.request.SignUpPersonRequest;
import com.example.mymoviememoir.utils.CredentialInfoUtils;
import com.example.mymoviememoir.utils.GsonUtils;
import com.example.mymoviememoir.utils.PersonInfoUtils;
import com.example.mymoviememoir.utils.Values;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class SignUpActivity extends BaseRequestRestfulServiceActivity {
    /**
     * Use regex to make an mail validation
     * reference the regex string from
     * http://regexlib.com/Search.aspx?k=email&AspxAutoDetectCookieSupport=1
     */
    private final String REGEX = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";

    private View btnSelectBirthday;

    private Calendar m_birthday;

    private TextInputEditText eFirstName;
    private TextInputEditText eLastName;
    private TextView tvShowBirthday;
    private TextInputEditText ePostcode;
    private TextInputEditText eEmail;
    private TextInputEditText ePassword;
    private TextInputEditText eCheckedPassword;
    private RadioGroup genderGroup;
    private Spinner spState;


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
        spState = findViewById(R.id.sp_state);
        btnSelectBirthday.setOnClickListener(v -> new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                m_birthday = calendar;
                TextView birthday = findViewById(R.id.tv_show_birthday);
                birthday.setText(Values.SIMPLE_DATE_FORMAT.format(calendar.getTime()));
                birthday.setVisibility(View.VISIBLE);
                if (!isValidBirthDay(m_birthday)) {
                    findViewById(R.id.tv_error_birthday_text).setVisibility(View.VISIBLE);
                }
            }
        }, 1990, 0, 1).show());

        findViewById(R.id.btn_sign_up).setOnClickListener(v -> {
            if (isInformationValid()) {
                requestCheckUserEmailExisting();
            }
        });
    }

    private void requestCheckUserEmailExisting(){
        requestRestfulService(MyMovieMemoirRestfulAPI.CHECK_USER_NAME, new RestfulGetModel() {
            @Override
            public List<String> getPathParameter() {
                return Collections.singletonList(eEmail.getText().toString());
            }
        });
    }

    private void tryToSignUp() {
        final String password = ePassword.getText().toString();
        final String email = eEmail.getText().toString();
        final RestfulPostModel postModel = new SignUpCredentialRequest(email, password);
        requestRestfulService(MyMovieMemoirRestfulAPI.SIGN_UP_CREDENTIALS, postModel);
    }


    private void tryToSignPerson(int id, SignUpCredentialRequest request) {
        SignUpPersonRequest personRequest = new SignUpPersonRequest();
        personRequest.setCredentialsId(new SignUpPersonRequest.CredentialsId(id, request.getUsername(), request.getPassword()));
        personRequest.setDob(Values.REQUESTING_FORMAT.format(m_birthday.getTime()));
        personRequest.setFname(eFirstName.getText().toString());
        personRequest.setState(spState.getSelectedItem().toString());
        personRequest.setPostcode(ePostcode.getText().toString());
        personRequest.setSurname(eLastName.getText().toString());
        personRequest.setGender(getGenderEnumByRadioButton().genderSymbol);
        requestRestfulService(MyMovieMemoirRestfulAPI.SIGN_UP_PERSON, personRequest);
    }

    @Override
    public void onPostExecute(RequestHelper helper, String response) {
        super.onPostExecute(helper, response);
        switch (helper.getRestfulAPI()) {
            case CHECK_USER_NAME:
                List<SignUpCredentialRequest> emailList = GsonUtils.fromJsonToList(response, SignUpCredentialRequest.class);
                if(emailList==null || emailList.isEmpty()){
                    tryToSignUp();
                }else{
                    eEmail.setError("The email address has existed, please select another one");
                }
                break;
            case SIGN_UP_CREDENTIALS:
                try {
                    int id = Integer.parseInt(response);
                    SignUpCredentialRequest request = (SignUpCredentialRequest) helper.getBodyRequestModel();
                    tryToSignPerson(id, request);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "unknown error", Toast.LENGTH_SHORT).show();
                }
                break;
            case SIGN_UP_PERSON:
                try {
                    Toast.makeText(this, "sign up successful", Toast.LENGTH_SHORT).show();
                    SignUpPersonRequest personRequest = (SignUpPersonRequest) helper.getBodyRequestModel();
                    getSharedPreferences(Values.USER_INFO, MODE_PRIVATE).edit().putString(Values.PERSON, personRequest.getBodyParameterJson()).apply();
                    PersonInfoUtils.setInstance(personRequest);
                    CredentialInfoUtils.setInstance(personRequest.getCredentialsId());
                    setResult(Values.SUCCESS);
                    finish();
                } catch (RequestHelper.NoSuchTypeOfModelException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }


    private boolean isInformationValid() {
        boolean success = true;
        //TODO 错误信息显示不全
        if (!isValidEmail(eEmail.getText().toString())) {
            eEmail.setError("the email address is invalid");
            success = false;
        }
        if (!isValidPostCode(ePostcode.getText().toString())) {
            ePostcode.setError("the post code is invalid");
            success = false;
        }
        if (TextUtils.isEmpty(ePassword.getText())) {
            ePassword.setError("the password cannot be empty");
            success = false;
        }
        if (TextUtils.isEmpty(eCheckedPassword.getText())) {
            eCheckedPassword.setError("the password cannot be empty");
            success = false;
        }
        if (!TextUtils.equals(eCheckedPassword.getText(), ePassword.getText()) && !TextUtils.isEmpty(ePassword.getText()) && !TextUtils.isEmpty(eCheckedPassword.getText())) {
            ePassword.setError("the password and checked password are not matched");
            eCheckedPassword.setError("the password and checked password are not matched");
            success = false;
        }
        if (getGenderEnumByRadioButton() == Gender.UNKNOWN) {
            Toast.makeText(this, "please select a gender", Toast.LENGTH_SHORT).show();
            success = false;
        }
        if (m_birthday == null) {
            Toast.makeText(this, "please select your birthday", Toast.LENGTH_SHORT).show();
            success = false;
        }
        return success;
    }

    private boolean isValidEmail(@Nullable String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        final Pattern pattern = Pattern.compile(REGEX);
        return pattern.matcher(email).matches();
    }

    private boolean isValidBirthDay(@Nullable Calendar birthday) {
        if (birthday == null) {
            return false;
        }
        return birthday.compareTo(Calendar.getInstance(Locale.getDefault())) < 0;
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


    private enum Gender {
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
