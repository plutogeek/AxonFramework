/*
 * Copyright (c) 2010-2012. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.axonframework.quickstart.handler;

import org.axonframework.quickstart.api.CreateToDoItemCommand;
import org.axonframework.quickstart.api.MarkCompletedCommand;
import org.axonframework.quickstart.api.ToDoItemCompletedEvent;
import org.axonframework.quickstart.api.ToDoItemCreatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Notice that we need to register the command handlers with the fixture.
 *
 * @author Jettro Coenradie
 */
public class ToDoItemTest {

    private FixtureConfiguration<ToDoItem> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new AggregateTestFixture<>(ToDoItem.class);
        fixture.registerCommandHandler(CreateToDoItemCommand.class,
                                       new CreateToDoCommandHandler(fixture.getRepository()));
        fixture.registerCommandHandler(MarkCompletedCommand.class,
                                       new MarkCompletedCommandHandler(fixture.getRepository()));
    }

    @Test
    public void testCreateToDoItem() throws Exception {
        fixture.given()
               .when(new CreateToDoItemCommand("todo1", "Need to implement the aggregate"))
               .expectEvents(new ToDoItemCreatedEvent("todo1", "Need to implement the aggregate"));
    }

    @Test
    public void testMarkToDoItemAsCompleted() throws Exception {
        fixture.given(new ToDoItemCreatedEvent("todo1", "Need to implement the aggregate"))
               .when(new MarkCompletedCommand("todo1"))
               .expectEvents(new ToDoItemCompletedEvent("todo1"));
    }
}
