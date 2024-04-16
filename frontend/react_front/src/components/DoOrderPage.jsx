import React, { useState } from 'react';
import "../styles/doOrderStyle.css"
import axios from 'axios';
import Cookies from 'js-cookie';

function DoOrderPage(props) {
    const [orderDescription, setOrderDescription] = useState('');
    const [clientId, setClientId] = useState('');
    const [createOrder, setCreateOrder] = useState('');
    const [message, setMessage] = useState('');

    const handleInputChange = (e) => {
        setOrderDescription(e.target.value);
        setClientId(Cookies.get('clientId'));
    };

    const handleOrderSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(props.url + "/client", { clientId: clientId, orderDescription: orderDescription });
            setCreateOrder(response.data.message);
        } catch (error) {
            console.log(error);
        } finally {
            setOrderDescription('');
        }
    };

    return (
        <div className="body">
            <header>
                <div>
                    <h1 id="jewelry">Jewelry</h1>
                    <nav>
                        <a href="/orders">All Orders</a>
                        <a style={ {left: 100} } href='/'>Logout</a>
                    </nav>
                </div>
            </header>

            <div>
                <div className="new-order">
                    <h2>New Order</h2>
                    <form onSubmit={handleOrderSubmit}>
                        <label htmlFor="orderDescription">Order Information:</label>
                        <input type="text" id="orderInfo" name="orderDescription" placeholder="Enter order information" value={orderDescription} onChange={handleInputChange} required/><br></br>
                        <button type="submit">Create Order</button>
                    </form>
                    <h1>{createOrder}</h1>
                </div>
            </div>
        </div>
    );
}

export default DoOrderPage;
