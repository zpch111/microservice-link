package pers.zpc.ml.thread;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import pers.zpc.ml.entity.LogUnitEntity;
import pers.zpc.ml.util.MDCUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author zpc
 * extend the executor to trans the logUnit.
 * @param <T>
 */
public class ExtendExecutor<T> extends ThreadPoolTaskExecutor {

    @Override
    public void execute(Runnable command) {
        super.execute(initRunable(command));
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        super.execute(initRunable(task), startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(initRunable(task));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(initCallable(task));
    }

    private class ExtendRunnable implements Runnable {
        private LogUnitEntity logUnit;
        private Runnable runnable;

        public ExtendRunnable(LogUnitEntity logUnit, Runnable runnable) {
            this.logUnit = logUnit;
            this.runnable = runnable;
        }

        @Override
        public void run() {
            MDCUtils.init(logUnit);
            runnable.run();
            MDCUtils.destroy();
        }
    }

    private class ExtendCallable<V> implements Callable<V> {
        private LogUnitEntity logUnit;
        private Callable<V> callable;

        public ExtendCallable(LogUnitEntity logUnit, Callable<V> callable) {
            this.logUnit = logUnit;
            this.callable = callable;
        }

        @Override
        public V call() throws Exception {
            MDCUtils.init(logUnit);
            V result = null;
            try{
                 result = callable.call();
            }
            finally {
                MDCUtils.destroy();
            }
            return result;
        }
    }

    private ExtendRunnable initRunable(Runnable command) {
        return new ExtendRunnable(MDCUtils.generator(), command);
    }

    private Callable initCallable(Callable command) {
        return new ExtendCallable(MDCUtils.generator(), command);
    }
}
