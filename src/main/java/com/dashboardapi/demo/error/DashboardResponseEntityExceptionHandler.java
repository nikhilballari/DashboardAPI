package com.dashboardapi.demo.error;

import com.dashboardapi.demo.entity.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@ResponseStatus
public class DashboardResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExistingDashboardTitleException.class)
    public ResponseEntity<ErrorMessage> existingDashboardTitleException(ExistingDashboardTitleException existingDashboardTitleException) {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT,
                                                    existingDashboardTitleException.getMessage(),
                                                    existingDashboardTitleException.getCause());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(errorMessage);
    }
}
