package com.vaccineadminsystem.util;

public class ErrorMess {

    private ErrorMess() {

    }
    public static final String INSERT_SUCCESS = "Insert Success";
    public static final String UPDATE_SUCCESS = "Update Success";
    public static final String NOT_PROVIDE_EMP_ID = "You must provide employee id";
    public static final String EXIST_EMPLOYEE = "The Employee with id is exist";
    public static final String CREATE_SUCCESS_NEWS = "Success!";
    public static final String PROVIDE_DATA = "Make sure you are provide data";
    public static final String NOT_FOUND = "Your content not found";

    public static final String VACCINE_TYPE_VALIDATE = "Vaccine Type is not valid";
    public static final String VACCINE_TYPE_NO_HAVE_RECORD = "Vaccine Type List don't have any record";
    public static final String VACCINE_TYPE_EXIST = "Vaccine Type Code is exist";
    public static final String SAVE_VACCINE_TYPE_INACTIVE = "Can't not save vaccine status In-Active";
    public static final String VACCINE_TYPE_NOT_EXIST = "Vaccine Type Code is not exist to update";
    public static final String VACCINE_TYPE_NOT_FOUND = "Vaccine Type is not found";
    public static final String IMAGE_NOT_FOUND = "Image you see not found";
    public static final String INVALID_AGE = "Birth you provide not valid";
    public static final String INVALID_CREDENTIAL = "Invalid Credentials";
    public static final String INVALID_NEWS = "Invalid News";
    public static final String NOT_FOUND_NEWS = "Not found News";
    public static final String JWT_PROVIDE = "You would need to provide the Jwt Token to Access This resource";

    public static final String NOT_FOUND_VACCINE = "Not found vaccine!";
    public static final String FAIL_REQUIREMENT = "Vaccine data is invalid";
    public static final String EXISTED_VACCINE = "Vaccine already existed";
    public static final String FAIL_SYSTEM = "Fail";
    public static final String INVALID_IMAGE = "You must provide image";
    public static final String INVALID_FILE_EXTENSION = "Cannot read file, check your file you provide";
    public static final String FAIL_IMPORT_EXCEL_FILE = "Please import excel file";
    public static final String UPDATE_FAIL = "Update fail";

    public static final String ACCESS_DENIED = "You no have to access this function";
    public static final String EMPLOYEE_CONSTRAINT = "The employee reference with other";
    public static final String NEWS_CONSTRAINT = "The employee reference with other";
}
