package com.auth.authsec.ui.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.auth.authsec.domain.exceptions.NoSuchRoleException;
import com.auth.authsec.domain.exceptions.NoSuchUserException;
import com.auth.authsec.domain.exceptions.NullParameterException;
import com.auth.authsec.domain.exceptions.NullResultListException;
import com.auth.authsec.domain.exceptions.InvalidEntityStateException;
import com.auth.authsec.domain.exceptions.UserAlreadyExistsException;

/**
 *
 * @see @ControllerAdvice
 * @see @ExceptionHandler
 *
 * @author Plamen
 *
 */
@ControllerAdvice
public class ExceptionController {

    private static final String MESSAGE = "message";

    private static final String ERROR = "error";

    private static final String NO_SUCH_ROLE = "The Role Doesn't Exist! The two possible roles for this system are: USER & ADMIN.";

    private static final String NO_SUCH_USER = "The User Doesn't Exist! Maybe change the search parameters.";

    private static final String USER_ALREADY_EXISTS = "The User Already Exists! Try another username.";

    private static final String UNSATISFIED_ENTITY_STATE = "The entity cannot be processed, because its state is invalid!"
            + "The Entity cannot be null and cannot have'ROLE_ADMIN' as an authority.";

    private static final String NULL_PARAMETER = "The received parameter has a null value! Method requires a non-null value for correct behavior.";

    private static final String NULL_RESULT_LIST = "No record was found for the specified query! The table may not contain any entries.";

    public ExceptionController() {

        super();

    }

    @ExceptionHandler(NoSuchRoleException.class)
    public ModelAndView noSuchRoleExists() {
        ModelAndView mav = new ModelAndView(ERROR);
        mav.addObject(MESSAGE, NO_SUCH_ROLE);

        return mav;
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ModelAndView noSuchUserExists() {
        ModelAndView mav = new ModelAndView(ERROR);
        mav.addObject(MESSAGE, NO_SUCH_USER);

        return mav;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ModelAndView userAlreadyExists() {
        ModelAndView mav = new ModelAndView(ERROR);
        mav.addObject(MESSAGE, USER_ALREADY_EXISTS);

        return mav;
    }

    @ExceptionHandler(InvalidEntityStateException.class)
    public ModelAndView nnsatisfiedEntityState() {
        ModelAndView mav = new ModelAndView(ERROR);
        mav.addObject(MESSAGE, UNSATISFIED_ENTITY_STATE);

        return mav;
    }

    @ExceptionHandler(NullParameterException.class)
    public ModelAndView nullParameter() {
        ModelAndView mav = new ModelAndView(ERROR);
        mav.addObject(MESSAGE, NULL_PARAMETER);

        return mav;
    }

    @ExceptionHandler(NullResultListException.class)
    public ModelAndView nullResultList() {
        ModelAndView mav = new ModelAndView(ERROR);
        mav.addObject(MESSAGE, NULL_RESULT_LIST);

        return mav;
    }

}
