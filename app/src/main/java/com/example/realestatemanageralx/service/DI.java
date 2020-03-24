package com.example.realestatemanageralx.service;



/**
 * Dependency injector to get instance of services
 */
public class DI {

    private static RealApiService service = new RealApiService();

    /**
     * Get an instance on @{@link RealApiService}
     * @return this instance
     */
    public static RealApiService getRestApiService() {
        return service;
    }

    /**
     * Get always a new instance on @{@link RealApiService}. Useful for tests, so we ensure the context is clean.
     * @return a new instance
     */
    public static RealApiService getNewInstanceApiService() {
        return new RealApiService();
    }
}
