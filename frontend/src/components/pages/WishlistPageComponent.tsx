import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../user/UserContextComponent";
import {Container} from "react-bootstrap";
import axios from "axios";

interface Listing {
    id: string;
    pricePence: number;
    dateListed: string;
}

function WishlistPageComponent() {

    const [registrations, setRegistrations] = useState<Listing[]>([]);

    const userContext = useContext(UserContext);

    useEffect(() => {
        if(userContext?.user?.id) findWishlist(userContext.user.id);
    }, [userContext]);

    if (!userContext?.user) return (<Container><span><i>Login to view owned registrations.</i></span></Container>);

    const findWishlist = (id: number) => {
        axios.get(`http://localhost:8080/customer/${id}/wishlist`)
            .then(response => {
                setRegistrations(response.data.map((listing: any) => {
                    return {
                        id: listing.id,
                        pricePence: listing.pricePence,
                        dateListed: listing.dateListed
                    }
                }));
            })
            .catch(error => console.log(error))
    }

    return (
        <>
            {registrations.map((listing: Listing, index) => {
                return (<div key = {index}><p>{listing.id}</p></div>);
            })}
        </>
    )
}

export default WishlistPageComponent;