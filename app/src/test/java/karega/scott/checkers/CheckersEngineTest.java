package karega.scott.checkers;

import org.junit.Test;
import org.junit.Assert;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import android.content.Context;

@RunWith(MockitoJUnitRunner.class)
public class CheckersEngineTest  {
        @Mock Context context;

        @Test public void simple() {
          Assert.assertNotNull(context);
        }
}