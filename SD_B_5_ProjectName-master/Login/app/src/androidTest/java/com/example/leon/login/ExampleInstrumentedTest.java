package com.example.leon.login;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.robotium.solo.Solo;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static com.example.leon.login.R.id.buttonSignin;
import static com.example.leon.login.R.id.editTextAccessCode;
import static com.example.leon.login.R.id.editTextEmail;
import static com.example.leon.login.R.id.editTextPassword;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.leon.login", appContext.getPackageName());
    }

    public void testLoginActivity(){

        //Test activity flow from Login Screen.

        Solo solo = new Solo(getInstrumentation(), getActivity());

        //Check for username
        solo.enterText(editTextEmail, "leon@gmail.com");

        //check for password.
        solo.enterText(editTextPassword, "password");

        solo.clickOnButton(buttonSignin);

        // Put a delay so that you testing with assert doesn't fail.
        solo.waitForActivity(OperationActivity.class);

        solo.assertCurrentActivity("Should open operation activity", OperationActivity.class);

        // View2 is a view that is lying in Activity2
        solo.clickOnButton(editTextAccessCode);
        // Put a delay so that you testing with assert doesn't fail.
        solo.waitForActivity(CompanyRegistration.class);

        solo.assertCurrentActivity("Should open company registration activity",CompanyRegistration.class);
    }







    public Solo.Config getActivity() {
        return getActivity();
    }
}
