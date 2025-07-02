package com.chetaru.tribe365_new.UI.UserInfo;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import android.content.Context;

import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.JsonObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Act_Forgot_PasswordTest {

    private Act_Forgot_Password forgetPass;

    private ArgumentCaptor<RequestReciever> responseDataCapture;

    @Mock
    Context mMockContext;

    private String email= "abc@gmail.com";
    private JsonObject object= new JsonObject();
    @Before
    public void setUpForgetPassword(){
        MockitoAnnotations.initMocks(this);
        forgetPass= new Act_Forgot_Password(mMockContext);
    }

    @Test
    public void test_api_ForgotPassword(){
        forgetPass.api_ForgotPassword();
        verify(forgetPass).api_ForgotPassword();

            BaseRequest baseRequest= new BaseRequest(mMockContext);
            baseRequest.setBaseRequestListner(responseDataCapture.capture());

        responseDataCapture.getValue().onSuccess(200,"successFull",object);
        ArgumentCaptor<String> argumentCaptor=ArgumentCaptor.forClass(String.class);
        verify(forgetPass);
        assertEquals("SuccessFull",argumentCaptor.getValue());

    }
    @Test
    public void emailValidator_CorrectEmailSimple_ReturnTrue() throws Exception {
        String email = "name@email.com";
        assertTrue(Utility.isValidEmail(email));

    }

    @Test
    public void emailValidator_CorrectEmailSubDomain_ReturnTrue() throws Exception {
        String email = "name@email.co.uk";
        assertTrue(Utility.isValidEmail(email));
    }

    @Test
    public void emailValidator_InvalidEmailNoTld_ReturnsFalse() throws Exception {
        String email = "name@email";
        assertFalse(Utility.isValidEmail(email));
    }

    @Test
    public void emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        String email = "name@email..com";
        assertFalse(Utility.isValidEmail(email));
    }

    @Test
    public void emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        String email = "@email.com";
        assertFalse(Utility.isValidEmail(email));
    }

    @Test
    public void emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(Utility.isValidEmail(""));
    }

    @Test
    public void emailValidator_NullEmail_ReturnsFalse() {
        assertFalse(Utility.isValidEmail(null));
    }


}
