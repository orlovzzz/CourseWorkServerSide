import React, { useState } from 'react';
import "../styles/loginStyle.css";
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import Cookies from 'js-cookie';

function LoginPage(props) {
  const navigate = useNavigate();
    const [client, setClient] = useState({ login: '', password: '' });
    const [employee, setEmployee] = useState ({ login: '', password: '', position: 'Master', uniqueCode: '' });
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
            const response = await axios.post(props.url + "/login/client", client);
            Cookies.set('clientId', response.data.message, {expires: 7});
            navigate('/doOrder')
        } catch (error) {
            setClientError(error.response.data.message);
        } finally {
            setClient({ login: '', password: '' });
        }
    };
    const handleEmployeeSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(props.url + "/login/employee", {
                ...employee,
                uniqueCode: parseInt(employee.uniqueCode)
            });
            if (response.data.position === 'Master') {
              Cookies.set('masterId', response.data.masterId);
              navigate('/master');
            }
            if (employee.position === 'Administrator') {
              Cookies.set('adminId', response.data.masterId);
              navigate('/admin');
            }
        } catch (error) {
            setEmployeeError(error.response.data.message);
        } finally {
            setEmployee({ login: '', password: '', position: 'Master', uniqueCode: '' });
        }
    };
  
    return (
      <div className="container">
        <div className="block">
          <h2>Login</h2>
          <form onSubmit={handleClientSubmit}>
            <div className="input-group">
              <label htmlFor="login">Login:</label>
              <input style={ {width: 380} } type="text" id="login" name="login" value={client.login} onChange={handleClientChange} placeholder="Enter your login" required/>
            </div>
            <div className="input-group">
              <label htmlFor="password">Password:</label>
              <input style={ {width: 380} } type="password" id="password" name="password" value={client.password} onChange={handleClientChange} placeholder="Enter your password" required/>
            </div>
            <button type="submit">Login</button>
            <p>{clientError}</p>
          </form>
        </div>
        <div className="block">
          <h2>Employee Login</h2>
          <form onSubmit={handleEmployeeSubmit}>
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
            <button type="submit">Login</button>
            <p>{employeeError}</p>
          </form>
        </div>
        <RegistrationLink/>
      </div>
    );


  function RegistrationLink() {
    return (
      <div className="registration-link">
        <p>Don't have an account? <a href="/registration">Registration</a>.</p>
      </div>
    );
  }
}

export default LoginPage;
