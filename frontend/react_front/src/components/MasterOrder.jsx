import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie'
import '../styles/masterOrderStyle.css'

function MasterOrder(props) {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
        try {
            const response = await axios.get(props.url + '/master/orders', {
                params: {
                    masterId: Cookies.get('masterId')
                }
            });
            setOrders(response.data);
        } catch (error) {
            console.error('Error fetching orders:', error);
        }
    };
    fetchData();
}, []);

    const handleOrderReady = async (orderId) => {
        try {
            const response = await axios.post(props.url + '/master/orders', null, {
                params: {
                    orderId: orderId
                }
            });
            setOrders(orders => orders.filter(order => order.id !== orderId));
        } catch (error) {
            console.log(error);
        }
    };

  return (
    <div>
      <header>
        <div>
          <h1>Master Orders</h1>
          <nav>
            <a href="/master">All Orders</a>
          </nav>
        </div>
      </header>

      <div className="client-block">
        {orders.map(order => (
          <div className="client-details" key={order.id}>
            <h2>Order ID: {order.id}</h2>
            <p>Client ID: {order.client.id}</p>
            <p>Name: {order.client.name}</p>
            <p>Surname: {order.client.surname}</p>
            <p>Order info: {order.orderDescription}</p>
            <p>Order state: {order.state}</p>
            <div className="input-group">
              <button
                className="show-orders-button"
                onClick={() => handleOrderReady(order.id)}>
                Order is ready
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default MasterOrder;