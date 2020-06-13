package com.example.mymoviememoir.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.entities.Credentials;
import com.example.mymoviememoir.entities.Person;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RequestHelper;
import com.example.mymoviememoir.network.interfaces.RestfulGetModel;
import com.example.mymoviememoir.network.interfaces.RestfulPostModel;
import com.example.mymoviememoir.network.request.SignUpCredentialRequest;
import com.example.mymoviememoir.utils.CredentialInfoUtils;
import com.example.mymoviememoir.utils.GsonUtils;
import com.example.mymoviememoir.utils.PasswordUtils;
import com.example.mymoviememoir.utils.PersonInfoUtils;
import com.example.mymoviememoir.utils.Utils;
import com.example.mymoviememoir.utils.Values;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class SignUpActivity extends BaseRequestRestfulServiceActivity {
    /**
     * Use regex to make an mail validation
     * reference the regex pattern from
     * http://regexlib.com/Search.aspx?k=email&AspxAutoDetectCookieSupport=1
     */
    private final String REGEX = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";

    private View btnSelectBirthday;

    private LocalDate m_birthday;

    private TextInputEditText eFirstName;
    private TextInputEditText eLastName;
    private TextView tvShowBirthday;
    private TextInputEditText eAddress;
    private TextInputEditText ePostcode;
    private TextInputEditText eEmail;
    private TextInputEditText ePassword;
    private TextInputEditText eCheckedPassword;
    private RadioGroup genderGroup;
    private Spinner spState;
    private Toolbar toolbar;
    private View signUpBtn;


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
        eAddress = findViewById(R.id.e_address);
        genderGroup = findViewById(R.id.gender_group);
        spState = findViewById(R.id.sp_state);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnSelectBirthday.setOnClickListener(v -> new DatePickerDialog(SignUpActivity.this, (view, year, month, dayOfMonth) -> {
            LocalDate selectBirthday = LocalDate.of(year, month+1, dayOfMonth);
            m_birthday = selectBirthday;
            tvShowBirthday.setText(String.format("The birthday is: %s", m_birthday.format(Values.SIMPLE_DATE_FORMAT)));
            tvShowBirthday.setVisibility(View.VISIBLE);
            if (!isValidBirthDay(m_birthday)) {
                findViewById(R.id.tv_error_birthday_text).setVisibility(View.VISIBLE);
            }
        }, 1990, 0, 1).show());

        signUpBtn = findViewById(R.id.btn_sign_up);
        signUpBtn.setOnClickListener(v -> {
            if (isInformationValid()) {
                requestCheckUserEmailExisting();
            }
        });
    }

    private void requestCheckUserEmailExisting() {
        requestRestfulService(MyMovieMemoirRestfulAPI.CHECK_USER_NAME, (RestfulGetModel) () -> Collections.singletonList(eEmail.getText().toString()));
    }

    private void tryToSignUp() {
        final String password;
        try {
            password = PasswordUtils.getHashedPassword(ePassword.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "an error happens during hashing the password", Toast.LENGTH_SHORT).show();
            return;
        }
        final String email = eEmail.getText().toString();
        final RestfulPostModel postModel = new SignUpCredentialRequest(email, password);
        requestRestfulService(MyMovieMemoirRestfulAPI.SIGN_UP_CREDENTIALS, postModel);
    }


    private void tryToSignPerson(int id, SignUpCredentialRequest request) {
        Person personRequest = new Person();
        personRequest.setCredentialsId(new Credentials(id, request.getUsername(), request.getPassword()));
        personRequest.setDob(LocalDateTime.of(m_birthday, LocalTime.now()).format(Values.REQUESTING_FORMAT));
        personRequest.setFname(eFirstName.getText().toString());
        personRequest.setState(spState.getSelectedItem().toString());
        personRequest.setPostcode(ePostcode.getText().toString());
        personRequest.setSurname(eLastName.getText().toString());
        personRequest.setGender(getGenderEnumByRadioButton().genderSymbol);
        personRequest.setAddress(eAddress.getText().toString());
        requestRestfulService(MyMovieMemoirRestfulAPI.SIGN_UP_PERSON, personRequest);
    }

    @Override
    public void preExecute(RequestHelper helper) {
        super.preExecute(helper);
        signUpBtn.setEnabled(false);
    }

    @Override
    public void onExecuteFailed(RequestHelper helper, String message, Exception ex) {
        super.onExecuteFailed(helper, message, ex);
        signUpBtn.setEnabled(true);
    }

    @Override
    public void onPostExecute(RequestHelper helper, String response) {
        super.onPostExecute(helper, response);
        signUpBtn.setEnabled(true);
        switch (helper.getRestfulAPI()) {
            case CHECK_USER_NAME:
                List<SignUpCredentialRequest> emailList = GsonUtils.fromJsonToList(response, SignUpCredentialRequest.class);
                if (emailList == null || emailList.isEmpty()) {
                    tryToSignUp();
                } else {
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
                    int personId = Integer.parseInt(response);
                    if (personId <= 0) {
                        Toast.makeText(this, "Sign up failed, please try again", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(this, "sign up successful", Toast.LENGTH_SHORT).show();
                    Person personRequest = (Person) helper.getBodyRequestModel();
                    personRequest.setId(personId);
                    PersonInfoUtils.setInstance(this, personRequest);
                    CredentialInfoUtils.setInstance(this, personRequest.getCredentialsId());
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


    /**
     * Input validation
     *
     * @return true if the user has input right information.
     */
    private boolean isInformationValid() {
        boolean success = true;
        if (!isValidEmail(eEmail.getText().toString())) {
            eEmail.setError("the email address is invalid");
            success = false;
        }
        if (!isValidPostCode(ePostcode.getText().toString())) {
            ePostcode.setError("the post code is invalid");
            success = false;
        }
        if (Utils.isBlank(ePassword.getText())) {
            ePassword.setError("the password cannot be empty");
            success = false;
        }
        if (Utils.isBlank(eCheckedPassword.getText())) {
            eCheckedPassword.setError("the password cannot be empty");
            success = false;
        }
        if (Utils.isBlank(eAddress.getText())) {
            eAddress.setError("The address cannot be empty");
            success = false;
        }
        if (!TextUtils.equals(eCheckedPassword.getText(), ePassword.getText()) && !Utils.isBlank(ePassword.getText()) && !Utils.isBlank(eCheckedPassword.getText())) {
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
        if (Utils.isBlank(email)) {
            return false;
        }
        final Pattern pattern = Pattern.compile(REGEX);
        return pattern.matcher(email).matches();
    }

    private boolean isValidBirthDay(@Nullable LocalDate birthday) {
        if (birthday == null) {
            return false;
        }
        return birthday.compareTo(LocalDate.now()) < 0;
    }


    private boolean isValidPostCode(@Nullable String mState) {

        if (Utils.isBlank(mState) || mState.length() != 4) {
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

        Gender(String s) {
            this.genderSymbol = s;
        }

        @NonNull
        @Override
        public String toString() {
            return genderSymbol;
        }

    }
}
