import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie'

function AdminOrders(props) {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
        try {
            const response = await axios.get(props.url + '/getOrders', {
              params: { clientId: Cookies.get('clientOrdersId') }
            })
            setOrders(response.data);
        } catch (error) {
            console.error('Error fetching orders:', error);
        }
    };
    fetchData();
}, []);

  const handleSubmit = async (orderId) => {
      try {
        const response = axios.post(props.url + '/getOrders', null, {
          params: {
            orderId: orderId,
            adminId: Cookies.get("adminId")
          }
        });
        setOrders(orders => orders.filter(order => order.id != orderId))
      } catch (error) {
        console.log(error);
      }
  }

  return (
    <div>
      <header>
        <div>
          <h1>Admin Panel</h1>
          <nav>
                <a style={ {left: 100} } href='/admin'>Clients</a>
            </nav>
        </div>
      </header>

      <div className="container">
        {orders.map(order => (
          <div className="order-block" key={order.id}>
            <div className="order-details">
              <h2>Order {order.id}</h2>
              <label>Client ID</label>
              <p>{order.clientId}</p>
              <br />
              <label>Master ID</label>
              <p>{order.employeeId !== 0 ? order.employeeId : 'Not indicated'}</p>
              <br />
              <label>Order Information:</label>
              <p>{order.orderDescription}</p>
              <br />
              <label>Order Price:</label>
              <p>{order.orderPrice !== 0 ? order.orderPrice : 'Not indicated'}</p>
              <br />
              <label>Order State:</label>
              <p>{order.orderState}</p>
              <br />
              <div className="input-group">
                <form onSubmit={(e) => {
                  e.preventDefault();
                  handleSubmit(order.id);
                  }}>
                  <button type="submit" className="show-orders-button">Delete order</button>
                </form>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default AdminOrders;
