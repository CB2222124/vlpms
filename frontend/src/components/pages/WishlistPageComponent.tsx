import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../user/UserContextComponent";
import {Container, Table} from "react-bootstrap";
import axios from "axios";
import WishlistItemComponent from "../wishlist/WishlistItemComponent";

/**
 * Interface representing a listing.
 */
export interface Listing {
    registration: {
        registration: string;
        style: string;
    };
    pricePence: number;
    dateListed: String;
}

/**
 * Page used to display user wishlisted listings. Each individual listing can be removed from the wishlist.
 */
function WishlistPageComponent() {


    const userContext = useContext(UserContext);
    const [listings, setListings] = useState<Listing[]>([]);

    /**
     * Let's fetch the wishlist when the user context is updated.
     */
    useEffect(() => {
        if (userContext?.user?.id) findWishlist(userContext.user.id);
    }, [userContext]);

    //Prompt the user to login where appropriate.
    if (!userContext?.user) return (<Container><span><i>Login to view your wishlist.</i></span></Container>);

    /**
     * Calls the backend service to receive the wishlist for the specified user, and updates the displayed listings.
     * @param id User ID.
     */
    const findWishlist = (id: number) => {
        axios.get(`http://localhost:8080/customer/${id}/wishlist`)
            .then(response => {
                setListings(response.data.map((listing: any) => {
                    return {
                        registration: listing.registration,
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
                    <th>Style</th>
                    <th>Price</th>
                    <th>Date Listed</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                {listings.map((listing: Listing, index) => {
                    return (<tr key={index}><WishlistItemComponent listing={listing} setListings={setListings}/></tr>);
                })}
                </tbody>
            </Table>

        </>
    )
}

export default WishlistPageComponent;