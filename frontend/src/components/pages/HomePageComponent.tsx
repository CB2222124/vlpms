import React, {SyntheticEvent, useState} from "react";
import {Link, useNavigate} from "react-router-dom";

/**
 * Application landing page. The useNavigate hook is used to switch to the search page with (validated) search data
 * entered on this page.
 */
function HomePageComponent() {

    const navigate = useNavigate();
    const [searchData, setSearchData] = useState<string>("");

    /**
     * Provides search data to the search page using useNavigate hook.
     * @param event Form submit event.
     */
    const handleSubmit = (event: SyntheticEvent) => {
        event.preventDefault();
        navigate("/search", {state: {search: searchData}});
    }

    const handleChange = (event: SyntheticEvent) => {
        const target = event.target as HTMLInputElement;
        setSearchData(target.value);
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