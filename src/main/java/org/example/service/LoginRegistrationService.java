package org.example.service;

import org.example.entity.Account;
import org.example.entity.Client;
import org.example.entity.Employee;
import org.example.entity.Success;
import org.example.repository.AccountRepository;
import org.example.repository.ClientRepository;
import org.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginRegistrationService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public String registrationUser(Client client, Account account) {
        if (accountRepository.findByLogin(account.getLogin()) != null) {
            return "Account with login " + account.getLogin() + " already exists";
        }
        client.setAccount(account);
        accountRepository.save(account);
        clientRepository.save(client);
        return null;
    }

    public String registrationEmployee(Employee employee, Account account, String employeePassword) {
        if (accountRepository.findByLogin(account.getLogin()) != null) {
            return "Account with login " + account.getLogin() + " already exists";
        }
        if (!employeePassword.equals("1234")) {
            return "Wrong employee`s password";
        }
        employee.setAccount(account);
        accountRepository.save(account);
        employeeRepository.save(employee);
        return null;
    }

    public Success loginUser(Account account) {
        if (accountRepository.findByLogin(account.getLogin()) == null) {
            return new Success(false, "Account with login " + account.getLogin() + " not found");
        }
        if (!account.getPassword().equals(accountRepository.findByLogin(account.getLogin()).getPassword())) {
            return new Success(false, "Wrong password");
        }
        account = accountRepository.findByLogin(account.getLogin());
        return new Success(true, Integer.toString(clientRepository.findByAccount(account).getId()));
    }

    public Success loginEmployee(Account account, int uniqueCode, String position) {
        if (accountRepository.findByLogin(account.getLogin()) == null) {
            return new Success(false, "Account with login " + account.getLogin() + " not found");
        }
        if (!account.getPassword().equals(accountRepository.findByLogin(account.getLogin()).getPassword())) {
            return new Success(false, "Wrong password");
        }
        account = accountRepository.findByLogin(account.getLogin());
        Employee employee = employeeRepository.findByAccount(account);
        if (employee.getUniqueCode() != uniqueCode) {
            return new Success(false, "Wrong unique code");
        }
        if (!employee.getPosition().equals(position)) {
            return new Success(false, "You`re not " + position);
        }
        return new Success(true, Integer.toString(employee.getId()));
    }

}
