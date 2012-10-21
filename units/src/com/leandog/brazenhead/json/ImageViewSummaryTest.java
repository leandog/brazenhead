package com.leandog.brazenhead.json;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.leandog.brazenhead.test.BrazenheadTestRunner;

@RunWith(BrazenheadTestRunner.class)
public class ImageViewSummaryTest {
    
    @Mock ImageView imageView;
    @Mock Drawable drawable;
    
    @Test
    public void itKnowsIfTheDrawableIsPresent() {
        shouldHaveADrawable();
        assertThat(theSummary().hasDrawable, is(true));
    }
    
    @Test
    public void itKnowsTheBounds() {
        shouldHaveADrawable();
        shouldHaveBounds();
        assertThat(theSummary().drawableRect, is(notNullValue()));
    }

    private ImageViewSummary theSummary() {
        return new ImageViewSummary(imageView);
    }

    private void shouldHaveADrawable() {
        when(imageView.getDrawable())
            .thenReturn(drawable);
    }

    private void shouldHaveBounds() {
        when(drawable.getBounds())
            .thenReturn(new Rect());
    }

}
