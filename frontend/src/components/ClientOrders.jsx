import React from 'react';
import { useState, useEffect } from 'react';
import '../styles/clientOrdersStyle.css'
import axios from 'axios';
import Cookies from 'js-cookie'

function ClientOrdersPage(props) {
    const [orders, setOrders] = useState([{ id: '', orderDescription: '', employees: { id: '', name: '', surname: '' }, orderPrice: '', orderState: '' }])

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(props.url + '/client', {
                    params: {
                        id: Cookies.get('clientId')
                    }
                });
                setOrders(response.data);
            } catch (error) {
                console.error('Error fetching orders:', error);
            }
        };

        fetchData();
    }, []);

    return (
        <div>
            <header>
                <div>
                    <h1>All Orders</h1>
                    <nav>
                        <a href="/doOrder">Create Order</a>
                    </nav>
                </div>
            </header>

            <div className="container">
                {orders && orders.map(order => (
                    <div key={order.id} className="order-block">
                        <div className="order-details">
                            <h2>Order {order.id}</h2>
                            <label>Order Information:</label>
                            <p>{order.orderDescription}</p><br />
                            <label>Master ID:</label>
                            <p>{order.employees ? order.employees.id : 'Not indicated'}</p><br />
                            <label>Master name:</label>
                            <p>{order.employees ? order.employees.name : 'Not indicated'}</p><br />
                            <label>Master surname:</label>
                            <p>{order.employees ? order.employees.surname : 'Not indicated'}</p><br />
                            <label>Order Price:</label>
                            <p>{order.orderPrice}</p><br />
                            <label>Order State:</label>
                            <p>{order.orderState}</p>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default ClientOrdersPage;
