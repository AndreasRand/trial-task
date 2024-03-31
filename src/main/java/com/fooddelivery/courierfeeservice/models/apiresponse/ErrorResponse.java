package com.fooddelivery.courierfeeservice.models.apiresponse;

public class ErrorResponse {
    private Error error;

    public ErrorResponse(String message, String type) {
        this.error = new Error();
        this.error.message = message;
        this.error.type = type;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public static class Error {
        private String message;
        private String type;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
