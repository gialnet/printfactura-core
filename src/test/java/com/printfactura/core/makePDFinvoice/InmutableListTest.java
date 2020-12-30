package com.printfactura.core.makePDFinvoice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InmutableListTest {


    @Test
    public void CheckListIsInmutable(){

        List<String> list = Stream.of("A","B","C").collect(Collectors.toList());
        List<String> inmutable = Collections.unmodifiableList(list);

        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> {
                    inmutable.add("D");
                }
        );

    }

    @Test
    public void CheckListIsNotInmutable(){

        List<String> list = Stream.of("A","B","C").collect(Collectors.toList());

        Assertions.assertTrue(list.add("D"));

    }

    @Test
    public void CheckListIsInmutableFromJava9(){

        // Available from Java 9

        List<String> list = List.of("A","B","C");

        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> {
                    list.add("D");
                }
        );

    }


}
