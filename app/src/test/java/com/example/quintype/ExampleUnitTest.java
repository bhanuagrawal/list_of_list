package com.example.quintype;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.example.quintype.data.ItemsRepo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private Context context = ApplicationProvider.getApplicationContext();

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}