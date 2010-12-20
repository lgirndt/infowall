/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package infowall.testing;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;

/**
 *
 */
public class Mocks {

    private static class ThreadLocalMocksControl extends ThreadLocal<IMocksControl> {
        @Override
        protected IMocksControl initialValue() {
            return EasyMock.createControl();
        }
    }

    private final ThreadLocalMocksControl threadLocalMocksControl = new ThreadLocalMocksControl();

    public synchronized void cleanup() {
        threadLocalMocksControl.remove();
    }

    private synchronized IMocksControl getMocksControl() {
        return threadLocalMocksControl.get();
    }

    public <T> T createMock(final Class<T> mockClass) {
        return getMocksControl().createMock(mockClass);
    }

    public void replayAll() {
        getMocksControl().replay();
    }

    public void verifyAll() {
        final IMocksControl control = getMocksControl();

        control.verify();
        control.reset();
    }
}
