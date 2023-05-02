import React, {SyntheticEvent} from "react";
import {SearchData} from "../pages/SearchPageComponent";

interface Props {
    performSearch(data: SearchData): void;

    searchData: SearchData;
    setSearchData: React.Dispatch<React.SetStateAction<SearchData>>;
}

function SearchToolbarComponent(props: Props) {

    const searchData = props.searchData;
    const setSearchData = props.setSearchData;

    const performSearch = props.performSearch;

    const handleChange = (event: SyntheticEvent) => {
        const target = event.target as HTMLInputElement;
        if (target.name == "sort") { //If we are switching sorts...
            setSearchData({...searchData, sort: target.value, similar: ""}); //Let's clear the conditional input field.
        } else {
            setSearchData({...searchData, [target.name]: target.value});
        }
    }

    const handleSubmit = (event: SyntheticEvent) => {
        event.preventDefault();
        performSearch(searchData);
    }

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <div className="mb-2">
                    <label>Style</label>
                    <select className="form-select" name="style" value={searchData.style} onChange={handleChange}>
                        <option value="">All</option>
                        <option value="Current">Current</option>
                        <option value="Prefix">Prefix</option>
                        <option value="Suffix">Suffix</option>
                    </select>
                </div>

                <div className="mb-2">
                    <label>Sort</label>
                    <select className="form-select" name="sort" value={searchData.sort} onChange={handleChange}>
                        <option value="">Similarity</option>
                        <option value="dateListed%2Cdesc">Newest</option>
                        <option value="dateListed">Oldest</option>
                        <option value="pricePence">Lowest Price</option>
                        <option value="pricePence%2Cdesc">Highest Price</option>
                    </select>
                </div>

                <div className="mb-2">
                    <label>Similar to</label>
                    <input type="text" className="form-control" name="similar"
                           value={searchData.similar}
                           onChange={handleChange}
                           disabled={searchData.sort != ""}
                           required pattern="^[a-zA-Z0-9]+$"
                           onInvalid={e =>
                               (e.target as HTMLInputElement).setCustomValidity('Please provide an alphanumeric value.')}
                           onInput={e =>
                               (e.target as HTMLInputElement).setCustomValidity('')}/>
                </div>

                <button type="submit" className="btn btn-primary">Update search</button>
            </form>
        </div>

    )
}

export default SearchToolbarComponent;