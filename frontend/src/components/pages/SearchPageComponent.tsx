import React, {useEffect, useState} from "react";
import SearchResultGridComponent from "../search/SearchResultGridComponent";
import axios from 'axios';
import {SearchResultData} from "../search/SearchResultComponent";

function SearchPageComponent() {

    const [data, setData] = useState<SearchResultData[]>([]);

    useEffect(() => {
        axios.get('http://localhost:8080/listing/search/newest', {params: {size: 20}})
            .then(response => {
                setData(response.data._embedded.listing.map((listing: any) => {
                    return {
                        registration: listing.id,
                        pricePence: listing.pricePence,
                        dateListed: listing.dateListed
                    }
                }));
            })
            .catch(error => console.log(error))
    }, []);

    return (
        <div className="container">
            <SearchResultGridComponent searchResultList={data}/>
        </div>
    )
}

export default SearchPageComponent;