package com.flightstats.client.example;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import scala.runtime.AbstractFunction1;
import scala.runtime.AbstractPartialFunction;
import scala.util.Either;

import com.flightstats.api.v1.FSAirport;
import com.flightstats.client.FSAirports;
import com.flightstats.client.FSTestClients;

import dispatch.Promise;

/* This "test" doesn't really test anything, it just shows examples of
 * how to handle the Promises returned by Dispatch in Java
 *
 */
public class FSJavaExampleTest {

    FSAirports airports = FSTestClients.airports();

    @Test
    public void usingApply(){
        Promise<FSAirport> promise = airports.byCode("PDX");

        // this call blocks
        FSAirport airport = promise.apply();

        assertNotNull(airport);
    }

    @Test
    public void onComplete(){
        Promise<FSAirport> promise = airports.byCode("PDX");

        promise.onComplete(
            new AbstractFunction1<Either<Throwable, FSAirport>, FSAirport>(){
                @Override
                public FSAirport apply(Either<Throwable, FSAirport> arg0) {
                    if(arg0.isRight()){
                        return arg0.right().get();
                    }else{
                        throw new RuntimeException(arg0.left().get());
                    }
                }
            });
    }

    /*
     * this test shows what's necessary to handle success on the promise returned by airports.byCode
     */
    @Test
    public void onSuccess(){
        Promise<FSAirport> promise = airports.byCode("PDX");

        promise.onSuccess(
            new AbstractPartialFunction<FSAirport, FSAirport>(){
                @Override
                public FSAirport apply(FSAirport arg0) {
                    return arg0;
                }
                @Override
                public boolean isDefinedAt(FSAirport arg0) {
                    return true;
                }
            });
    }

    /*
     * this test shows what's necessary to handle failure on the promise returned by airports.byCode
     */
    @Test
    public void onFailure(){
        Promise<Either<Throwable, FSAirport>> promise = airports.byCode("blarg!").either();

        promise.onFailure(
            new AbstractPartialFunction<Throwable, FSAirport>(){
                @Override
                public FSAirport apply(Throwable arg0) {
                    return null;
                }
                @Override
                public boolean isDefinedAt(Throwable arg0) {
                    return true;
                }
            });
    }

}

