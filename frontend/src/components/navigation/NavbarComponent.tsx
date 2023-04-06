import React from 'react';
import {Link} from 'react-router-dom';

function NavbarComponent() {
    return (
        <nav className='navbar navbar-dark bg-dark navbar-expand-sm'>
            <div className='container'>
                <Link to={'/'} className='navbar-brand'>
                    <i className='fa fa-car me-2'/>
                    Vehicle License Plate Management System
                </Link>
            </div>
        </nav>
    )
}

export default NavbarComponent;