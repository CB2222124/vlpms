import React from 'react';
import '../styles/home.css'
import '../styles/registration.css'

function HomeComponent() {
    return (
        <form className="d-flex flex-column align-items-center justify-content-center" style={{height: '50vh'}}>
            <h2>Find your Personalised Registration</h2>
            <input className="form-control registration search" placeholder="Search" aria-label="Search"/>
            <button className='btn btn-success bg-gradient search search-button mt-2'><b>Search</b></button>
            <span className='text-success mt-2'>Advanced Search</span>
        </form>
    )
}

export default HomeComponent;