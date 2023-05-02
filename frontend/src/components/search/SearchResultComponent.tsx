import React from "react";

export interface SearchResultData {
    registration: String;
    pricePence: number;
    dateListed: String;
}

interface Props {
    searchResultData: SearchResultData;
}

function SearchResultComponent(props: Props) {
    const searchResultData = props.searchResultData;
    return (
        <div className="search__result">
            <span className="mb-0"><b>{"£" + searchResultData.pricePence / 100}</b></span>
            <span className="float-end"><i className="fa fa-heart-circle-plus text-secondary"/></span>
            <p className="mb-0 registration">{searchResultData.registration}</p>
            <p className="text-success"><i className="fa fa-eye"></i> View</p>
        </div>
    )
}

export default SearchResultComponent;