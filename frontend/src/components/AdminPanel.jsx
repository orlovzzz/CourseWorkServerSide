import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie'

function AdminPanel(props) {
    const [clients, setClients] = useState([]);
    const navigate = useNavigate();
    
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(props.url + '/admin')
                setClients(response.data);
            } catch (error) {
                console.error('Error fetching orders:', error);
            }
        };
        fetchData();
    }, []);

    const handleSubmit = (clientId) => {
        Cookies.set("clientOrdersId", clientId, {expires: 7});
        navigate('/admin/orders');
    }

  return (
    <div>
      <header>
        <div>
          <h1 style={{paddingTop: 10 }}>Admin Panel</h1>
            <nav>
                <a style={ {left: 100} } href='/'>Logout</a>
            </nav>
        </div>
      </header>

      {clients.map(client => (
        <div className="client-block" key={client.id}>
          <div className="client-details">
            <h2>Client ID: {client.id}</h2>
            <p>Name: {client.name}</p>
            <p>Surname: {client.surname}</p>
            <div className="input-group">
                <form onSubmit={(e) => {
                  e.preventDefault();
                  handleSubmit(client.id);
                  }}>
                  <button type="submit" className="show-orders-button">Show orders</button>
                </form>
              </div>
          </div>
        </div>
      ))}
    </div>
  );
}

export default AdminPanel;
