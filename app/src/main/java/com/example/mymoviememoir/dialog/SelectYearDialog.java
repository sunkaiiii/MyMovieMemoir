package com.example.mymoviememoir.dialog;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.utils.Utils;

import java.util.Calendar;

public class SelectYearDialog extends DialogFragment {
    private EditText year;
    private Button ok;
    private Button cancel;
    private OnYearConfirmListener onYearConfirmListener;
    private OnCancelListener onCancelListener;
    private int selectYear = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Dialog_NoActionBar);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.year_select_dialog_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        year = view.findViewById(R.id.year);
        ok = view.findViewById(R.id.ok);
        cancel = view.findViewById(R.id.cancel);
        year.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - 1));
        ok.setOnClickListener((v) -> {
            if (Utils.isBlank(year.getText())) {
                Toast.makeText(getContext(), "Year cannot by empty", Toast.LENGTH_SHORT).show();
                return;
            }
            selectYear = Integer.parseInt(year.getText().toString());
            if (onYearConfirmListener != null) {
                onYearConfirmListener.onYearConfirmed(selectYear);
            }
            dismiss();
        });
        cancel.setOnClickListener((v) -> {
            dismiss();
        });
    }

    public SelectYearDialog setOnYearConfirmListener(OnYearConfirmListener listener) {
        this.onYearConfirmListener = listener;
        return this;
    }

    public SelectYearDialog setCancelListener(OnCancelListener listener) {
        this.onCancelListener = listener;
        return this;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (selectYear < 0 && onCancelListener != null) {
            onCancelListener.onCancel();
        }
    }

    public interface OnYearConfirmListener {
        void onYearConfirmed(int year);
    }

    public interface OnCancelListener {
        void onCancel();
    }

}
