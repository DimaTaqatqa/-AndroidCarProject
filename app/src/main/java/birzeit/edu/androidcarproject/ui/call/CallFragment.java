package birzeit.edu.androidcarproject.ui.call;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import birzeit.edu.androidcarproject.R;
import birzeit.edu.androidcarproject.databinding.FragmentCallBinding;

public class CallFragment extends Fragment {


    private FragmentCallBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCallBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button callCarDealer = root.findViewById(R.id.buttonCallCarDealer);
        Button openMap = root.findViewById(R.id.buttonOpenMap);
        Button sendEmail = root.findViewById(R.id.buttonSendEmail);

        callCarDealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent();
                dialIntent.setAction(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:+972595397397"));
                startActivity(dialIntent);
            }
        });

        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapsIntent = new Intent();
                mapsIntent.setAction(Intent.ACTION_VIEW);
                mapsIntent.setData(Uri.parse("geo:31.9171256,35.2169641"));
                startActivity(mapsIntent);
            }
        });
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gmailIntent = new Intent();
                gmailIntent.setAction(Intent.ACTION_SENDTO);
                gmailIntent.setType("message/rfc822");
                gmailIntent.setData(Uri.parse("mailto:"));
                gmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"CarDealer@cars.com"});
                gmailIntent.putExtra(Intent.EXTRA_SUBJECT, "My Subject");
                gmailIntent.putExtra(Intent.EXTRA_TEXT, "Content of the message");
                startActivity(gmailIntent);
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