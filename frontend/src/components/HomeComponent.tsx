import React from 'react';
import '../styles/registration.css'

function HomeComponent() {
    return (
        <div className="d-flex flex-column align-items-center justify-content-center" style={{height: '50vh'}}>
            <h2>Find your Personalised Registration</h2>
            <form>
                <input className="form-control registration" placeholder="Search" aria-label="Search"/>
            </form>
        </div>
    )
}

export default HomeComponent;