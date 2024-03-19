package logic;

/**
 * OrderType enum represents the order types that are available in the park.
 */
public enum OrderType {
    SOLO,   // Represents a solo visit.
    FAMILY, // Represents a family visit.
    GROUP;  // Represents a group visit.

    /**
     * Overrides the toString method to provide custom string representations for each enum value.
     * @return A string representation of the enum value.
     */
    @Override
    public String toString() {
        switch (this) {
            case SOLO:
                return "Solo Visit";
            case FAMILY:
                return "Family visit";
            case GROUP:
                return "Group Visit";
            default:
                throw new IllegalArgumentException();
        }
    }
}
