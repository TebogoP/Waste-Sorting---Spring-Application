package com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.ExceptionHandling;

/**
 * Custom exception class for handling errors related to WasteCategory operations.
 */
public class WasteCategoryException extends RuntimeException {

    private final String errorCode;

    /**
     * Constructor for WasteCategoryException with an error message and error code.
     * @param errorCode The error code for the exception.
     * @param message   The detailed error message.
     */
    public WasteCategoryException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Constructor for WasteCategoryException with an error message, error code, and a cause.
     * @param errorCode The error code for the exception.
     * @param message   The detailed error message.
     * @param cause     The underlying cause of the exception.
     */
    public WasteCategoryException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Get the error code associated with the exception.
     * @return The error code.
     */
    public String getErrorCode() {
        return errorCode;
    }
}
