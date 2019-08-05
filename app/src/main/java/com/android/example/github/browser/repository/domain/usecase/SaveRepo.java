package com.android.example.github.browser.repository.domain.usecase;

import com.android.example.github.browser.UseCase;
import com.android.example.github.browser.repository.data.model.Repository;
import com.android.example.github.browser.repository.data.source.ReposRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

/**
 * Inserts Github repo.
 */
public final class SaveRepo extends UseCase<SaveRepo.RequestValues, Completable> {

    private final ReposRepository mRepository;

    @Inject
    public SaveRepo(ReposRepository reposRepository) {
        mRepository = reposRepository;
    }

    @Override
    protected Completable executeUseCase(RequestValues values) {
        return mRepository.saveRepo(values.getRepository());
    }


    public static final class RequestValues implements UseCase.RequestValues {
        private final Repository mRepo;

        public RequestValues(Repository repo) {
            mRepo = repo;
        }

        public Repository getRepository() {
            return mRepo;
        }
    }
}
