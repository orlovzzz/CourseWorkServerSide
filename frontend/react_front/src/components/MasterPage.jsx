import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie'
import "../styles/allOrderStyle.css"

function MasterPage(props) {
    const [orders, setOrders] = useState([{ id: '', orderDescription: '', client: { id: '', name: '', surname: '' }}])

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(props.url + '/master');
                setOrders(response.data);
            } catch (error) {
                console.error('Error fetching orders:', error);
            }
        };
        fetchData();
    }, []);

    const handleSubmit = async (orderId, price) => {
        try {
            const response = await axios.post(props.url + "/master", 
            { 
                masterId: Cookies.get("masterId"),
                price: price,
                orderId: orderId
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
          <h1>Master Page</h1>
          <nav>
            <a href="/master/myOrders">My Orders</a>
            <a style={ {left: 100} } href='/'>Logout</a>
          </nav>
        </div>
      </header>
      {orders && orders.map(order => (
        <div key={order.id} className="client-block">
          <div className="client-details">
            <h2>Order ID: {order.id}</h2>
            <label>Client ID:</label>
            <p>{order.client.id}</p><br />
            <label>Name:</label>
            <p>{order.client.name}</p><br />
            <label>Surname:</label>
            <p>{order.client.surname}</p><br />
            <label>Order description:</label>
            <p>{order.orderDescription}</p><br />
            <div className="input-group">
              <form onSubmit={(e) => {
                e.preventDefault();
                const price = e.target.elements.price.value;
                handleSubmit(order.id, price);
                }}>
                <input type="hidden" name="orderId" value={order.id} />
                <label>Order price:</label>
                <input type="text" name="price" placeholder="Enter price" required /><br />
                <button type="submit" className="show-orders-button">Enter price and add to My Orders</button>
              </form>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};

export default MasterPage;
