package birzeit.edu.androidcarproject.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Car Dealer Group\n\n: Your destination for modern cars at competitive prices.\n We prioritize long-term customer loyalty through transparent pricing, exceptional service, and a commitment to quality.\n\nDrive with confidence with us.");

    }

    public LiveData<String> getText() {
        return mText;
    }
}