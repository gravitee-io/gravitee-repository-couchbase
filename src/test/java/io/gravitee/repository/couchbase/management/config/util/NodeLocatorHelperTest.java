/**
 * Copyright (C) 2015 Couchbase, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALING
 * IN THE SOFTWARE.
 */
package io.gravitee.repository.couchbase.management.config.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.InetAddress;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.couchbase.client.java.util.NodeLocatorHelper;

/**
 * Verifies the functionality of the {@link NodeLocatorHelper}.
 *
 * @author Michael Nitschinger
 * @since 2.1.0
 */
public class NodeLocatorHelperTest extends ClusterDependentTest {

    private NodeLocatorHelper helper;

    @Before
    public void setup() {
        helper = NodeLocatorHelper.create(bucket());
    }

    @Test
    public void shouldListAllNodes() {
        List<InetAddress> expected = bucketManager().info().nodeList();

        assertFalse(helper.nodes().isEmpty());
        assertEquals(expected, helper.nodes());
    }

    @Test
    public void shouldLocateActive() {
        InetAddress node = helper.activeNodeForId("foobar");
        assertTrue(helper.nodes().contains(node));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptHigherReplicaNum() {
        helper.replicaNodeForId("foo", 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptLowerReplicaNum() {
        helper.replicaNodeForId("foo", 0);
    }

}