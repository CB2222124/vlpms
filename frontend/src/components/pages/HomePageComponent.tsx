import React from "react";
import {Link} from "react-router-dom";

function HomePageComponent() {
    return (
        <form className="d-flex flex-column align-items-center justify-content-center home__form">
            <h2>Find your Personalised Registration</h2>
            <input className="form-control registration" placeholder="Search" aria-label="Search"/>
            <button className="mt-2 btn btn-success bg-gradient"><b>Search</b></button>
            <span className="mt-2"><Link to={"/search"} className="text-success">Advanced Search</Link></span>
        </form>
    )
}

export default HomePageComponent;