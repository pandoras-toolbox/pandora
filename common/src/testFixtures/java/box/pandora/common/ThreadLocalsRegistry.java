package box.pandora.common;

import java.util.ArrayList;
import java.util.List;

public enum ThreadLocalsRegistry {

    INSTANCE;

    private final List<ThreadLocal<?>> threadLocals = new ArrayList<>();

    /**
     * Registers {@link ThreadLocal} objects in use so that their values can be removed via the {@link #remove()} method.
     *
     * <p>To avoid memory leaks and reduce contention this method should only be called when
     * setting static final locations (for example finals in enums or static final fields).
     *
     * @param threadLocal ThreadLocal to register
     * @return the input ThreadLocal
     */
    public <T> ThreadLocal<T> register(ThreadLocal<T> threadLocal) {
        threadLocals.add(threadLocal);
        return threadLocal;
    }

    public void remove() {
        for (var threadLocal : threadLocals) {
            threadLocal.remove();
        }
    }

}
