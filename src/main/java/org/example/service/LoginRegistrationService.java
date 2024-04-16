package org.example.service;

import org.example.dto.*;
import org.example.entity.Account;
import org.example.entity.Client;
import org.example.entity.Employee;
import org.example.repository.AccountRepository;
import org.example.repository.ClientRepository;
import org.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LoginRegistrationService {
    private static final String EMAIL_REGEX = "@[a-z]*\\.";
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public SuccessMessageDTO registrationUser(AccountRegistrationClientDTO clientRegDTO) {
        Account account = accountRepository.findByLogin(clientRegDTO.getLogin());
        if (account != null) {
            return new SuccessMessageDTO(false, "Account already exists");
        }
        Matcher matcher = pattern.matcher(clientRegDTO.getEmail());
        if (!matcher.find()) {
            return new SuccessMessageDTO(false, "Wrong email");
        }
        account = accountRepository.save(new Account(clientRegDTO.getLogin(), clientRegDTO.getPassword(), false));
        clientRepository.save(new Client(clientRegDTO.getName(), clientRegDTO.getSurname(), clientRegDTO.getEmail(), account));
        return new SuccessMessageDTO(true, "");
    }

    public SuccessMessageDTO registrationEmployee(AccountRegistrationEmployeeDTO employeeDTO) {
        Account account = accountRepository.findByLogin(employeeDTO.getLogin());
        if (account != null) {
            return new SuccessMessageDTO(false,  "Account already exists");
        }
        if (employeeDTO.getUniqueCode() != 1234) {
            return new SuccessMessageDTO(false, "Wrong unique code");
        }
        account = accountRepository.save(new Account(employeeDTO.getLogin(), employeeDTO.getPassword(), true));
        employeeRepository.save(new Employee(employeeDTO.getName(), employeeDTO.getSurname(), employeeDTO.getPosition(), account));
        return new SuccessMessageDTO(true, "");
    }

    public SuccessMessageDTO loginUser(AccountClientLoginDTO accountDTO) {
        Account account = accountRepository.findByLogin(accountDTO.getLogin());
        if (clientRepository.findByAccount(account) == null) {
            return new SuccessMessageDTO(false, "Wrong login");
        }
        if (account == null) {
            return new SuccessMessageDTO(false, "User not found");
        }
        if (!accountDTO.getPassword().equals(account.getPassword())) {
            return new SuccessMessageDTO(false, "Wrong password");
        }
        return new SuccessMessageDTO(true, String.valueOf(clientRepository.findByAccount(account).getId()));
    }

    public SuccessMessageEmployeeDTO loginEmployee(AccountEmployeeLoginDTO accountDTO) {
        Account account = accountRepository.findByLogin(accountDTO.getLogin());
        if (account == null) {
            return new SuccessMessageEmployeeDTO(false, "Employee not found");
        }
        if (!accountDTO.getPassword().equals(account.getPassword())) {
            return new SuccessMessageEmployeeDTO(false, "Wrong password");
        }
        Employee employee = employeeRepository.findByAccount(account);
        if (employee == null) {
            return new SuccessMessageEmployeeDTO(false, "Wrong login");
        }
        if (!employee.getPosition().equals(accountDTO.getPosition())) {
            return new SuccessMessageEmployeeDTO(false, "You`re not " + accountDTO.getPosition());
        }
        if (employee.getUniqueCode() != accountDTO.getUniqueCode()) {
            return new SuccessMessageEmployeeDTO(false, "Wrong unique code");
        }
        return new SuccessMessageEmployeeDTO(
                true, "", accountDTO.getPosition(), String.valueOf(employee.getId()));
    }

}
