package com.qiren.project.ui.core;

import com.qiren.project.exceptions.PageNotFoundException;
import com.qiren.project.ui.*;

/**
 * @see PageRequest
 * @see PageControlCenter
 */
public class PageBuilder {
    public static AbstractPage createPage(String pageName)
            throws PageNotFoundException{
        return switch (pageName) {
            case PageRequest.MAIN_PAGE -> new MainPage();
            case PageRequest.LOGIN_PAGE -> new LoginPage();
            case PageRequest.ACCOUNT_PAGE -> new AccountPage();
            case PageRequest.STAFF_COURSE_PAGE -> new StaffCoursePage();
            case PageRequest.STUDENT_COURSE_PAGE -> new StudentCoursePage();
            case PageRequest.ADD_COURSE_PAGE -> new AddCoursePage();
            case PageRequest.VIEW_COURSE_PAGE -> new ViewCoursePage();
            case PageRequest.ADD_PLAN_PAGE -> new AddCoursePlanPage();
            case PageRequest.VIEW_PLAN_PAGE -> new ViewCoursePlanPage();
            case PageRequest.REGIS_PLAN_PAGE -> new RegistrationPage();
            case PageRequest.VIEW_SYLLABUS_PAGE -> new SyllabusPage();
            case PageRequest.VIEW_SCHEDULE_PAGE -> new SchedulePage2();
            case PageRequest.VIEW_MESSAGE_PAGE -> new MessagePage();
            case PageRequest.VIEW_GRADE_PAGE -> new ViewGradePage();
            default -> throw new PageNotFoundException(pageName);
        };
    }
}
