import React, { useState } from 'react';
import "../styles/registrationStyle.css"
import axios from 'axios';
import { useNavigate } from "react-router-dom";

function RegistrationPage(props) {
    const navigate = useNavigate();
    const [client, setClient] = useState({ name: '', surname: '', login: '', password: '', email: '' });
    const [employee, setEmployee] = useState ({ name: '', surname: '', login: '', password: '', position: 'Master', uniqueCode: '' });
    const [clientError, setClientError] = useState('');
    const [employeeError, setEmployeeError] = useState('');

    const handleClientChange = (e) => {
        setClient({ ...client, [e.target.name]: e.target.value });
    }
    const handleEmployeeChange = (e) => {
        setEmployee({...employee, [e.target.name]: e.target.value})
    }
    const handleClientSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(props.url + "/registration/client", client);
            navigate('/')
        } catch (error) {
            setClientError(error.response.data.message);
        } finally {
            setClient({ name: '', surname: '', login: '', password: '', email: '' });
        }
    };
    const handleEmployeeSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(props.url + "/registration/employee", {
                ...employee,
                uniqueCode: parseInt(employee.uniqueCode)
            });
            navigate('/')
        } catch (error) {
            setEmployeeError(error.response.data.message);
        } finally {
            setEmployee({ name: '', surname: '', login: '', password: '', position: 'Master', uniqueCode: '' });
        }
    };
  
    return (
      <div className="container">
        <div className="block">
          <h2>Registration</h2>
          <form onSubmit={handleClientSubmit}>
            <div className="input-group">
              <label htmlFor="name">Name:</label>
              <input type="text" id="name" name="name" value={client.name} onChange={handleClientChange} placeholder="Enter your name" required/>
            </div>
            <div className="input-group">
              <label htmlFor="surname">Surname:</label>
              <input type="text" id="surname" name="surname" value={client.surname} onChange={handleClientChange} placeholder="Enter your surname" required/>
            </div>
            <div className="input-group">
              <label htmlFor="email">Email:</label>
              <input type="text" id="email" name="email" value={client.email} onChange={handleClientChange} placeholder="Enter your email" required/>
            </div>
            <div className="input-group">
              <label htmlFor="login">Login:</label>
              <input type="text" id="login" name="login" value={client.login} onChange={handleClientChange} placeholder="Enter your login" required/>
            </div>
            <div className="input-group">
              <label htmlFor="password">Password:</label>
              <input style={ {width: 383} } type="password" id="password" name="password" value={client.password} onChange={handleClientChange} placeholder="Enter your password" required/>
            </div>
            <button type="submit">Registration</button>
            <p>{clientError}</p>
          </form>
        </div>
        <div className="block">
          <h2>Employee Registration</h2>
          <form onSubmit={handleEmployeeSubmit}>
            <div className="input-group">
              <label htmlFor="name">Name:</label>
              <input type="text" name="name" value={employee.name} onChange={handleEmployeeChange} placeholder="Enter your name" required/>
            </div>
            <div className="input-group">
              <label htmlFor="surname">Surname:</label>
              <input type="text" name="surname" value={employee.surname} onChange={handleEmployeeChange} placeholder="Enter your surname" required/>
            </div>
            <div className="input-group">
              <label htmlFor="loginEmployee">Login:</label>
              <input type="text" id="loginEmployee" name="login" value={employee.login} onChange={handleEmployeeChange} placeholder="Enter your login" required/>
            </div>
            <div className="input-group">
              <label htmlFor="passwordEmployee">Password:</label>
              <input type="password" id="passwordEmployee" name="password" value={employee.password} onChange={handleEmployeeChange} placeholder="Enter your password" required/>
            </div>
            <div className="input-group">
              <label htmlFor="position">Position:</label>
              <select id="position" name="position" value={employee.position} onChange={handleEmployeeChange} required>
                <option value="Master">Master</option>
                <option value="Administrator">Administrator</option>
              </select>
            </div>
            <div className="input-group">
              <label htmlFor="uniqueCode">Unique code:</label>
              <input type="password" id="uniqueCode" name="uniqueCode" value={employee.uniqueCode} onChange={handleEmployeeChange} pattern="\d*" placeholder="Enter your unique code" required/>
            </div>
            <button type="submit">Registration</button>
            <p>{employeeError}</p>
          </form>
        </div>
        <LoginLink/>
      </div>
    );

    function LoginLink() {
        return (
          <div className="registration-link">
            <p>Already have account? <a href="/">Login</a>.</p>
          </div>
        );
      }
}

export default RegistrationPage;
