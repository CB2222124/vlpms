import React from "react";
import {SearchResultData} from "../pages/SearchPageComponent";

interface Props {
    searchResultData: SearchResultData;
}

function SearchResultComponent(props: Props) {
    const searchResultData = props.searchResultData;
    return (
        <div className="search__result">
            <span><b>{"£" + searchResultData.pricePence / 100}</b></span>
            <span className="float-end"><i className="fa fa-heart-circle-plus text-primary"/></span>
            <p className="mb-0 registration">{searchResultData.registration}</p>
            <span className="text-primary"><i className="fa fa-shopping-cart"></i> Buy</span>
            <p className="float-end text-muted small"><b>{searchResultData.dateListed}</b></p>
        </div>
    )
}

export default SearchResultComponent;