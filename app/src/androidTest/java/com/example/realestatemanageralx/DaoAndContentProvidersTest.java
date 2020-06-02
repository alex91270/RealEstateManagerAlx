package com.example.realestatemanageralx;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.platform.app.InstrumentationRegistry;
import com.example.realestatemanageralx.database.AppDatabase;
import com.example.realestatemanageralx.helpers.LiveDataTestUtil;
import com.example.realestatemanageralx.model.Agent;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.model.Rate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.util.List;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static com.example.realestatemanageralx.providers.AgentContentProvider.AGENT_URI_ITEM;
import static com.example.realestatemanageralx.providers.MediasContentProvider.MEDIA_URI_ITEM;
import static com.example.realestatemanageralx.providers.PropertyContentProvider.PROPERTY_URI_ITEM;
import static com.example.realestatemanageralx.providers.RatesContentProvider.RATE_URI_ITEM;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class DaoAndContentProvidersTest {

    // FOR DATA
    private ContentResolver mContentResolver;
    private AppDatabase database;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        database = AppDatabase.getDatabase(InstrumentationRegistry.getInstrumentation().getTargetContext());
        mContentResolver = InstrumentationRegistry.getInstrumentation().getTargetContext().getContentResolver();
    }


    @Test public void compareAgentOne() throws InterruptedException{
        Agent agent = LiveDataTestUtil.getValue(database.agentDAO().getAgentById(1));

        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(AGENT_URI_ITEM, 1), null, null, null, null);
        cursor.moveToFirst();

        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertTrue(cursor.getString(1).equals(agent.getFirstName()));
        assertTrue(cursor.getString(2).equals(agent.getLastName()));

        cursor.close();
    }



    @Test
    public void comparePropertiesList() throws InterruptedException{

        List<Property> locallyAccessedList = LiveDataTestUtil.getValue(database.propertyDAO().getAllProperties());

        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(PROPERTY_URI_ITEM, 1), null, null, null, null);

        assertTrue(locallyAccessedList.size()==cursor.getCount());

        cursor.close();
    }

    @Test
    public void compareMediasForFirstProperty() throws InterruptedException{

        List<OfferMedia> locallyAccessedList = LiveDataTestUtil.getValue(database.offerMediaDAO().getMediasByPropertyId(1));

        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(MEDIA_URI_ITEM, 1), null, null, null, null);

        assertTrue(locallyAccessedList.size()==cursor.getCount());

        cursor.close();
    }


    @Test public void compareRateTenYears() throws InterruptedException {

        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(RATE_URI_ITEM, 13), null, null, null, null);
        Rate locallyAccessedRate = LiveDataTestUtil.getValue(database.rateDAO().getRateById(13));
        cursor.moveToFirst();
        double providerValue = cursor.getDouble(2);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertTrue(providerValue == locallyAccessedRate.getValue());

        cursor.close();
    }
}
