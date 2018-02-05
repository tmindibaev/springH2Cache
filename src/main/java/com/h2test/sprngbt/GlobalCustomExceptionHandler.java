package com.h2test.sprngbt;

import com.h2test.sprngbt.exceptions.NoSuchUserException;
import com.h2test.sprngbt.service.DisplayErrorView;
import com.h2test.sprngbt.service.DisplayErrorViewImpl;
import com.h2test.sprngbt.service.ErrorCode;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalCustomExceptionHandler extends ResponseEntityExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String DEFAULT_ERROR_VIEW = "error";

    /*@ExceptionHandler(value = Exception.class)
    public ModelAndView
    defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        if (AnnotationUtils.findAnnotation
                (e.getClass(), ResponseStatus.class) != null)
            throw e;

        // Otherwise setup and send the user to a default error-view.
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }*/
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }

        DisplayErrorViewImpl errorView =
                new DisplayErrorViewImpl(HttpStatus.BAD_REQUEST, ErrorCode.CONSTRAINT_VIOLATION, errors.toString());
        return new ResponseEntity<Object>(errorView, new HttpHeaders(), errorView.getHttpStatus());
    }

    @ExceptionHandler({NoSuchUserException.class})
    public ResponseEntity<Object> handleNoSuchUserException() {
        logger.info("messageHandler: NoSuchUserException");
        DisplayErrorViewImpl errorView =
                new DisplayErrorViewImpl(HttpStatus.NOT_FOUND, "", ErrorCode.NO_SUCH_USER, "userId not found");// ex.getLocalizedMessage());
        return new ResponseEntity<Object>(errorView, new HttpHeaders(), errorView.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        logger.info("messageHandler: MissingServletRequestParameter");
        String error = ex.getParameterName() + " parameter is missing";

        DisplayErrorViewImpl errorView =
                new DisplayErrorViewImpl(HttpStatus.BAD_REQUEST, "", ErrorCode.MISSING_REQUEST_PARAMETER, ex.getLocalizedMessage());
        return new ResponseEntity<Object>(errorView, new HttpHeaders(), errorView.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info("messageHandler: NoHandlerFoundException");
        String error = ex.getMessage();

        DisplayErrorViewImpl errorView =
                new DisplayErrorViewImpl(HttpStatus.NOT_FOUND, "", ErrorCode.MISSING_REQUEST_PARAMETER, ex.getLocalizedMessage() + error);
        return new ResponseEntity<Object>(errorView, new HttpHeaders(), errorView.getHttpStatus());
    }
}
