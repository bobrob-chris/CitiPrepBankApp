import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import './App.css'

function App() {
  const [count, setCount] = useState(0)
  const [customers, setCustomers] = useState([])
  const [accounts, setAccounts] = useState([])
  const [selectedCustomer, setSelectedCustomer] = useState(null)

  const [loadingCustomers, setLoadingCustomers] = useState(false)
  const [loadError, setLoadError] = useState(null)

  const [customerName, setCustomerName] = useState('')
  const [customerEmail, setCustomerEmail] = useState('')
  const [customerId, setCustomerId] = useState('')

  
  async function loadCustomers() {
    setLoadError(null)
    setLoadingCustomers(true)
    try {
      console.log('Loading customers from API...')
      const response = await fetch('http://localhost:8080/api/customers')
      if (!response.ok) {
        const txt = await response.text().catch(() => '')
        const msg = `Server responded ${response.status} ${response.statusText} ${txt ? '- ' + txt : ''}`
        console.error(msg)
        setLoadError(msg)
        return
      }
      const data = await response.json()
      setCustomers(data)
    } catch (error) {
      console.error('Error loading customers:', error)
      setLoadError(error?.message || String(error))
    } finally {
      setLoadingCustomers(false)
    }
  }

  


  return (
    <>
      <section id="center">
        <div>
          <h1>Zork Bank</h1>
          <p>
            Welcome to Zork Bank. Site under construction, please enter at your own risk.
            </p>
        </div>
        <div>
          <h2>Customers</h2>
          <p>Select a customer to view account details.</p>

          <button
            type="button"
            onClick={loadCustomers}
            disabled={loadingCustomers}
          >
            {loadingCustomers ? 'Loading...' : 'Refresh Customers'}
          </button>
          {loadError && (
            <p className="error" role="alert">Error: {loadError}</p>
          )}
          <button 
            type="button">
              Create Customer
          </button>

          <button 
            type="button">
              Edit Customer
          </button>

          <button 
            type="button">
              Create Account
          </button>

          <button 
            type="button">
              Edit Account
          </button>
          
        </div>


        <div className="customer-grid">
          {customers.length === 0 ? (
            <p>No customers found.</p>
          ) : (
            customers.map((customer) => (
              <div
                key={customer.id}
                className="customer-card"
                onClick={() => setSelectedCustomer(customer)}
              >
                <h3>{customer.name}</h3>

                <p>
                  <strong>ID:</strong> {customer.id}
                </p>

                <p>
                  <strong>Email:</strong> {customer.email}
                </p>

                <p>
                  <strong>Accounts:</strong>{' '}
                  {customer.accounts?.length ?? 0}
                </p>
              </div>
            ))
          )}
        </div>
        


        
      </section>

      
      {selectedCustomer && (
      <section className="detail-panel">
        <h2>{selectedCustomer.name} - {selectedCustomer.id}</h2>

        <p>{selectedCustomer.email}</p>

        <table>
          <thead>
            <tr>
              <th>Account Number</th>
              <th>Type</th>
              <th>Balance</th>
            </tr>
          </thead>

          <tbody>
            {selectedCustomer.accounts?.map(account => (
              <tr key={account.id}>
                <td>{account.accountNumber}</td>
                <td>{account.accountType}</td>
                <td>
                  $
                  {account.balance.toLocaleString()}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>
      )}

      
      

    </>
  )
}

export default App
