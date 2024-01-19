package birzeit.edu.androidcarproject.ui.addadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import birzeit.edu.androidcarproject.ChooseRole;
import birzeit.edu.androidcarproject.SharedViewModel;
import birzeit.edu.androidcarproject.SignUP;
import birzeit.edu.androidcarproject.databinding.FragmentAddAdminBinding;

public class AddAdminFragment extends Fragment {

    private FragmentAddAdminBinding binding;
    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        // Retrieve email from the shared ViewModel
        String emailReceived = sharedViewModel.getUserEmail();
        Intent intent =new Intent(getActivity(), SignUP.class);
        intent.putExtra("userType", 1);
        intent.putExtra("nextIntentAdminHome", 1);
        intent.putExtra("AdminEmail", emailReceived);
        startActivity(intent);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}