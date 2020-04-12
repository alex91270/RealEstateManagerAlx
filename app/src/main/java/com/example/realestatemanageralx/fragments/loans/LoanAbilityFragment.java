package com.example.realestatemanageralx.fragments.loans;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class LoanAbilityFragment extends Fragment {

    private RateViewModel rateViewModel;
    private List<Double> ratesList;
    private String updateDate;
    private TypesConversions tc = new TypesConversions();
    private EditText textIncome;
    private TextView textCapacity10y;
    private TextView textCapacity20y;
    private TextView textCapacity30y;
    private TextView textRatesUpdate;
    private Button buttonCalculate;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_loan_ability, container, false);
        textIncome = root.findViewById(R.id.loan_capacity_income);
        textCapacity10y = root.findViewById(R.id.loan_capacity_10y);
        textCapacity20y = root.findViewById(R.id.loan_capacity_20y);
        textCapacity30y = root.findViewById(R.id.loan_capacity_30y);
        textRatesUpdate = root.findViewById(R.id.capacity_update);
        buttonCalculate = root.findViewById(R.id.loan_ability_calculate);

        buttonCalculate.setOnClickListener(v -> {
            calculate();
        });

        initObserver();

        return root;
    }

    private void initObserver() {
        rateViewModel = ViewModelProviders.of(this).get(RateViewModel.class);
        rateViewModel.getRates().observe(this, new Observer<List<Rate>>() {
            public void onChanged(@Nullable List<Rate> rates) {
                updateDate = tc.getStringFromTimestamp((long)(rates.get(2).getValue()*1000));
                textRatesUpdate.setText("Rates used, last updated on " + updateDate);

                ratesList = new ArrayList<>();

                for (int i = 3; i < 33; i++) {
                    ratesList.add(tc.round(rates.get(i).getValue(), 2));
                    //Log.i("alex", "index: " + i + "  " + rates.get(i).getDataType() + ": " + rates.get(i).getValue());
                }
            }
        });
    }

    private void calculate() {
        double m = Double.valueOf(textIncome.getText().toString())/36; //max monthly payment

        double t10 = ratesList.get(9)/100; //rate
        long M10; //the result, the capacity on 10 years
        M10 = Math.round((m*(1-Math.pow((1+(t10/12)), -120))) / (t10/12));
        textCapacity10y.setText(String.valueOf(M10));

        double t20 = ratesList.get(19)/100; //rate
        long M20; //the result, the capacity on 20 years
        M20 = Math.round((m*(1-Math.pow((1+(t20/12)), -240))) / (t20/12));
        textCapacity20y.setText(String.valueOf(M20));

        double t30 = ratesList.get(29)/100; //rate
        long M30; //the result, the capacity on 30 years
        M30 = Math.round((m*(1-Math.pow((1+(t30/12)), -360))) / (t30/12));
        textCapacity30y.setText(String.valueOf(M30));
    }

}
