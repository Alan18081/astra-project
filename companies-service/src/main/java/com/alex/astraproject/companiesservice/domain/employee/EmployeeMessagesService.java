package com.alex.astraproject.companiesservice.domain.employee;

public interface EmployeeMessagesService {

    void sendCreatedEmployeeEvent(EmployeeEventEntity event);

    void sendUpdatedEmployeeEvent(EmployeeEventEntity event);

    void sendFiredEmployeeEvent(EmployeeEventEntity event);

    void sendChangedEmployeePositionEvent(EmployeeEventEntity event);

}
