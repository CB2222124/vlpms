import React from "react";
import {SearchResultData} from "../pages/SearchPageComponent";
import {Listing} from "../pages/WishlistPageComponent";
import {Button} from "react-bootstrap";

interface Props {
    listing: Listing;
}

function WishlistItemComponent(props: Props) {
    const listing = props.listing;
    return (
        <>
            <td className="registration" style={{fontSize: "24px"}}>{listing.registration.registration}</td>
            <td>{listing.registration.style}</td>
            <td>{"£" + listing.pricePence / 100}</td>
            <td>{listing.dateListed}</td>
            <td><Button variant="danger">Remove</Button></td>
        </>
    )
}

export default WishlistItemComponent;