import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import './App.css'

function App() {
  const [count, setCount] = useState(0)
  const [customers, setCustomers] = useState([{"id":1,"name":"john","email":"none","accounts":[]},
  {"id":2,"name":"jane","email":"janedoe@gmail.com","accounts":[]}])
  const [accounts, setAccounts] = useState([])
  const [selectedCustomer, setSelectedCustomer] = useState(null)

  const [customerName, setCustomerName] = useState('')
  const [customerEmail, setCustomerEmail] = useState('')
  const [customerId, setCustomerId] = useState('')

  
  async function loadCustomers() {
    try {
      const response = await fetch(
        'http://localhost:8080/api/customers'
      )
      const data = await response.json()
      
      setCustomers(data)
    } catch (error) {
      console.error('Error loading customers:', error)
    }
    

  }

  


  return (
    <>
      <section id="center">
        <div>
          <h1>Zork Bank</h1>
          <p>
            Under construction. Please check back later for updates on our progress. We
          </p>
        </div>
        <div>
          <h2>Customers</h2>
          <p>Select a customer to view account details.</p>

          <button
            type="button"
            onClick={loadCustomers}
          >
            Refresh Customers
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

      
      

    </>
  )
}

export default App
