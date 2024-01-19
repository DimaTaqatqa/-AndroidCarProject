package birzeit.edu.androidcarproject.ui.deletecustomer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import birzeit.edu.androidcarproject.DataBaseHelper;
import birzeit.edu.androidcarproject.R;
import birzeit.edu.androidcarproject.SignUP;
import birzeit.edu.androidcarproject.databinding.FragmentDeleteCustomerBinding;

public class DeleteCustomerFragment extends Fragment {

    private FragmentDeleteCustomerBinding binding;
    DataBaseHelper db;

    EditText email;
    Button deleteButton;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDeleteCustomerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = new DataBaseHelper(requireContext());

        email = root.findViewById(R.id.emailEditText);
        deleteButton = root.findViewById(R.id.deleteButton);


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailAddress = email.getText().toString();

                if (!db.emailExists("Customer", emailAddress)) {
                    Toast.makeText(getContext(), "Email doesn't exist!", Toast.LENGTH_SHORT).show();
                }else{
                    if(db.deleteCustomer(emailAddress))
                        Toast.makeText(getContext(), "Customer Deleted Successfully!", Toast.LENGTH_SHORT).show();
                        email.setText("");
                }

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}