package com.chetaru.tribe365_new.UI.UserInfo;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.res.Resources;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.collection.SimpleArrayMap;
import androidx.collection.SparseArrayCompat;
import androidx.core.app.ComponentActivity;
import androidx.fragment.app.FragmentController;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.utility.ErrorLayout;
import com.chetaru.tribe365_new.utility.MarshMallowPermission;
import com.chetaru.tribe365_new.utility.Res;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class ActLoginTest {
    @Mock
    EditText et_username;
    @Mock
    EditText et_password;
    @Mock
    TextView tv_signin;
    @Mock
    TextView tv_forgotPass;
    @Mock
    RadioButton rb_user;
    @Mock
    RadioButton rb_super_admin;
    @Mock
    LinearLayout ll_topblock;
    @Mock
    TextView tv_help;
    @Mock
    Context context;
    @Mock
    Utility utility;
    @Mock
    BaseRequest baseRequest;
    @Mock
    SessionParam sessionParam;
    @Mock
    MarshMallowPermission marshMallowPermission;
    @Mock
    ErrorLayout errorLayout;
    @Mock
    ArrayList<String> permissionsToRequest;
    @Mock
    ArrayList<String> permissionsRejected;
    @Mock
    ArrayList<String> permissions;
    @Mock
    List<Activity> sActivities;
    @Mock
    Activity currentAct;
    @Mock
    Context mContext;
    @Mock
    Dialog dialog;
    @Mock
    NotificationManager notificationManager;
    //Field mFirebaseAnalytics of type FirebaseAnalytics - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    BroadcastReceiver myReceiver;
    @Mock
    Res res;
    @Mock
    AppCompatDelegate mDelegate;
    @Mock
    Resources mResources;
    @Mock
    FragmentController mFragments;
    @Mock
    LifecycleRegistry mFragmentLifecycleRegistry;
    @Mock
    SparseArrayCompat<String> mPendingFragmentActivityResults;
    @Mock
    LifecycleRegistry mLifecycleRegistry;
    //Field mSavedStateRegistryController of type SavedStateRegistryController - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    ViewModelStore mViewModelStore;
    @Mock
    ViewModelProvider.Factory mDefaultFactory;
    //Field mOnBackPressedDispatcher of type OnBackPressedDispatcher - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    SimpleArrayMap<Class<? extends ComponentActivity.ExtraData>, ComponentActivity.ExtraData> mExtraDataMap;
    @InjectMocks
    ActLogin actLogin;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnCreate() throws Exception {
        when(utility.getAndroidID(any())).thenReturn("getAndroidIDResponse");
        actLogin.onCreate(null);
    }

    @Test
    public void testInit() throws Exception {
        when(utility.getAndroidID(any())).thenReturn("getAndroidIDResponse");
        actLogin.init();
    }

    @Test
    public void testOnRequestPermissionsResult() throws Exception {
        actLogin.onRequestPermissionsResult(0, new String[]{"permissions"}, new int[]{0});
    }

    @Test
    public void testOnClick() throws Exception {
        actLogin.onClick(null);
    }

    @Test
    public void testValidation() throws Exception {
        boolean result = actLogin.validation();
        Assert.assertEquals(true, result);
    }

    @Test
    public void testLoginApi() throws Exception {
        actLogin.loginApi();
    }

    @Test
    public void testDialog() throws Exception {
        actLogin.dialog();
    }

    @Test
    public void testOnPause() throws Exception {
        actLogin.onPause();
    }

    @Test
    public void testOnResume() throws Exception {
        actLogin.onResume();
    }

    @Test
    public void testFinishAllActivitiesStatic() throws Exception {
        ActLogin.finishAllActivitiesStatic();
    }

    @Test
    public void testNotiCallBack() throws Exception {
        Void result = actLogin.NotiCallBack("Action");
        Assert.assertEquals(null, result);
    }

    @Test
    public void testSetStatusBarColor() throws Exception {

    }

    @Test
    public void testGetResources() throws Exception {
        Resources result = actLogin.getResources();
        Assert.assertEquals(null, result);
    }

    @Test
    public void testSetAppBar() throws Exception {

    }

    @Test
    public void testCallHomeAct() throws Exception {
        actLogin.callHomeAct(null);
    }

    @Test
    public void testHideSoftKeyboard() throws Exception {
        actLogin.hideSoftKeyboard(mContext);
    }

    @Test
    public void testGetOpenCloseAppStatus() throws Exception {
        actLogin.getOpenCloseAppStatus();
    }

    @Test
    public void testFinishAllActivities() throws Exception {
        actLogin.finishAllActivities();
    }

    @Test
    public void testGetAppString() throws Exception {
        String result = actLogin.getAppString(0);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testDialogBaseActivity() throws Exception {
        when(utility.getCurrentDate()).thenReturn("getCurrentDateResponse");

        actLogin.dialogBaseActivity();
    }

    @Test
    public void testApi_addHappyIndex() throws Exception {
        when(utility.getCurrentDate()).thenReturn("getCurrentDateResponse");

    }

    @Test
    public void testLogoutDialog() throws Exception {
        actLogin.logoutDialog();
    }

    @Test
    public void testApi_logout() throws Exception {
        actLogin.api_logout();
    }

    @Test
    public void testHandleIntent() throws Exception {

    }

    @Test
    public void testApi_notificationRead() throws Exception {
        actLogin.api_notificationRead("id");
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme