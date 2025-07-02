package com.chetaru.tribe365_new;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import android.content.Context;

import com.chetaru.tribe365_new.UI.Risk.Act_RiskHome;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RiskHomeTest {

    private static final String TEST_STRING="Hello world";

    //As we don't have access to context in our JUnit classes,we need to mock it
    @Mock
    Context mMockContext;

    @Mock
    Act_RiskHome riskHome;

    @Before
    public void setUp(){
        riskHome= new Act_RiskHome(mMockContext);
    }

    @Test
    public void getValue(int a, int b){
        riskHome.add(a+b);
    }

    @Test
    public void testTheReturn(){
        //Create a mock object of the class Calculator
        //Calculator mockCalculator= Mockito.mock(Calculator.class);

        //Return the value of 30 when the add method is called with the arguments 10 and 20
       // Mockito.when(mockCalculator.add(10,20)).thenReturn(30);

        //Asserts that the return value of add method with arguments 10 and 20 is 30
        //assertEquals(mockCalculator.add(10,20), 30);
    }
    @Test
    public void readStringFromContext(){

        //Returns the TEST_STRING when getString(R.string.hello_world) is called
        when(mMockContext.getString(R.string.app_name)).thenReturn(TEST_STRING);

        //Creates an object of the ClassUnderTest with the mock context
        Act_RiskHome riskHome= new Act_RiskHome(mMockContext);

        //Stores the return value of getHelloWorldString() in result
        String result= riskHome.getHelloWorldString();

        //Asserts that result is the value of TEST_STRING
        Assert.assertThat(result,is(TEST_STRING));
    }

}
