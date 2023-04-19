import React from 'react';
import {Link} from 'react-router-dom';

function NavbarComponent() {
    return (
        <nav className='navbar navbar-expand-lg navbar-dark bg-success bg-gradient'>
            <Link to={'/'} className='navbar-brand'>
                <i className='fa fa-car me-2 ms-2'/>
                <span>VLPMS | </span>
                <span className='navbar-text'>Vehicle License Plate Management System</span>
            </Link>
            <ul className="navbar-nav me-2 ms-auto">
                <li><Link to={'/'} className='nav-link active'>Home</Link></li>
                <li><Link to={'/'} className='nav-link active'>Registrations</Link></li>
                <li><Link to={'/'} className='nav-link active'>Advanced Search</Link></li>
            </ul>
        </nav>
    )
}

export default NavbarComponent;