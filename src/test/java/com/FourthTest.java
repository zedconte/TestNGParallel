package com;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.BaseTest;

/**
 * Created by ONUR on 03.12.2016.
 */
public class FourthTest extends BaseTest {

    @Test
    public void GOOGLE4() {
        System.out.println("Google2 Test Started! " + "Thread Id: " +  Thread.currentThread().getId());
        getDriver().navigate().to("http://www.google.com");
        System.out.println("Google2 Test's Page title is: " + getDriver().getTitle() +" " + "Thread Id: " +  Thread.currentThread().getId());
        Assert.assertEquals(getDriver().getTitle(), "Google");
        System.out.println("Google2 Test Ended! " + "Thread Id: " +  Thread.currentThread().getId());
    }

}