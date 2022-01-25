package com.moataz.salah.propertymanagement.ui.expenseDetails;

import android.content.Context;

import androidx.lifecycle.ViewModelProvider;

import com.moataz.salah.propertymanagement.databinding.ExpensesDetailsFragmentBinding;
import com.moataz.salah.propertymanagement.databinding.VacantDetailsFragmentBinding;

public class ExpenseDetailsModelFactory extends ViewModelProvider.NewInstanceFactory {
    public ExpenseDetailsModelFactory(Context mContext, ExpensesDetailsFragmentBinding binding){}
}