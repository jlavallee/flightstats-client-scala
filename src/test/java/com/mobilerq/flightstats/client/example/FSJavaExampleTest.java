package com.mobilerq.flightstats.client.example;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import scala.concurrent.Await;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.runtime.AbstractFunction1;
import scala.runtime.AbstractPartialFunction;
import scala.util.Try;

import com.mobilerq.flightstats.api.v1.FSAirport;
import com.mobilerq.flightstats.client.FSAirports;
import com.mobilerq.flightstats.client.FSTestClients;

/* This "test" doesn't really test anything, it just shows examples of
 * how to handle the Futures returned by Dispatch in Java
 *
 */
public class FSJavaExampleTest {
    ExecutionContext executionContext = scala.concurrent.ExecutionContext$.MODULE$.global();
    FSAirports airports = FSTestClients.airports();

    @Test
    public void usingApply() throws Exception {
        Future<FSAirport> future = airports.byCode("PDX",
                scala.collection.immutable.Map$.MODULE$.<String, String>empty());

        // this call blocks
        FSAirport airport = Await.result(future, Duration.apply("1 minute"));

        assertNotNull(airport);
    }

    @Test
    public void onComplete(){
        Future<FSAirport> future = airports.byCode("PDX",
                scala.collection.immutable.Map$.MODULE$.<String, String>empty());

        future.onComplete(
            new AbstractFunction1<Try<FSAirport>, Throwable>(){

                @Override
                public Throwable apply(Try<FSAirport> arg0) {
                    if(arg0.isFailure()){
                        
                    }
                    // do something with arg0
                    return null;
                }
            },
            executionContext
        );
    }

    /*
     * this test shows what's necessary to handle success on the future returned by airports.byCode
     */
    @Test
    public void onSuccess(){
        Future<FSAirport> future = airports.byCode("PDX",
                scala.collection.immutable.Map$.MODULE$.<String, String>empty());

        future.onSuccess(
            new AbstractPartialFunction<FSAirport, FSAirport>(){
                @Override
                public FSAirport apply(FSAirport arg0) {
                    return arg0;
                }
                @Override
                public boolean isDefinedAt(FSAirport arg0) {
                    return true;
                }
            },
            executionContext
        );
    }

    /*
     * this test shows what's necessary to handle failure on the future returned by airports.byCode
     */
    @Test
    public void onFailure(){
        Future<FSAirport> future = airports.byCode("blarg!",
                scala.collection.immutable.Map$.MODULE$.<String, String>empty());

        future.onFailure(
                new AbstractPartialFunction<Throwable, FSAirport>(){
                    @Override
                    public boolean isDefinedAt(Throwable arg0) {
                        return true;
                    }
                    
                },
                executionContext
            );
    }

}

