package com.flightstats.client.example;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import scala.runtime.AbstractFunction1;
import scala.runtime.AbstractPartialFunction;
import scala.util.Either;

import com.flightstats.api.v1.FSAirport;
import com.flightstats.client.FSAirports;
import com.flightstats.client.FSTestClients;

import dispatch.Promise;

public class FSJavaExampleTest {

    FSAirports airports = FSTestClients.airports();

    @Test
    public void usingApply(){
        Promise<Either<Throwable, FSAirport>> promise = airports.byCode("PDX");

        // this call blocks - not what we want
        Either<Throwable, FSAirport> either = promise.apply();

        assertNotNull(either);

        System.out.println("Got: " + ( either.isRight()
                           ? either.right().get().iata()
                           : "Error: " + either.left().get().getMessage()
                           )
                );

        assertTrue(either.isRight());
    }

    @Test
    public void onComplete(){
        Promise<Either<Throwable, FSAirport>> promise = airports.byCode("PDX");

        promise.onComplete(
            new AbstractFunction1<Either<Throwable, Either<Throwable, FSAirport>>, FSAirport>(){
                @Override
                public FSAirport apply(Either<Throwable, Either<Throwable, FSAirport>> arg0) {
                    if(arg0.isRight()){
                        if(arg0.right().get().isRight()){
                            System.out.println("succesful response: " + arg0.right().get().right().get().iata());
                            return arg0.right().get().right().get();
                        }else{
                            System.out.println("Failed!" + arg0.right().get().left().get().getMessage());
                            fail("whoops!" + arg0.right().get().left().get().getMessage());
                            throw new RuntimeException(arg0.right().get().left().get());
                        }
                    }else{
                        System.out.println("Failed!" + arg0.left().get().getMessage());
                        fail("whoops!" + arg0.left().get().getMessage());
                        throw new RuntimeException(arg0.left().get());
                    }
                }
            });

        // *now* block
        promise.apply();
    }
 
    /*
     * this test shows what's necessary to handle success on the promise returned by airports.byCode
     */
    @Test
    public void onSuccess(){
        Promise<Either<Throwable, FSAirport>> promise = airports.byCode("PDX");
        
        promise.onSuccess( 
            new AbstractPartialFunction<Either<Throwable, FSAirport>, FSAirport>(){
                @Override
                public FSAirport apply(Either<Throwable, FSAirport> arg0) {
                    if(arg0.isRight()){
                        System.out.println("succesful response: " + arg0.right().get().iata());
                        return arg0.right().get();
                    }else{
                        System.out.println("Failed!" + arg0.left().get().getMessage());
                        fail("whoops!" + arg0.left().get().getMessage());
                        throw new RuntimeException(arg0.left().get());
                    }
                }
                @Override
                public boolean isDefinedAt(Either<Throwable, FSAirport> arg0) {
                    return true;
                }
            });

        // *now* block
        promise.apply();
    }

    volatile Boolean onFailureGot = true;
    /*
     * this test shows what's necessary to handle failure on the promise returned by airports.byCode
     */
    @Test
    public void onFailure(){
        Promise<Either<Throwable, FSAirport>> promise = airports.byCode("blarg!");
        
        promise.onFailure( 
            new AbstractPartialFunction<Throwable, FSAirport>(){
                @Override
                public FSAirport apply(Throwable arg0) {
                        System.out.println("hey Chet, Failed!" + arg0.getMessage());
                        onFailureGot = false;
                        return null;
                }
                @Override
                public boolean isDefinedAt(Throwable arg0) {
                    return true;
                }
            });
        promise.onSuccess( 
            new AbstractPartialFunction<Either<Throwable, FSAirport>, FSAirport>(){
                @Override
                public FSAirport apply(Either<Throwable, FSAirport> arg0) {
                    if(arg0.isRight()){
                        System.out.println("succesful response: " + arg0.right().get().iata());
                        return arg0.right().get();
                    }else{
                        onFailureGot = false;  // THIS is the one that gets called.  Nasty
                        System.out.println("Failed!" + arg0.left().get().getMessage());
                        fail("whoops!" + arg0.left().get().getMessage());
                        throw new RuntimeException(arg0.left().get());
                    }
                }
                @Override
                public boolean isDefinedAt(Either<Throwable, FSAirport> arg0) {
                    return true;
                }
            });

        // *now* block
        promise.apply();
        assertFalse(onFailureGot);
        
    }

}

