import React from "react";
import {Link} from "react-router-dom";

function NavbarComponent() {
    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-primary bg-gradient">
            <Link to={"/"} className="navbar-brand">
                <i className="fa fa-car me-2 ms-2"/>
                <span>VLPMS | </span>
                <span className="navbar-text">Vehicle License Plate Management System</span>
            </Link>
            <div className="navbar-nav me-2 ms-auto">
                <Link to={"/"} className="nav-link active"><i className="fa fa-home me-2"/>Home</Link>
                <Link to={"/search"} className="nav-link active"><i className="fa fa-search me-2"/>Search</Link>
                <Link to={"/"} className="nav-link active"><i className="fa fa-box me-2"/>Registrations</Link>
                <Link to={"/"} className="nav-link active"><i className="fa fa-heart me-2"/>Wishlist</Link>
                <Link to={"/"} className="nav-link active"><i className="fa fa-user me-2"/>My Account</Link>
            </div>
        </nav>
    )
}

export default NavbarComponent;