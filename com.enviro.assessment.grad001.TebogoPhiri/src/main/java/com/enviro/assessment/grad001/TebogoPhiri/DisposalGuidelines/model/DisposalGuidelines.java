package com.enviro.assessment.grad001.TebogoPhiri.DisposalGuidelines.model;

import java.util.List;
public record DisposalGuidelines (List<DisposalGuideline> disposal) {
    /*Record is immuntable(unchangable)
     * Record has its own toString, hasCode, equals, getters (no setters)
     * Record Automatically generates a constructor
     * Record used for modeling data-centric objects (data containers)
     * Record fields are final and cannot be reassigned once initialized.
     * Record encourages immutability for thread safety and cleaner design.
     * Record cannot extend other classes (i.e., they are final by design).
     * Record can implement interfaces, which allows for some degree of polymorphism.
     * Record performance is similar to classes.
     * Record due to immutability and auto-generated equals()/hashCode(), they might be slightly more efficient in contexts like hash-based collections.
     * Use records when you want a data-centric, immutable structure with minimal boilerplate.
     * Use classes when you need full flexibility, complex behavior, or mutability.
     * */

    public DisposalGuidelines {
        if (disposal == null) {
            throw new IllegalArgumentException("Disposal guidelines list cannot be null");
        }
    }
}