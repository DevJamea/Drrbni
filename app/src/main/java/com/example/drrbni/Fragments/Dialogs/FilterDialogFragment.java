package com.example.drrbni.Fragments.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.drrbni.Models.Filters;
import com.example.drrbni.databinding.FragmentFilterDialogBinding;

public class FilterDialogFragment extends DialogFragment {

    private FragmentFilterDialogBinding binding;
    public static final String TAG = "FilterDialog";
    private FilterListener filterListener;

    public interface FilterListener {
        void onFilter(Filters filters);
    }


    public FilterDialogFragment(FilterListener filterListener) {
        this.filterListener = filterListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        binding = FragmentFilterDialogBinding.inflate(getLayoutInflater() ,null ,false);

        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterListener.onFilter(getFilters());
                dismiss();
            }
        });

        binding.btnChancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setView(binding.getRoot() , 0,0,0,0);
        return alertDialog;
    }


    private String getSelectedEmployment(){
        String selectedEmployment = binding.employmentSpinner.getSelectedItem().toString();
        if (binding.employmentSpinner.getSelectedItemPosition() < 1) {
            return null;
        } else {
            return selectedEmployment;
        }
    }


    public void resetFilters() {
        binding.employmentSpinner.setSelection(0);
    }

    public Filters getFilters() {
        Filters filters = new Filters();
        filters.setEmployment(getSelectedEmployment());
        return filters;
    }


}