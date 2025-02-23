package org.shop.exception;

/**
 * Custom exception thrown when a requested resource is not found in the system.
 * This is a runtime exception, meaning it does not need to be explicitly declared in method signatures.
 */
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -3592608280861922762L; // Ensures version compatibility

    private final String resourceName; // e.g., "Product", "User"
    private final String fieldName;    // e.g., "id", "email"
    private final Object fieldValue;   // e.g., 123, "user@shop.com"

    /**
     * Constructs a new exception with details about the missing resource.
     *
     * @param resourceName Name of the resource (e.g., "Product").
     * @param fieldName    Name of the field used to search for the resource (e.g., "id").
     * @param fieldValue   Value of the field that caused the resource not to be found.
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format(
                "%s not found with %s: %s",
                resourceName, fieldName, fieldValue
        ));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    // Getters (optional, useful for error response customization)
    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}