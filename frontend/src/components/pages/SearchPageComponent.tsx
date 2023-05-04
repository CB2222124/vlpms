import React, {useEffect, useState} from "react";
import SearchResultGridComponent from "../search/SearchResultGridComponent";
import axios from 'axios';
import SearchToolbarComponent from "../search/SearchToolbarComponent";
import {useLocation} from "react-router-dom";

export interface SearchData {
    sort: string;
    style: string;
    similar: string;
    page: number;
}

export interface SearchResultData {
    registration: {
        registration: string;
        style: string;
    };
    pricePence: number;
    dateListed: String;
}

function SearchPageComponent() {

    const [searchData, setSearchData] = useState<SearchData>({
        sort: "dateListed%2Cdesc",
        style: "",
        similar: "",
        page: 0
    });

    const [searchResultData, setSearchResultData] = useState<SearchResultData[]>([]);

    const location = useLocation();

    const performSearch = (data: SearchData) => {
        const url: string = constructSearchUrl(data);
        axios.get(url)
            .then(response => {
                setSearchResultData(response.data._embedded.listing.map((listing: any) => {
                    return {
                        registration: listing.registration,
                        pricePence: listing.pricePence,
                        dateListed: listing.dateListed
                    }
                }));
            })
            .catch(error => console.log(error))
    }

    const constructSearchUrl = (data: SearchData): string => {
        const similar: string = data.similar.toUpperCase().trim();
        if(similar != "") return `http://localhost:8080/listing/search/findBySimilarity?target=${similar}&page=${data.page}&size=20`;
        if(data.style != "") return `http://localhost:8080/listing/search/findByRegistrationStyleIn?styles=${data.style}&page=${data.page}&size=20&sort=${data.sort}`;
        return `http://localhost:8080/listing?page=${data.page}&size=20&sort=${data.sort}`;
    }

    useEffect(() => {
        let data: SearchData = searchData;
        if (location.state) {
            data = {sort: "", style: "", page: 0, similar: location.state.search};
            setSearchData(data);
        }
        performSearch(data);
    }, []);

    return (
        <div className="d-flex ms-2 mt-2">
            <div className="search__toolbar me-2">
                <SearchToolbarComponent searchData={searchData} setSearchData={setSearchData}
                                        performSearch={performSearch}/>
            </div>
            <div className="vr"/>
            <SearchResultGridComponent searchResultList={searchResultData}/>
        </div>
    )
}

export default SearchPageComponent;