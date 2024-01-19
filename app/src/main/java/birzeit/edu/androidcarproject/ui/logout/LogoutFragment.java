package birzeit.edu.androidcarproject.ui.logout;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

import birzeit.edu.androidcarproject.ChooseRole;
import birzeit.edu.androidcarproject.RegistrationAndLogin;
import birzeit.edu.androidcarproject.SignUP;
import birzeit.edu.androidcarproject.databinding.FragmentLogoutBinding;

public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Use getActivity() to get the context
        Intent intent = new Intent(getActivity(), RegistrationAndLogin.class);

        // Start the activity
        startActivity(intent);

        // Finish the current activity (if applicable)
        if (getActivity() != null) {
            getActivity().finish();
        }
        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}