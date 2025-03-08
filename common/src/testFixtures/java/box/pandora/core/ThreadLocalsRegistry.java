package box.pandora.core;

import java.util.ArrayList;
import java.util.List;

// Prevent SonarQube warning: "ThreadLocal" variables should be cleaned up when no longer used
@SuppressWarnings("java:S5164")
public final class ThreadLocalsRegistry {

    private static final List<ThreadLocal<?>> THREAD_LOCALS = new ArrayList<>();

    private ThreadLocalsRegistry() {
    }

    /**
     * Registers {@link ThreadLocal} objects in use so that their values can be removed via the {@link #remove()} method.
     *
     * <p>To avoid memory leaks and reduce contention this method should only be called when
     * setting static final locations (for example finals in enums or static final fields).
     *
     * @param threadLocal ThreadLocal to register
     * @return the input ThreadLocal
     */
    public static synchronized <T> ThreadLocal<T> register(ThreadLocal<T> threadLocal) {
        THREAD_LOCALS.add(threadLocal);
        return threadLocal;
    }

    public static synchronized void remove() {
        for (var threadLocal : THREAD_LOCALS) {
            threadLocal.remove();
        }
    }

}
