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
