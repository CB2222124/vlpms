import React, {SyntheticEvent, useState} from "react";
import {Link, useNavigate} from "react-router-dom";

function HomePageComponent() {

    const [searchData, setSearchData] = useState<string>("");

    const navigate = useNavigate();

    const handleChange = (event: SyntheticEvent) => {
        const target = event.target as HTMLInputElement;
        setSearchData(target.value);
    }

    const handleSubmit = (event: SyntheticEvent) => {
        event.preventDefault();
        navigate("/search", {state: {search: searchData}});
    }

    return (
        <form className="d-flex flex-column align-items-center justify-content-center home__form"
              onSubmit={handleSubmit}>
            <h2>Find your Personalised Registration</h2>
            <input className="form-control registration" placeholder="Search" aria-label="Search"
                   required pattern="^[a-zA-Z0-9]+$"
                   value={searchData}
                   onChange={handleChange}
                   onInvalid={e =>
                       (e.target as HTMLInputElement).setCustomValidity('Please provide an alphanumeric value.')}
                   onInput={e =>
                       (e.target as HTMLInputElement).setCustomValidity('')}/>
            <button className="mt-2 btn btn-primary bg-gradient"><b>Search</b></button>
            <span className="mt-2"><Link to={"/search"} className="text-primary">Advanced Search</Link></span>
        </form>
    )
}

export default HomePageComponent;