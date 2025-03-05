package box.pandora.functional_test.rest;

enum InterceptorState {

    REQUEST_STARTED("-->"),
    REQUEST_ENDED("--> END"),
    RESPONSE_STARTED("<--"),
    RESPONSE_ENDED("<-- END");

    private final String identifier;

    InterceptorState(String identifier) {
        this.identifier = identifier;
    }

    public String identifier() {
        return identifier;
    }

}
