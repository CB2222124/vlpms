import React from "react";
import SearchResultGridComponent from "../search/SearchResultGridComponent";
import {SearchResultData} from "../search/SearchResultComponent";

function SearchPageComponent() {

    const mockResultData: SearchResultData[] = [];
    mockResultData.push({registration: "ABC 123", pricePence: 29999});
    mockResultData.push({registration: "ABC 123", pricePence: 29999});
    mockResultData.push({registration: "ABC 123", pricePence: 29999});
    mockResultData.push({registration: "ABC 123", pricePence: 29999});
    mockResultData.push({registration: "ABC 123", pricePence: 29999});
    mockResultData.push({registration: "ABC 123", pricePence: 29999});
    mockResultData.push({registration: "ABC 123", pricePence: 29999});

    return (
        <div className="container">
            <SearchResultGridComponent searchResultList={mockResultData}/>
        </div>
    )
}

export default SearchPageComponent;