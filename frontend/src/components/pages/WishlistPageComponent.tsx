import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../user/UserContextComponent";
import {Button, Container, Table} from "react-bootstrap";
import axios from "axios";
import {SearchResultData} from "./SearchPageComponent";
import SearchResultComponent from "../search/SearchResultComponent";
import WishlistItemComponent from "../wishlist/WishlistItemComponent";

export interface Listing {
    registration: string;
    pricePence: number;
    dateListed: string;
}

function WishlistPageComponent() {

    const [listings, setListings] = useState<Listing[]>([]);

    const userContext = useContext(UserContext);

    useEffect(() => {
        if (userContext?.user?.id) findWishlist(userContext.user.id);
    }, [userContext]);

    if (!userContext?.user) return (<Container><span><i>Login to view your wishlist.</i></span></Container>);

    const findWishlist = (id: number) => {
        axios.get(`http://localhost:8080/customer/${id}/wishlist`)
            .then(response => {
                setListings(response.data.map((listing: any) => {
                    return {
                        registration: listing.id,
                        pricePence: listing.pricePence,
                        dateListed: listing.dateListed
                    }
                }));
            })
            .catch(error => console.log(error))
    }

    return (
        <>
            <Table striped hover style={{textAlign: "center", verticalAlign: "middle"}}>
                <thead>
                <tr>
                    <th>Registration</th>
                    <th>Price</th>
                    <th>Date Listed</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                {listings.map((listing: Listing, index) => {
                    return (<tr key={index}><WishlistItemComponent listing={listing}/></tr>);
                })}
                </tbody>
            </Table>

        </>
    )
}

export default WishlistPageComponent;