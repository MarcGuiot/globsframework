package org.globsframework.metamodel.data;

import org.junit.Test;

public class DataVisitorTest {

    @Test
    public void name() {
        DataVisitor dataVisitor = null;

        dataVisitor
                .pushObject()
                .attribute("toto").push(1)
                .attribute("titi")
                .pushObject()
                .attribute("titi.a").push(3.14)
                .attribute("titi.values").pushArray().push(2.2).push(3.3).endArray()
                .endObject()
                .endObject();



//        .;
    }
}