package com.android.example.github.browser;

import javax.inject.Inject;

/**
 * Runs {@link UseCase}s.
 */
public final class UseCaseHandler {

    @Inject
    public UseCaseHandler() { }

    public <T extends UseCase.RequestValues, V> V execute(UseCase<T, V> useCase, T values) {
        useCase.setRequestValues(values);
        return useCase.run();
    }
}
