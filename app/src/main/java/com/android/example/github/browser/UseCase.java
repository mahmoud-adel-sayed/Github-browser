package com.android.example.github.browser;

/**
 * Use cases are the entry points to the domain layer.
 *
 * @param <R> the request type
 * @param <T> the return type of run method
 */
public abstract class UseCase<R extends UseCase.RequestValues, T> {

    private R mRequestValues;

    public void setRequestValues(R requestValues) {
        mRequestValues = requestValues;
    }

    public R getRequestValues() {
        return mRequestValues;
    }

    public T run() {
        return executeUseCase(mRequestValues);
    }

    protected abstract T executeUseCase(R requestValues);

    /**
     * Data passed to a request.
     */
    public interface RequestValues { }
}
