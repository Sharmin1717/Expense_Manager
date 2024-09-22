package com.example.emanager;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emanager.Model.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class DashboardFragment extends Fragment {

    private FloatingActionButton fab_main_btn;
    private FloatingActionButton fab_income_btn;
    private FloatingActionButton fab_expense_btn;

    private TextView fab_income_txt;
    private TextView fab_expense_txt;

    private boolean is_open = false;

    private Animation FadeOpen, FadeClose;

    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;
    private DatabaseReference mExpenseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();
        mIncomeDatabase = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        mExpenseDatabase = FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid);

        fab_main_btn = myview.findViewById(R.id.fb_main_plus_btn);
        fab_income_btn = myview.findViewById(R.id.income_Ft_btn);
        fab_expense_btn = myview.findViewById(R.id.expense_Ft_btn);

        fab_income_txt = myview.findViewById(R.id.income_ft_text);
        fab_expense_txt = myview.findViewById(R.id.expense_ft_text);

        FadeOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_open);
        FadeClose = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_close);

        fab_main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_open) {
                    closeFabMenu();
                } else {
                    openFabMenu();
                }
            }
        });

        addData();

        return myview;
    }

    private void openFabMenu() {
        fab_income_btn.startAnimation(FadeOpen);
        fab_expense_btn.startAnimation(FadeOpen);
        fab_income_txt.startAnimation(FadeOpen);
        fab_expense_txt.startAnimation(FadeOpen);

        fab_income_btn.setClickable(true);
        fab_expense_btn.setClickable(true);
        fab_income_txt.setClickable(true);
        fab_expense_txt.setClickable(true);

        is_open = true;
    }

    private void closeFabMenu() {
        fab_income_btn.startAnimation(FadeClose);
        fab_expense_btn.startAnimation(FadeClose);
        fab_income_txt.startAnimation(FadeClose);
        fab_expense_txt.startAnimation(FadeClose);

        fab_income_btn.setClickable(false);
        fab_expense_btn.setClickable(false);
        fab_income_txt.setClickable(false);
        fab_expense_txt.setClickable(false);

        is_open = false;
    }

    private void addData() {
        fab_income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incomeDataInsert();
            }
        });

        fab_expense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expenseDataInsert();
            }
        });
    }

    public void incomeDataInsert() {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View myview = inflater.inflate(R.layout.custom_layout_for_insertdata, null);
        mydialog.setView(myview);
        AlertDialog dialog = mydialog.create();

        EditText edtAmount = myview.findViewById(R.id.amount_edt);
        EditText edtType = myview.findViewById(R.id.type_edt);
        EditText edtNote = myview.findViewById(R.id.note_edt);
        Button btnSave = myview.findViewById(R.id.btnSave);
        Button btnCancel = myview.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = edtType.getText().toString().trim();
                String amount = edtAmount.getText().toString().trim();
                String note = edtNote.getText().toString().trim();

                if (TextUtils.isEmpty(type)) {
                    edtType.setError("Required Field...");
                    return;
                }
                if (TextUtils.isEmpty(amount)) {
                    edtAmount.setError("Required Field...");
                    return;
                }
                int ourAmountInt = Integer.parseInt(amount);
                if (TextUtils.isEmpty(note)) {
                    edtNote.setError("Required Field...");
                    return;
                }

                String id = mIncomeDatabase.push().getKey();
                String date = DateFormat.format("dd-MM-yyyy", Calendar.getInstance().getTime()).toString();

                Data data = new Data(ourAmountInt, type, note, id, date);
                mIncomeDatabase.child(id).setValue(data);

                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void expenseDataInsert() {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View myview = inflater.inflate(R.layout.custom_layout_for_insertdata, null);
        mydialog.setView(myview);
        AlertDialog dialog = mydialog.create();

        EditText edtAmount = myview.findViewById(R.id.amount_edt);
        EditText edtType = myview.findViewById(R.id.type_edt);
        EditText edtNote = myview.findViewById(R.id.note_edt);
        Button btnSave = myview.findViewById(R.id.btnSave);
        Button btnCancel = myview.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = edtType.getText().toString().trim();
                String amount = edtAmount.getText().toString().trim();
                String note = edtNote.getText().toString().trim();

                if (TextUtils.isEmpty(type)) {
                    edtType.setError("Required Field...");
                    return;
                }
                if (TextUtils.isEmpty(amount)) {
                    edtAmount.setError("Required Field...");
                    return;
                }
                int ourAmountInt = Integer.parseInt(amount);
                if (TextUtils.isEmpty(note)) {
                    edtNote.setError("Required Field...");
                    return;
                }

                String id = mExpenseDatabase.push().getKey();
                String date = DateFormat.format("dd-MM-yyyy", Calendar.getInstance().getTime()).toString();

                Data data = new Data(ourAmountInt, type, note, id, date);
                mExpenseDatabase.child(id).setValue(data);

                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}


