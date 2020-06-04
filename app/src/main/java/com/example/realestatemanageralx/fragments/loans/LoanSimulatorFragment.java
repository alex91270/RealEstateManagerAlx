package com.example.realestatemanageralx.fragments.loans;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.realestatemanageralx.R;
import com.example.realestatemanageralx.helpers.TypesConversions;
import com.example.realestatemanageralx.model.Rate;
import com.example.realestatemanageralx.viewmodels.RateViewModel;

import java.util.ArrayList;
import java.util.List;

public class LoanSimulatorFragment extends Fragment {

    private RateViewModel rateViewModel;
    private List<Double> ratesList;
    private String updateDate;
    private int duration;
    private EditText editTextLoanAmount;
    private Spinner durationSpinner;
    private EditText editTextRate;
    private Button calculateButton;
    private TextView textViewMonthlyAmount;
    private TextView textViewSuggestedRates;
    private Context context;
    private TypesConversions tc = new TypesConversions();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_loan_simulator, container, false);
        editTextLoanAmount = root.findViewById(R.id.loan_monthly_amount);
        durationSpinner = root.findViewById(R.id.spinnerDuration);
        editTextRate = root.findViewById(R.id.loan_monthly_rate);
        calculateButton = root.findViewById(R.id.loan_monthly_calculate);
        textViewMonthlyAmount = root.findViewById(R.id.loan_monthly_payment);
        textViewSuggestedRates = root.findViewById(R.id.loan_monthly_update);
        context = root.getContext();

        populateSpinner();
        initObserver();

        calculateButton.setOnClickListener(v -> {

            if (editTextLoanAmount.getText().toString().equals("") || editTextRate.getText().toString().equals("")) {
                Toast.makeText(context, "Please provide an amount and a rate", Toast.LENGTH_LONG).show();
            } else {
                calculate();
            }

        });

        return root;
    }

    private void populateSpinner() {
        final List<Integer> spinnerArray = new ArrayList<>();
        for (int i = 1; i < 31; i++) {
            spinnerArray.add(i);
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(context, R.layout.my_spinner, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationSpinner.setAdapter(adapter);

        durationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.i("alex", "position: " + position);
                duration = (position + 1) * 12;
                editTextRate.setText(String.valueOf(ratesList.get(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    private void initObserver() {
        rateViewModel = ViewModelProviders.of(this).get(RateViewModel.class);
        rateViewModel.getRates().observe(this, new Observer<List<Rate>>() {
            public void onChanged(@Nullable List<Rate> rates) {
                updateDate = tc.getStringFromTimestamp((long) (rates.get(2).getValue() * 1000));
                textViewSuggestedRates.setText("Suggested rates last updated on " + updateDate);
                //Log.i("alex", "update date: " + updateDate);

                ratesList = new ArrayList<>();

                for (int i = 3; i < 33; i++) {
                    ratesList.add(tc.round(rates.get(i).getValue(), 2));
                    Log.i("alex", "index: " + i + "  " + rates.get(i).getDataType() + ": " + rates.get(i).getValue());
                }
            }
        });
    }

    private void calculate() {
        double payment; //monthly payment, the result
        double M = Double.valueOf(editTextLoanAmount.getText().toString()); //initial borrowed amount
        double t = Double.valueOf(editTextRate.getText().toString()) / 100; //rate

        payment = (((M * t) / 12) / (1 - Math.pow((1 + (t / 12)), -duration)));

        textViewMonthlyAmount.setText(String.valueOf(Math.round(payment)));
    }


}
