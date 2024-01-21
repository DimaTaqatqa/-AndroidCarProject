package birzeit.edu.androidcarproject;

import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private String userEmail;

        public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
