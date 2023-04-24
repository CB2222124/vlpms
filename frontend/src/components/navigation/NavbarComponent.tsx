import React from "react";
import {Link} from "react-router-dom";

function NavbarComponent() {
    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-success bg-gradient">
            <Link to={"/"} className="navbar-brand">
                <i className="fa fa-car me-2 ms-2"/>
                <span>VLPMS | </span>
                <span className="navbar-text">Vehicle License Plate Management System</span>
            </Link>
            <ul className="navbar-nav me-2 ms-auto">
                <li><Link to={"/"} className="nav-link active"><i className="fa fa-home me-2"/>Home</Link></li>
                <li><Link to={"/search"} className="nav-link active"><i className="fa fa-search me-2"/>Search</Link></li>
                <li><Link to={"/"} className="nav-link active"><i className="fa fa-user me-2"/>My Account</Link></li>
                {/*
                <i className='fa fa-box me-2'/>Registrations
                <i className='fa fa-heart me-2'/>Wishlist
                */}
            </ul>
        </nav>
    )
}

export default NavbarComponent;