import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginPage from "./components/LoginPage"
import RegistrationPage from './components/RegistrationPage';
import DoOrderPage from './components/DoOrderPage';
import ClientOrdersPage from './components/ClientOrders';
import MasterPage from './components/MasterPage';
import MasterOrder from './components/MasterOrder';
import AdminPanel from './components/AdminPanel';
import AdminOrders from './components/AdminOrders';

function App() {
  const apiUrl = 'http://server:8080';
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<LoginPage url={apiUrl}/>}></Route>
          <Route path="/registration" element={<RegistrationPage url={apiUrl}/>}></Route>
          <Route path='/doOrder' element={<DoOrderPage url={apiUrl}/>}></Route>
          <Route path='/orders' element={<ClientOrdersPage url={apiUrl}/>}></Route>
          <Route path='/master' element={<MasterPage url={apiUrl}/>}></Route>
          <Route path='/master/myOrders' element={<MasterOrder url={apiUrl}/>}></Route>
          <Route path='/admin' element={<AdminPanel url={apiUrl}/>}></Route>
          <Route path='/admin/orders' element={<AdminOrders url={apiUrl}/>}></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;