


# Real Estate Manager

This application propose a list of real estate offers for sale located in NYC. It allows anyone to consult the details of the offers, contact the real estate agent in charge, see them on a map, and much more. It also has a professional acces for the agents to manage their offers.
# Files

All the source files can be downloaded or cloned from GitHub, at [**[[https://github.com/alex91270/Go4Lunch](https://github.com/alex91270/Go4Lunch)]**

## Compiling and running
In order to run the app, you need AndroidStudio.
- If you downloaded the sources package, then click on File/Open, then select the directory where you saved it.
- Otherwise, you can click on: File/New/Project from version control/Git, then, fill in the Url of the package to clone it straight forward.
	> To run the app, you can either use an emulator, or a real Android device connected in USB (Device with API 19 minimum).

-  To properly run the application, many dependencies are required in your gradle file, here is a list of the additional dependencies used in the project:
    > //EASY PERMISSIONS
    implementation 'pub.devrel:easypermissions:3.0.0'

    > // ViewModel and LiveData
    implementation "android.arch.lifecycle:extensions:1.1.1"
    annotationProcessor "android.arch.lifecycle:compiler:1.1.1"

    > // Room
    implementation "androidx.room:room-runtime:2.2.5"
    implementation "androidx.room:room-compiler:2.2.5"
    annotationProcessor "androidx.room:room-compiler:2.2.5"

    implementation "android.arch.persistence.room:runtime:1.0.0"

    > //Files copy
    implementation 'com.wshunli.android:android-copy-assets:2.2.0'

    > //GLIDE
    implementation 'com.github.bumptech.glide:glide:4.9.0'

    > //MAPS UTILS
    implementation 'com.google.maps.android:android-maps-utils:0.4'

    > //PLACES
    implementation 'com.google.android.libraries.places:places:2.0.0'

    > //RANGE SEEK BAR
    implementation 'org.florescu.android.rangeseekbar:rangeseekbar-library:0.3.0'

    > // EVENT BUS
    implementation 'org.greenrobot:eventbus:3.1.1'

    > //BUTTER KNIFE
    implementation 'com.jakewharton:butterknife:10.2.0'
    annotationProcessor 'com.jakewharton:butterknife-compiler:10.2.0'   
}


## Pull requests

No pull request desired. The evolutions of the app are managed by it's dev team.