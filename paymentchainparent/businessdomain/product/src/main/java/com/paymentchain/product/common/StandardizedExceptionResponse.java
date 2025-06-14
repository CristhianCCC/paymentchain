package com.paymentchain.product.common;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "this is used to return errors in RFC 7807 which created a generalized error handling schema composed by 5 parts")
public class StandardizedExceptionResponse {

    @Schema(description = "the unique uri identifier that categorizes the error", name="type",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "/error/authentication/not-authorized")
    private String type;

    @Schema(description = "A brief human-readable message about the error", name="title",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "the user doesn't have authorization")
    private String title;

    @Schema(description = "The unique error code", name="code", requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            example = "192")
    private String code;

    @Schema(description = "A human readable explanation of the error", name = "detail",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "the user doesn't have permissions please contact admin")
    private String detail;

    @Schema(description = "A URI that identifies the error", name="instance", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "/errors/authentication/not-authorized/01")
    private String instance;

    public StandardizedExceptionResponse(String code, String detail, String instance, String title, String type) {
        super();
        this.code = code;
        this.detail = detail;
        this.instance = instance;
        this.title = title;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}




