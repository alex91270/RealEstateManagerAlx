package com.example.realestatemanageralx.database;

import android.os.AsyncTask;
import android.util.Log;
import com.example.realestatemanageralx.model.Agent;
import com.example.realestatemanageralx.model.OfferMedia;
import com.example.realestatemanageralx.model.Property;
import com.example.realestatemanageralx.model.Rate;

/**
 * Called if the database did not exist yet, to initialise it with initial datas
 */

public class DatabaseInitialize {


    public static void populateAsync(final AppDatabase db) {

        Log.i("alex", "populate async");

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {



        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateProjectsIfNewDb(mDb);
            return null;
        }
    }

    private static void populateProjectsIfNewDb(AppDatabase db) {

        Log.i("alex", "populating DB");

        //adding professional users
        db.agentDAO().insertAgent(new Agent("john", "doe", "john.doe", "1234", "john.doe@rem.com", "014075226"));
        db.agentDAO().insertAgent(new Agent("jane", "doe", "jane.doe", "1234", "jane.doe@rem.com", "014075226"));

        //adding properties
        db.propertyDAO().insertProperty(new Property("Meticulous and unique West Village Townhouse with separate Carriage House accessed by one the few remaining Horse Walks in Manhattan.",
                "New-York", "West village", 696, 6, 4, 5,
                3, true, 1581423466000L , "40.737412, -74.006920",
                13500000, 1, false, "5,+,3,+,0", 10, "Apartment", 0 ));

        db.propertyDAO().insertProperty(new Property("Rare opportunity to acquire a grand and lovingly maintained home on the most desirable street in Chelsea. Perfectly positioned on historic Cushman Row and overlooking the General Theological Seminary and its lovely gardens.",
                "New-York", "Chelsea", 439, 6, 3, 4,
                2, true, 1581423466000L , "40.744740, -74.003505",
                8650000, 2, false , "2,+,0,+,0", 9, "Apartment", 0 ));

        db.propertyDAO().insertProperty(new Property("This unique and stunning 5-story elevator townhouse is located in Chelsea and offers a 6 bedroom layout with private garage. The sensational double-height living room features 22-foot ceilings, exposed brick walls, a wood-burning/gas fireplace.",
                "New-York", "Chelsea", 418, 6, 3, 4,
                2, true, 1583756266000L , "40.741245, -73.999680",
                8995000, 1, false, "4,+,0,+,1", 9, "Apartment", 0 ));

        db.propertyDAO().insertProperty(new Property("In a word, PENTHOUSE A is spectacular. With a West and South-facing wrap terrace, it is sun-flooded with glorious views of Midtown Manhattan, the iconic Carlyle Hotel, the East River and a peek of Central Park.",
                "New-York", "Upper East Side", 520, 8, 9, 8,
                6, true, 1585311466000L , "40.775196, -73.960445",
                22319400, 2, false , "2,+,0,+,1", 11, "Apartment", 0 ));

        db.propertyDAO().insertProperty(new Property("This charming Maisonette Duplex is steeped in History. The ambiance of this home is influenced by color and travel. The atmosphere is an artistic mix, a picturesque journey combining a book collection, family portraits and original textile designs by the Florentine owner.",
                "New-York", "Murray Hill", 213, 3, 3, 2,
                2, true, 1578485866000L , "40.754387, -73.963513",
                2200000, 1, false , "0,9,2,6,0", 5, "Apartment", 0));

        db.propertyDAO().insertProperty(new Property("With unobstructed North, South and East-facing windows - no matter the season or the weather, this pin-drop quiet 15th floor apt. in the heart of the Upper East Side is wrapped with windows filled with natural light offering unobstructed views.",
                "New-York", "Lenox Hill", 157, 3, 3, 2,
                2, true, 1576757866000L , "40.765439, -73.966848",
                2350000, 2, false,"2,+,0,+,0", 6, "Apartment", 0));

        db.propertyDAO().insertProperty(new Property("Achieve the ultimate goal of living in the chic 70’s between Park and Madison! This spacious 2 bedroom, 2 full bath co-op is one block from Central Park and allows 75% financing, guarantors and sublets!",
                "New-York", "Upper East Side", 122, 2, 2, 1,
                1, false, 1577535466000L , "40.775125, -73.962203",
                1075000, 1, false , "5,+,0,8,0", 4, "Apartment", 0));

        db.propertyDAO().insertProperty(new Property("Welcome to vibrant Inwood! This Art Deco building, built 1939, 6 floors, forty-eight units boasts a spacious and bright four bedrooms oozing with charm. It features a large foyer, hardwood floors, high ceilings, eat-in kitchen and a spa.",
                "New-York", "Midtown", 140, 4, 2, 2,
                1, true, 1548850666000L , "40.755090, -73.986091",
                2350000, 2, false , "+,+,2,+,5", 7, "House", 0));

        //adding rates
        db.rateDAO().insertRate(new Rate("exchangeRate", 1.074506 ));
        db.rateDAO().insertRate(new Rate("dateExchangeRate", 1585004046 ));
        db.rateDAO().insertRate(new Rate("dateLoanInterest", 1584706666 ));
        db.rateDAO().insertRate(new Rate("Rate1", 0.37830001115799 ));
        db.rateDAO().insertRate(new Rate("Rate2", 0.446099996566772 ));
        db.rateDAO().insertRate(new Rate("Rate3", 0.528199970722198 ));
        db.rateDAO().insertRate(new Rate("Rate4", 0.614300012588501 ));
        db.rateDAO().insertRate(new Rate("Rate5", 0.698499977588654 ));
        db.rateDAO().insertRate(new Rate("Rate6", 0.777499973773956 ));
        db.rateDAO().insertRate(new Rate("Rate7", 0.850199997425079 ));
        db.rateDAO().insertRate(new Rate("Rate8", 0.916400015354156 ));
        db.rateDAO().insertRate(new Rate("Rate9", 0.976499974727631 ));
        db.rateDAO().insertRate(new Rate("Rate10", 1.03120005130768 ));
        db.rateDAO().insertRate(new Rate("Rate11", 1.0814000368118 ));
        db.rateDAO().insertRate(new Rate("Rate12", 1.1279000043869 ));
        db.rateDAO().insertRate(new Rate("Rate13", 1.17139995098114 ));
        db.rateDAO().insertRate(new Rate("Rate14", 1.21280002593994 ));
        db.rateDAO().insertRate(new Rate("Rate15", 1.25240004062653 ));
        db.rateDAO().insertRate(new Rate("Rate16", 1.29089999198914 ));
        db.rateDAO().insertRate(new Rate("Rate17", 1.32850003242493 ));
        db.rateDAO().insertRate(new Rate("Rate18", 1.36559998989105 ));
        db.rateDAO().insertRate(new Rate("Rate19", 1.40230000019073 ));
        db.rateDAO().insertRate(new Rate("Rate20", 1.43879997730255 ));
        db.rateDAO().insertRate(new Rate("Rate21", 1.47510004043579 ));
        db.rateDAO().insertRate(new Rate("Rate22", 1.51129996776581 ));
        db.rateDAO().insertRate(new Rate("Rate23", 1.54750001430511 ));
        db.rateDAO().insertRate(new Rate("Rate24", 1.58350002765656 ));
        db.rateDAO().insertRate(new Rate("Rate25", 1.61940002441406 ));
        db.rateDAO().insertRate(new Rate("Rate26", 1.6550999879837 ));
        db.rateDAO().insertRate(new Rate("Rate27", 1.69050002098083 ));
        db.rateDAO().insertRate(new Rate("Rate28", 1.72560000419617 ));
        db.rateDAO().insertRate(new Rate("Rate29", 1.76030004024506 ));
        db.rateDAO().insertRate(new Rate("Rate30", 1.79449999332428 ));


        //adding medias
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(1, "photo11.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(1, "photo12.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(1, "photo13.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(1, "photo14.jpg", true));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(1, "photo15.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(1, "photo16.jpg", false));

        db.offerMediaDAO().insertOfferMedia(new OfferMedia(2, "photo21.jpg", true));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(2, "photo22.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(2, "photo23.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(2, "photo24.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(2, "photo25.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(2, "photo26.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(2, "photo27.jpg", false));

        db.offerMediaDAO().insertOfferMedia(new OfferMedia(3, "photo31.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(3, "photo32.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(3, "photo33.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(3, "photo34.jpg", true));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(3, "photo35.jpg", false));

        db.offerMediaDAO().insertOfferMedia(new OfferMedia(4, "photo41.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(4, "photo42.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(4, "photo43.jpg", true));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(4, "photo44.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(4, "photo45.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(4, "photo46.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(4, "photo47.jpg", false));

        db.offerMediaDAO().insertOfferMedia(new OfferMedia(5, "photo51.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(5, "photo52.jpg", true));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(5, "photo53.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(5, "photo54.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(5, "photo55.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(5, "photo56.jpg", false));

        db.offerMediaDAO().insertOfferMedia(new OfferMedia(6, "photo61.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(6, "photo62.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(6, "photo63.jpg", true));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(6, "photo64.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(6, "photo65.jpg", false));

        db.offerMediaDAO().insertOfferMedia(new OfferMedia(7, "photo71.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(7, "photo72.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(7, "photo73.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(7, "photo74.jpg", true));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(7, "photo75.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(7, "photo76.jpg", false));

        db.offerMediaDAO().insertOfferMedia(new OfferMedia(8, "photo81.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(8, "photo82.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(8, "photo83.jpg", true));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(8, "photo84.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(8, "photo85.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(8, "photo86.jpg", false));
        db.offerMediaDAO().insertOfferMedia(new OfferMedia(8, "video87.mp4", false));

    }
}



