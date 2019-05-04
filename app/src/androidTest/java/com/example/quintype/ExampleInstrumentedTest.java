package com.example.quintype;


import android.app.Application;
import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quintype.data.ItemsRepo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest{

    @Test
    public void isCorrectCacheExipyTimeSet() {
        ItemsRepo itemsRepo = new ItemsRepo(ApplicationProvider.getApplicationContext());
        itemsRepo.setCacheExipyTime();
        assertFalse(itemsRepo.isCacheExpired());
    }
}
