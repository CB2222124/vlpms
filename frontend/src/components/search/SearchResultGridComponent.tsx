import React from "react";
import SearchResultComponent from "./SearchResultComponent";
import {SearchResultData} from "./SearchResultComponent";

interface Props {
    searchResultList: SearchResultData[];
}

function SearchResultGridComponent(props: Props) {

    const searchResultList = props.searchResultList;

    if (searchResultList.length === 0) return (<span><i>No Results.</i></span>);

    return (
        <>
            <div className="d-flex flex-wrap">
                {searchResultList.map((searchResult: SearchResultData, index) => {
                    return (<div key = {index}><SearchResultComponent searchResultData={searchResult}/></div>);
                })}
            </div>
        </>
    );
}

export default SearchResultGridComponent;