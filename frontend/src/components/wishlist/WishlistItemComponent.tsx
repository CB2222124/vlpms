import React, {useContext} from "react";
import {Listing} from "../pages/WishlistPageComponent";
import {Button} from "react-bootstrap";
import axios from "axios";
import {UserContext} from "../user/UserContextComponent";

interface Props {
    listing: Listing; //Listing.
    setListings: React.Dispatch<React.SetStateAction<Listing[]>>; //Listing setter.
}

/**
 * Component responsible for a single table row representing a single listing. Provides the ability to remove a listing
 * by calling the backend service.
 */
function WishlistItemComponent(props: Props) {

    const userContext = useContext(UserContext);
    const listing = props.listing;
    const setListings = props.setListings;

    /**
     * Removes this listing by calling the backend service which provides the updated wishlist. Once the setter is called
     * this component will no longer be used on the next render cycle.
     */
    function handleRemove() {
        axios.delete(`http://localhost:8080/customer/wishlist?id=${userContext?.user?.id}&registration=${listing.registration.registration}`)
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
            <td className="registration" style={{fontSize: "24px"}}>{listing.registration.registration}</td>
            <td>{listing.registration.style}</td>
            <td>{"£" + listing.pricePence / 100}</td>
            <td>{listing.dateListed}</td>
            <td><Button variant="danger" onClick={handleRemove}>Remove</Button></td>
        </>
    )
}

export default WishlistItemComponent;