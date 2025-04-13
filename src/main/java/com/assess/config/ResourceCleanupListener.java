package com.assess.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

/**
 * @Creator 4/13/2025
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public class ResourceCleanupListener  implements StepExecutionListener {

    private final EntityManagerFactory entityManagerFactory;

    public ResourceCleanupListener(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        EntityManager entityManager = EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory);
        if (entityManager != null) {
            entityManager.clear();
        }

        // Additional cleanup if needed
        return stepExecution.getExitStatus();
    }
}
