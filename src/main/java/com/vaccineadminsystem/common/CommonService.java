package com.vaccineadminsystem.common;

import com.vaccineadminsystem.dto.JWTUserDetails;

public interface CommonService {

    JWTUserDetails getCurrentLogin();


    String getCurrentToken();
}
