import React, {useEffect, useState} from "react";
import SearchResultGridComponent from "../search/SearchResultGridComponent";
import axios from 'axios';
import SearchToolbarComponent from "../search/SearchToolbarComponent";
import {useLocation} from "react-router-dom";

/**
 * Interface representing search data.
 */
export interface SearchData {
    sort: string;
    style: string;
    similar: string;
    page: number;
}

/**
 * Interface representing a listing.
 */
export interface SearchResultData {
    registration: {
        registration: string;
        style: string;
    };
    pricePence: number;
    dateListed: String;
}

/**
 * Search page. Search data is manipulated through the SearchToolbarComponent child component and the
 * backend service is called. SearchGridComponent is responsible for displaying results.
 */
function SearchPageComponent() {

    const location = useLocation();
    const [searchResultData, setSearchResultData] = useState<SearchResultData[]>([]);

    /**
     * Provide a default search.
     */
    const [searchData, setSearchData] = useState<SearchData>({
        sort: "dateListed%2Cdesc",
        style: "",
        similar: "",
        page: 0
    });

    /**
     * When the search page is first opened, let's check if there is data provided to use through
     * the useLocation hook. If there is use it, otherwise use our default search data.
     */
    useEffect(() => {
        let data: SearchData = searchData;
        if (location.state) {
            data = {sort: "", style: "", page: 0, similar: location.state.search};
            setSearchData(data);
        }
        performSearch(data);
    }, []);

    /**
     * Calls the backend service with the provided search data and assigns results to searchResultData.
     * @param data The search data.
     */
    const performSearch = (data: SearchData) => {
        const url: string = constructSearchUrl(data);
        axios.get(url)
            .then(response => {
                setSearchResultData(response.data.content.map((listing: any) => {
                    return {
                        registration: listing.registration,
                        pricePence: listing.pricePence,
                        dateListed: listing.dateListed
                    }
                }));
            })
            .catch(() => setSearchResultData([]))
    }

    /**
     * Constructs the appropriate backend service call based on search data.
     * @param data The search data.
     */
    const constructSearchUrl = (data: SearchData): string => {
        const similar: string = data.similar.toUpperCase().trim();
        if (similar != "") return `/listing/search/similar?target=${similar}&page=${data.page}&size=20`;
        if (data.style != "") return `/listing/search/style?style=${data.style}&page=${data.page}&size=20&sort=${data.sort}`;
        return `/listing/search?page=${data.page}&size=20&sort=${data.sort}`;
    }

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