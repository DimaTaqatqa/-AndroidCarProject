package birzeit.edu.androidcarproject.ui.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import birzeit.edu.androidcarproject.DataBaseHelper;
import birzeit.edu.androidcarproject.EditProfile;
import birzeit.edu.androidcarproject.R;
import birzeit.edu.androidcarproject.SharedViewModel;
import birzeit.edu.androidcarproject.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private DataBaseHelper db;
    private SharedViewModel sharedViewModel;
    private ImageView profilePicture;

    TextView emailTxt;
    TextView firstNameTxt;
    TextView lastNameTxt;
    TextView genderTxt;
    TextView countryTxt;
    TextView cityTxt;
    TextView phoneTxt;
    TextView roleTxt;

    Button editProfileButton;


    @SuppressLint("Range")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = new DataBaseHelper(requireContext());
        emailTxt = root.findViewById(R.id.emailTxt);
        firstNameTxt = root.findViewById(R.id.firstNameTxt);
        lastNameTxt = root.findViewById(R.id.lastNameTxt);
        genderTxt = root.findViewById(R.id.genderTxt);
        countryTxt = root.findViewById(R.id.countryTxt);
        cityTxt = root.findViewById(R.id.cityTxt);
        phoneTxt = root.findViewById(R.id.phoneTxt);
        roleTxt = root.findViewById(R.id.roleTxt);
        editProfileButton = root.findViewById(R.id.buttonEditProfile);
        profilePicture = root.findViewById(R.id.iv_inst_pic);


        // Obtain the shared ViewModel from the hosting activity
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        // Retrieve email from the shared ViewModel
        String emailReceived = sharedViewModel.getUserEmail();

        Cursor cursor = db.displayProfileData(emailReceived);
        @SuppressLint("Range") String firstName = null;
        @SuppressLint("Range") String lastName = null;
        @SuppressLint("Range") String gender = null;
        @SuppressLint("Range") String country = null;
        @SuppressLint("Range") String city = null;
        @SuppressLint("Range") String phone = null;
        byte[] photo = new byte[0];

        int userType = 0;
        if (cursor.moveToFirst()) {
            // Retrieve data from the cursor
            firstName = cursor.getString(cursor.getColumnIndex("first_name"));
            lastName = cursor.getString(cursor.getColumnIndex("last_name"));
            gender = cursor.getString(cursor.getColumnIndex("gender"));
            country = cursor.getString(cursor.getColumnIndex("country"));
            city = cursor.getString(cursor.getColumnIndex("city"));
            phone = cursor.getString(cursor.getColumnIndex("phone"));
            userType = cursor.getInt(cursor.getColumnIndex("user_type"));
            photo = cursor.getBlob(cursor.getColumnIndex("photo"));


        } else {
            // No rows in the result set
            Log.d("CursorData", "No data found");
        }

        cursor.close();

        emailTxt.setText(emailReceived);
        firstNameTxt.setText(firstName);
        lastNameTxt.setText(lastName);
        genderTxt.setText(gender);
        countryTxt.setText(country);
        cityTxt.setText(city);
        phoneTxt.setText(phone);
        if (photo != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            profilePicture.setImageBitmap(bitmap);
        }
        if (userType == 1) {
            roleTxt.setText("Admin");
        } else {
            roleTxt.setText("Customer");
        }

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                intent.putExtra("email",emailReceived);
                // Start the activity
                startActivity(intent);
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
