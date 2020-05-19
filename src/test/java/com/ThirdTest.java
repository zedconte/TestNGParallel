package com;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.BaseTest;

/**
 * Created by ONUR on 03.12.2016.
 */
public class ThirdTest extends BaseTest {

    @Test
    public void YANDEX3() {
        System.out.println("Yandex Test Started! " + "Thread Id: " +  Thread.currentThread().getId());
        getDriver().navigate().to("http://www.yandex.com");
        System.out.println("Yandex Test's Page title is: " + getDriver().getTitle() +" " + "Thread Id: " + Thread.currentThread().getId());
        Assert.assertEquals(getDriver().getTitle(), "Yandex");
        System.out.println("Yandex Test Ended! " + "Thread Id: " +  Thread.currentThread().getId());
    }
}