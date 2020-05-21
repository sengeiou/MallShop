package com.epro.mall;

import android.text.TextUtils;
import android.widget.TextView;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        // assertEquals(4, 2 + 2);
        ArrayList list=new ArrayList();
        Object o=new Object();
        list.add(o);
        list.add(o);
        list.add(o);
        list.add(o);
        list.remove(o);

        for (int i = 0; i <list.size() ; i++) {
            System.out.println(list.get(i).toString());
        }

    }

}